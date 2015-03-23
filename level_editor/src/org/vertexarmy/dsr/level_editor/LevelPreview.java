package org.vertexarmy.dsr.level_editor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.vertexarmy.dsr.core.Root;
import org.vertexarmy.dsr.core.UiNode;
import org.vertexarmy.dsr.core.component.ComponentType;
import org.vertexarmy.dsr.core.component.Node;
import org.vertexarmy.dsr.core.component.RenderComponent;
import org.vertexarmy.dsr.core.systems.RenderSystem;
import org.vertexarmy.dsr.game.Level;
import org.vertexarmy.dsr.game.Tiles;
import org.vertexarmy.dsr.graphics.ShaderRepository;
import org.vertexarmy.dsr.graphics.SpriteFactory;
import org.vertexarmy.dsr.level_editor.polygon_editor.PolygonEditor;

class LevelPreview extends Game {
    private final static SpriteFactory SPRITE_FACTORY = SpriteFactory.getInstance();

    private final Root root = new Root();

    private final Map<Tiles, TextureRegion> tiles = new HashMap<>();

    private Level level;

    private DebugValuesPanel debugValuesPanel;

    private final String levelToLoad;

    private PolygonSprite terrainSprite;

    private PolygonEditor terrainPolygonEditor;

    private UiNode uiNode;

    private LevelPreview(String level) {
        levelToLoad = level;
    }

    @Override
    public void create() {
        root.initialize();

        ShaderRepository.instance().initialize();

        root.addNode(new Node(ComponentType.INPUT, new CameraController()));

        uiNode = new UiNode();
        root.addNode(uiNode);

        Node originNode = new Node(ComponentType.RENDER, new RenderComponent() {
            @Override
            public void render() {
                doUpdate();

                ShapeRenderer shapeRenderer = RenderSystem.instance().getShapeRenderer();
                PolygonSpriteBatch polygonSpriteBatch = RenderSystem.instance().getPolygonSpriteBatch();

                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(Color.YELLOW);
                shapeRenderer.circle(0, 0, 6);
                shapeRenderer.end();

                if (level != null) {
                    polygonSpriteBatch.begin();
                    terrainSprite.draw(polygonSpriteBatch);
                    polygonSpriteBatch.end();
                }
            }
        });
        root.addNode(originNode);

        Texture tilesTexture = new Texture(Gdx.files.internal("tiles.png"));
        tiles.put(Tiles.GRASS, new TextureRegion(tilesTexture, 0, 0, 32, 32));
        tiles.put(Tiles.DIRT, new TextureRegion(tilesTexture, 32, 0, 32, 32));
        tiles.put(Tiles.SAW, new TextureRegion(tilesTexture, 64, 0, 64, 64));

        createUI();

        attemptToLoadLevel();
    }

    private void attemptToLoadLevel() {
        if (levelToLoad != null) {
            LevelLoader loader = new LevelLoader(levelToLoad);
            Level level = null;

            try {
                level = loader.load();
            } catch (LevelLoader.CorruptLevel | IOException e) {
                Gdx.app.error("LevelLauncher", "Could not load SVG file. Reason: " + e.getMessage());
            }

            if (level != null) {
                setLevel(level, levelToLoad);
            }
        }
    }

    private void createUI() {
        debugValuesPanel = new DebugValuesPanel(uiNode.getUiSkin());
        uiNode.getContentTable().add(debugValuesPanel);
    }

    @Override
    public void render() {
        root.update();
    }

    private void doUpdate() {
        Camera camera = RenderSystem.instance().getCamera();
        float zoom = RenderSystem.instance().getZoom();

        DebugValues.instance().setValue("camera position", (int) camera.position.x + ", " + (int) camera.position.y);
        DebugValues.instance().setValue("zoom", String.valueOf(zoom * 100) + " %");
        DebugValues.instance().setValue("mouse (vp)", Gdx.input.getX() + ", " + Gdx.input.getY());

        Vector2 mouseWorld = RenderSystem.instance().screenToWorld(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        DebugValues.instance().setValue("mouse (world)", (int) mouseWorld.x + ", " + (int) mouseWorld.y);

        terrainSprite = SPRITE_FACTORY.createSprite(level.getTerrainPatches().get(0));
        terrainSprite.setColor(Color.BLACK);
    }

    @Override
    public void resize(int w, int h) {
        RenderSystem.instance().setViewportSize(w, h);

        uiNode.getStage().getViewport().update(w, h, true);
    }

    void setLevel(Level level, String levelName) {
        this.level = level;
        DebugValues.instance().setValue("loaded level", levelName);

        terrainSprite = SPRITE_FACTORY.createSprite(level.getTerrainPatches().get(0));
        terrainSprite.setColor(Color.BLACK);

        terrainPolygonEditor = new PolygonEditor(level.getTerrainPatches().get(0));
        root.addNode(terrainPolygonEditor.getNode());
    }

    public static void launch(String level) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1024;
        config.height = 800;
        new LwjglApplication(new LevelPreview(level), config);
    }
}
