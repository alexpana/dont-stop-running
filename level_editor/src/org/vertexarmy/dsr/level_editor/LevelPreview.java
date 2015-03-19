package org.vertexarmy.dsr.level_editor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.vertexarmy.dsr.game.Level;
import org.vertexarmy.dsr.game.Tiles;
import org.vertexarmy.dsr.game.behavior.Behavior;
import org.vertexarmy.dsr.game.behavior.SpriteRotateBehavior;
import org.vertexarmy.dsr.game.behavior.SweepHorizontalBehavior;
import org.vertexarmy.dsr.game.behavior.SweepVerticalBehavior;
import org.vertexarmy.dsr.game.objects.GameObject;
import org.vertexarmy.dsr.game.objects.SpriteObject;
import org.vertexarmy.dsr.graphics.ShaderRepository;
import org.vertexarmy.dsr.graphics.ShaderRepository.ShaderInstance;
import org.vertexarmy.dsr.graphics.SpriteFactory;

public class LevelPreview extends Game {
    private final static SpriteFactory SPRITE_FACTORY = SpriteFactory.getInstance();

    private PolygonSpriteBatch spriteBatch;

    private Map<Tiles, TextureRegion> tiles = new HashMap<>();

    private Camera camera;

    private CameraController cameraController;

    private final List<GameObject> gameObjectList = new ArrayList<>();

    private final Map<GameObject, List<Behavior>> behaviors = new HashMap<>();
    private Level level;
    private String levelName;

    private Stage stage;
    private Label cameraPositionLabel;
    private Label levelNameLabel;

    private ShaderRepository shaderRepository = new ShaderRepository();

    private LevelPreview() {
    }

    @Override
    public void create() {
        shaderRepository.buildShaders();

        spriteBatch = new PolygonSpriteBatch(128, shaderRepository.getShader(ShaderInstance.DEFAULT_POS_COL_TEX_PROJ));
        cameraController = new CameraController();

        Texture tilesTexture = new Texture(Gdx.files.internal("tiles.png"));
        tiles.put(Tiles.GRASS, new TextureRegion(tilesTexture, 0, 0, 32, 32));
        tiles.put(Tiles.DIRT, new TextureRegion(tilesTexture, 32, 0, 32, 32));
        tiles.put(Tiles.SAW, new TextureRegion(tilesTexture, 64, 0, 64, 64));

        Sprite sawSprite = new Sprite(tiles.get(Tiles.SAW));
        sawSprite.setCenter(32, 32);

        gameObjectList.add(new SpriteObject(sawSprite, new Vector2(300, 300)));
        gameObjectList.add(new SpriteObject(sawSprite, new Vector2(350, 300)));
        gameObjectList.add(new SpriteObject(sawSprite, new Vector2(200, 150)));
        gameObjectList.add(new SpriteObject(sawSprite, new Vector2(300, 200)));

        SweepVerticalBehavior verticalSlow = new SweepVerticalBehavior(50, 2);
        SweepVerticalBehavior verticalFast = new SweepVerticalBehavior(50, 6f);
        SweepHorizontalBehavior horizontalSlow = new SweepHorizontalBehavior(50, 2);
        SweepHorizontalBehavior horizontalFast = new SweepHorizontalBehavior(50, 6);
        SpriteRotateBehavior rotateFast = new SpriteRotateBehavior(1.0f);
        SpriteRotateBehavior rotateSlow = new SpriteRotateBehavior(3.0f);

        addBehavior(gameObjectList.get(0), verticalFast);
        addBehavior(gameObjectList.get(0), horizontalFast);
        addBehavior(gameObjectList.get(0), rotateFast);
        addBehavior(gameObjectList.get(1), verticalSlow);
        addBehavior(gameObjectList.get(1), rotateFast);
        addBehavior(gameObjectList.get(2), horizontalFast);
        addBehavior(gameObjectList.get(2), rotateSlow);
        addBehavior(gameObjectList.get(3), horizontalSlow);
        addBehavior(gameObjectList.get(3), rotateSlow);

        createUI();

        Gdx.input.setInputProcessor(new InputMultiplexer(cameraController));

        Gdx.gl.glClearColor(0.22f, 0.23f, 0.23f, 1);
    }

    private void createUI() {
        FileHandle skinFileHandle = Gdx.files.internal("ui/skin.json");
        Skin uiSkin = new Skin(skinFileHandle);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        stage.setDebugAll(true);

        cameraPositionLabel = new Label("", uiSkin);
        cameraPositionLabel.setAlignment(Align.left);

        levelNameLabel = new Label("No level loaded.", uiSkin);
        levelNameLabel.setAlignment(Align.left);

        table.add(cameraPositionLabel).row();
        table.add(levelNameLabel);

        table.left().top();
    }

    private void addBehavior(GameObject object, Behavior behavior) {
        if (!behaviors.containsKey(object)) {
            behaviors.put(object, new ArrayList<Behavior>());
        }

        behaviors.get(object).add(behavior);
    }

    @Override
    public void render() {
        doUpdate();
        doRender();
    }

    private void doUpdate() {
        cameraPositionLabel.setText("Camera Position: " + camera.position.x + ", " + camera.position.y);
        spriteBatch.setTransformMatrix(camera.view);
    }

    private void doRender() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderUI();

        spriteBatch.begin();
        renderGrid();
        renderTerrain();
        spriteBatch.end();
    }

    private void renderGrid() {
    }

    private void renderUI() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void renderTerrain() {
        if (level == null) {
            return;
        }

        PolygonSprite terrain = SPRITE_FACTORY.createSprite(level.getTerrainPatches().get(0));
        terrain.setColor(Color.BLACK);

        PolygonSprite startPosition = SPRITE_FACTORY.createSprite(level.getStartArea());
        startPosition.setColor(Color.GREEN);

        terrain.draw(spriteBatch);
        startPosition.draw(spriteBatch);
    }

    @Override
    public void resize(int w, int h) {
        camera = new OrthographicCamera(w, h);
        camera.update();
        cameraController.setManagedCamera(camera);

        Matrix4 m = new Matrix4();
        m.setToOrtho(0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, 1000);
        spriteBatch.setProjectionMatrix(m);

        stage.getViewport().update(w, h, true);
    }

    public void loadLevel(Level level, String levelName) {
        this.level = level;
        this.levelName = levelName;

        this.levelNameLabel.setText("Loaded " + levelName);
    }

    public static LevelPreview launch() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.samples = 8;
        config.width = 1024;
        config.height = 800;
        LevelPreview instance = new LevelPreview();
        new LwjglApplication(instance, config);
        return instance;
    }
}
