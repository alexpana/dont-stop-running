package org.vertexarmy.dsr.leveleditor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.google.common.base.Function;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.annotation.Nullable;
import org.vertexarmy.dsr.Version;
import org.vertexarmy.dsr.core.Log;
import org.vertexarmy.dsr.core.Root;
import org.vertexarmy.dsr.core.Serialization;
import org.vertexarmy.dsr.core.assets.FontRepository;
import org.vertexarmy.dsr.core.assets.TextureRepository;
import org.vertexarmy.dsr.core.component.ComponentType;
import org.vertexarmy.dsr.core.component.InputComponent;
import org.vertexarmy.dsr.core.component.Node;
import org.vertexarmy.dsr.core.component.RenderComponent;
import org.vertexarmy.dsr.core.systems.RenderSystem;
import org.vertexarmy.dsr.game.Level;
import org.vertexarmy.dsr.leveleditor.cameracontroller.AutoScrollCameraController;
import org.vertexarmy.dsr.leveleditor.cameracontroller.UserPanningCameraController;
import org.vertexarmy.dsr.leveleditor.levelrenderer.LevelRenderer;
import org.vertexarmy.dsr.leveleditor.polygoneditor.PolygonEditor;
import org.vertexarmy.dsr.leveleditor.ui.DebugValuesPanel;
import org.vertexarmy.dsr.leveleditor.ui.Dialog;
import org.vertexarmy.dsr.leveleditor.ui.ElegantGraySkin;
import org.vertexarmy.dsr.leveleditor.ui.LevelBackgroundDialog;
import org.vertexarmy.dsr.leveleditor.ui.LevelLoadDialog;
import org.vertexarmy.dsr.leveleditor.ui.LevelSaveDialog;
import org.vertexarmy.dsr.leveleditor.ui.SpritePickerDialog;
import org.vertexarmy.dsr.leveleditor.ui.Toolbox;
import org.vertexarmy.dsr.math.Algorithms;
import org.vertexarmy.dsr.math.Polygon;

class LevelEditor extends Game {
    private final Log log = Log.create();

    private final Root root = new Root();

    private final Function<LevelEditor, Boolean> initTask;

    private final LevelRenderer levelRenderer = new LevelRenderer();

    private final GridRenderer gridRenderer = new GridRenderer();

    private File boundLevelFile;

    private Level level;

    private PolygonEditor terrainPolygonEditor;

    private Toolbox toolbox;

    private DebugValuesPanel debugValuesPanel;

    private LevelSaveDialog saveDialog;

    private LevelLoadDialog loadDialog;

    private SpritePickerDialog spritePickerDialog;

    private LevelBackgroundDialog levelBackgroundDialog;

    private UserPanningCameraController userUserPanningCameraController = new UserPanningCameraController();

    private AutoScrollCameraController autoScrollCameraController = new AutoScrollCameraController();

    private LevelEditor(Function<LevelEditor, Boolean> initTask) {
        this.initTask = initTask;
    }

    @Override
    public void create() {
        root.initialize();

        loadAssets();

        ElegantGraySkin.install(root.getUiNode().getUiSkin());

        root.addNode(new Node(ComponentType.INPUT, userUserPanningCameraController));
        root.addNode(new Node(ComponentType.UPDATE, autoScrollCameraController));
        root.addNode(createEditorNode());

        initUI();

        setLevel(Level.createDefaultLevel());

        userUserPanningCameraController.setEnabled(true);

        initTask.apply(this);
    }

    private Node createEditorNode() {
        Node originNode = new Node(ComponentType.RENDER, new RenderComponent() {
            @Override
            public void render() {
                update();

                gridRenderer.renderGrid();

                levelRenderer.render();

                gridRenderer.renderRulers();
            }
        });

        originNode.addComponent(ComponentType.INPUT, new InputComponent() {
            @Override
            public InputProcessor getInputAdapter() {
                return new InputAdapter() {
                    @Override
                    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                        if (terrainPolygonEditor.getHoveredVertex() != null) {
                            return false;
                        }

                        Vector2 mouseWorldPosition = RenderSystem.instance().screenToWorld(new Vector2(screenX, screenY));
                        Polygon clickedPolygon = findPolygonWhichContains(mouseWorldPosition);

                        if (clickedPolygon != null && clickedPolygon != terrainPolygonEditor.getPolygon()) {
                            editPolygon(clickedPolygon);
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                        if (terrainPolygonEditor.getHoveredVertex() != null) {
                            return false;
                        }

                        Vector2 mouseWorldPosition = RenderSystem.instance().screenToWorld(new Vector2(screenX, screenY));

                        if (findPolygonWhichContains(mouseWorldPosition) == null) {
                            editPolygon(null);
                            return false;
                        }

                        return true;
                    }

                    @Override
                    public boolean keyDown(int keycode) {
                        if (Shortcuts.isHideDebugInfoShortcut(keycode)) {
                            debugValuesPanel.setVisible(!debugValuesPanel.isVisible());
                            return true;
                        }

                        if (Shortcuts.isHideUIShortcut(keycode)) {
                            root.getUiNode().getContentTable().setVisible(!root.getUiNode().getContentTable().isVisible());
                            return true;
                        }

                        if (Shortcuts.isSaveShortcut(keycode)) {
                            if (boundLevelFile == null) {
                                saveDialog.show();
                            } else {
                                saveLevel();
                            }
                            return true;
                        }

                        if (Shortcuts.isOpenShortcut(keycode)) {
                            openLevelFile();
                            return true;
                        }

                        if (Shortcuts.isFullscreenShortcut(keycode)) {
                            RenderSystem.instance().toggleFullscreen();
                            return true;
                        }

                        if (Shortcuts.isSpritePickerShortcut(keycode)) {
                            spritePickerDialog.show();
                        }

                        if (Shortcuts.isEditBackgroundShortcut(keycode)) {
                            levelBackgroundDialog.show();
                        }

                        return false;
                    }

                    private Polygon findPolygonWhichContains(Vector2 mouseWorldPosition) {
                        Polygon clickedPolygon = null;

                        for (Polygon terrainPolygon : level.getTerrainPatches()) {
                            if (Algorithms.polygonContainsVertex(mouseWorldPosition, terrainPolygon)) {
                                clickedPolygon = terrainPolygon;
                            }
                        }
                        return clickedPolygon;
                    }
                };
            }
        });
        return originNode;
    }

    private void loadAssets() {
        loadFonts();
        loadTextures();
    }

    private void loadFonts() {
        FontRepository.instance().loadFont(AssetName.FONT_MARKE_8, Gdx.files.internal("fonts/marke_eigenbau_normal_8.fnt"));
        FontRepository.instance().loadFont(AssetName.FONT_VERA_SANS_MONO_10, Gdx.files.internal("fonts/vera_sans_mono_10.fnt"));
    }

    private void loadTextures() {
        Texture tilesTexture = new Texture(Gdx.files.internal("tiles.png"));
        TextureRepository.instance().addTexture("grass", new TextureRegion(tilesTexture, 0, 0, 32, 32));
        TextureRepository.instance().addTexture("dirt", new TextureRegion(tilesTexture, 32, 0, 32, 32));
        TextureRepository.instance().addTexture("saw", new TextureRegion(tilesTexture, 64, 0, 64, 64));
        TextureRepository.instance().addTexture("floor_tile_42342_42523_01", new TextureRegion(new Texture(Gdx.files.internal("test.jpg"))));
        TextureRepository.instance().loadTextureAtlas(Gdx.files.internal("ui/ui_icons.atlas"));
        TextureRepository.instance().loadTextureAtlas(Gdx.files.internal("ui/ui_icons_background.atlas"));
    }

    private void initUI() {
        debugValuesPanel = new DebugValuesPanel(root.getUiNode().getUiSkin());
        debugValuesPanel.setVisible(false);

        toolbox = new Toolbox(root.getUiNode().getUiSkin(), new Toolbox.Listener() {
            @Override
            public void loadFileRequested() {
                openLevelFile();
            }

            @Override
            public void saveFileRequested() {
                saveDialog.show();
            }

            @Override
            public void autoScrollForwardRequested() {
                userUserPanningCameraController.setEnabled(false);
                autoScrollCameraController.increaseSpeed();
            }

            @Override
            public void pauseAutoScrollRequested() {
                userUserPanningCameraController.setEnabled(true);
                autoScrollCameraController.resetSpeed();
            }

            @Override
            public void autoScrollBackwardRequested() {
                userUserPanningCameraController.setEnabled(false);
                autoScrollCameraController.decreaseSpeed();
            }
        });

        root.getUiNode().getContentTable().add(toolbox).expandX().fill().row();
        root.getUiNode().getContentTable().add(debugValuesPanel).left().row();

        saveDialog = new LevelSaveDialog(root.getUiNode().getStage(), "Save Level", root.getUiNode().getUiSkin());
        saveDialog.setListener(new Dialog.Listener<LevelSaveDialog.Event>() {
            @Override
            public void dialogAccepted(LevelSaveDialog.Event event) {
                setBoundLevelFile(new File("levels/" + event.getFilename() + ".dat"));
                saveLevel();
            }
        });

        loadDialog = new LevelLoadDialog(root.getUiNode().getStage(), "Load Level", root.getUiNode().getUiSkin());
        loadDialog.setListener(new Dialog.Listener<LevelLoadDialog.Event>() {
            @Override
            public void dialogAccepted(LevelLoadDialog.Event event) {
                log.debug("Requested to load the level " + event.getLevelName());

                try {
                    loadLevel(new File("levels/" + event.getLevelName() + ".dat"));
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        });

        spritePickerDialog = new SpritePickerDialog(root.getUiNode().getStage(), "Select terrain texture", root.getUiNode().getUiSkin());

        levelBackgroundDialog = new LevelBackgroundDialog(root.getUiNode().getStage(), "Edit Background", root.getUiNode().getUiSkin());
        levelBackgroundDialog.setListener(new Dialog.Listener<LevelBackgroundDialog.Event>() {
            @Override
            public void dialogAccepted(LevelBackgroundDialog.Event event) {
                level.getBackgroundLayers().clear();
                level.getBackgroundLayers().addAll(event.getBackgroundLayers());
                levelRenderer.reloadLevel();
            }
        });
    }

    private void openLevelFile() {
        loadDialog.setAvailableLevelsList(FileUtils.discoverAvailableLevels());
        loadDialog.show();
    }

    public void loadLevel(File selectedFile) {
        try {
            log.debug("Attempting to load file " + selectedFile);
            FileInputStream inputStream = new FileInputStream(selectedFile);

            Level level = Serialization.deserialize(inputStream);

            setLevel(level);

            setBoundLevelFile(selectedFile);
        } catch (Exception e) {
            log.exception(e);
        }
    }

    private void saveLevel() {
        if (boundLevelFile != null) {
            try {
                FileOutputStream outputStream = new FileOutputStream(boundLevelFile);
                Serialization.serialize(outputStream, level);
                log.info("Saved level " + boundLevelFile.getAbsolutePath());
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        } else {
            log.warning("Attempted to save a level which is not bound to an external file.");
        }
    }

    private void setBoundLevelFile(File selectedFile) {
        boundLevelFile = selectedFile;
        if (boundLevelFile != null) {
            Gdx.graphics.setTitle(boundLevelFile.getAbsolutePath() + " - Level Editor - " + Version.value());
        }
    }

    @Override
    public void render() {
        root.update();
    }

    @Override
    public void resize(int w, int h) {
        root.handleResize(w, h);
    }

    private void update() {
        Camera camera = RenderSystem.instance().getCamera();
        float zoom = RenderSystem.instance().getZoom();

        DebugValues.instance().setValue(DebugItems.CAMERA_POSITION, (int) camera.position.x + ", " + (int) camera.position.y);
        DebugValues.instance().setValue(DebugItems.ZOOM, String.valueOf(zoom * 100) + " %");
        DebugValues.instance().setValue(DebugItems.MOUSE_VIEWPORT, Gdx.input.getX() + ", " + Gdx.input.getY());

        Vector2 mouseWorld = RenderSystem.instance().screenToWorld(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        DebugValues.instance().setValue(DebugItems.MOUSE_WORLD, (int) mouseWorld.x + ", " + (int) mouseWorld.y);

        DebugValues.instance().setValue(DebugItems.FPS, String.valueOf(Gdx.graphics.getFramesPerSecond()));

        levelRenderer.reloadLevel();
    }

    private void setLevel(Level level) {
        this.level = level;

        editPolygon(level.getTerrainPatches().get(0));

        levelRenderer.setLevel(level);
    }

    private void editPolygon(@Nullable Polygon polygon) {
        if (terrainPolygonEditor != null) {
            if (polygon == terrainPolygonEditor.getPolygon()) {
                return;
            }

            root.removeNode(terrainPolygonEditor.getNode());
        }

        if (polygon != null) {
            terrainPolygonEditor = new PolygonEditor(polygon);
            root.addNode(terrainPolygonEditor.getNode());
        }
    }

    public static void launch(Function<LevelEditor, Boolean> initTask) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1000;
        config.height = 800;
        config.fullscreen = false;
        config.vSyncEnabled = true;
        config.title = "Level Editor - " + Version.value();
        new LwjglApplication(new LevelEditor(initTask), config);
    }
}
