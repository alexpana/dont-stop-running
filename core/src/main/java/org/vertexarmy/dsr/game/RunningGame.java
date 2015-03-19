package org.vertexarmy.dsr.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.vertexarmy.dsr.graphics.SpriteFactory;
import org.vertexarmy.dsr.game.behavior.Behavior;
import org.vertexarmy.dsr.game.behavior.SpriteRotateBehavior;
import org.vertexarmy.dsr.game.behavior.SweepHorizontalBehavior;
import org.vertexarmy.dsr.game.behavior.SweepVerticalBehavior;
import org.vertexarmy.dsr.math.Polygon;
import org.vertexarmy.dsr.game.objects.GameObject;
import org.vertexarmy.dsr.game.objects.SpriteObject;

public class RunningGame extends Game {
    private Map<Tiles, TextureRegion> tiles = new HashMap<Tiles, TextureRegion>();
    private Camera camera;
    private final List<GameObject> gameObjectList = new ArrayList<GameObject>();
    private final Map<GameObject, List<Behavior>> behaviors = new HashMap<GameObject, List<Behavior>>();
    public static PolygonSpriteBatch PSB;
    private static SpriteBatch SB;
    private World physicsWorld;

    @Override
    public void create() {
        camera = new OrthographicCamera(640, 480);
        camera.update();

        SB = new SpriteBatch();
        PSB = new PolygonSpriteBatch(128, createShaderProgram());
        PSB.setShader(createDefaultShader());

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
    }

    private ShaderProgram createShaderProgram() {
        return null;
    }

    private void addBehavior(GameObject object, Behavior behavior) {
        if (!behaviors.containsKey(object)) {
            behaviors.put(object, new ArrayList<Behavior>());
        }

        behaviors.get(object).add(behavior);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.22f, 0.23f, 0.23f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        Polygon polygon = new Polygon(new float[]{0, 0, 0, 70, 50, 120, 80, 105, 100, 105, 100, 0});

        PolygonSprite sprite1 = SpriteFactory.getInstance().createSprite(polygon);
        PolygonSprite sprite2 = SpriteFactory.getInstance().createSprite(polygon);
        PolygonSprite sprite3 = SpriteFactory.getInstance().createSprite(polygon);

        sprite2.setPosition(100, 200);
        sprite3.setPosition(300, 300);
        sprite3.setRotation(90f);


        PSB.begin();
        sprite1.draw(PSB);
        sprite2.draw(PSB);
        sprite3.draw(PSB);
        PSB.end();


//        physicsWorld = new World(new Vector2(0, -98f), true);
//
//        // Now create a BodyDefinition.  This defines the physics objects type and position in the simulation
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.DynamicBody;
//
//        bodyDef.position.set(sprite.getX(), sprite.getY());
//
//        // Create a body in the physicsWorld using our definition
//        Body body = physicsWorld.createBody(bodyDef);
//
//        // Now define the dimensions of the physics shape
//        PolygonShape shape = new PolygonShape();
//        // We are a box, so this makes sense, no?
//        // Basically set the physics polygon to a box with the same dimensions as our sprite
//        shape.set(vertices);
//
//        // FixtureDef is a confusing expression for physical properties
//        // Basically this is where you, in addition to defining the shape of the body
//        // you also define it's properties like density, restitution and others we will see shortly
//        // If you are wondering, density and area are used to calculate over all mass
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape = shape;
//        fixtureDef.density = 1f;
//
//        Fixture fixture = body.createFixture(fixtureDef);
//
//        // Shape is the only disposable of the lot, so get rid of it
//        shape.dispose();
    }

    @Override
    public void resize(int w, int h) {
        camera = new OrthographicCamera(w, h);
    }


    private ShaderProgram createDefaultShader() {
        String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
                + "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
                + "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
                + "uniform mat4 u_projTrans;\n" //
                + "varying vec4 v_color;\n" //
                + "varying vec2 v_texCoords;\n" //
                + "\n" //
                + "void main()\n" //
                + "{\n" //
                + "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
                + "   v_color.a = v_color.a * (255.0/254.0);\n" //
                + "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
                + "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
                + "}\n";
        String fragmentShader = "#ifdef GL_ES\n" //
                + "#define LOWP lowp\n" //
                + "precision mediump float;\n" //
                + "#else\n" //
                + "#define LOWP \n" //
                + "#endif\n" //
                + "varying LOWP vec4 v_color;\n" //
                + "varying vec2 v_texCoords;\n" //
                + "uniform sampler2D u_texture;\n" //
                + "void main()\n"//
                + "{\n" //
                + "  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n" //
                + "}";

        ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
        if (!shader.isCompiled())
            throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
        return shader;
    }
}
