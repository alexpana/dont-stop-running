package org.vertexarmy.dsr.core.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import org.vertexarmy.dsr.core.Log;
import org.vertexarmy.dsr.core.assets.ShaderRepository;
import org.vertexarmy.dsr.core.component.RenderComponent;

/**
 * created by Alex
 * on 3/21/2015.
 */
public class RenderSystem {
    private final static RenderSystem INSTANCE = new RenderSystem();

    private final Log log = Log.create();

    private final Multimap<RenderComponent.RenderList, RenderComponent> components = LinkedListMultimap.create();

    @Getter
    private Camera camera;

    @Getter
    private Camera standardCamera;

    private int viewportWidth = 800;

    private int viewportHeight = 600;

    @Getter
    private float zoom = 1.0f;

    @Getter
    private SpriteBatch spriteBatch;

    @Getter
    private PolygonSpriteBatch polygonSpriteBatch;

    @Getter
    private ShapeRenderer shapeRenderer;

    private RenderSystem() {
    }

    public static RenderSystem instance() {
        return INSTANCE;
    }

    public void initialize() {
        camera = new OrthographicCamera(800, 600);

        standardCamera = new OrthographicCamera(800, 600);

        shapeRenderer = new ShapeRenderer();

        polygonSpriteBatch = new PolygonSpriteBatch(128, ShaderRepository.instance().getShader(ShaderRepository.ShaderInstance.DEFAULT_POS_COL_TEX_PROJ));

        spriteBatch = new SpriteBatch();

        updateCameras();

        Gdx.gl.glClearColor(0.22f, 0.23f, 0.23f, 1);

        log.info("Initialized ok.");
    }

    public void addRenderComponent(RenderComponent component) {
        components.put(component.getRenderList(), component);
    }

    public void removeRenderComponent(RenderComponent component) {
        components.remove(component.getRenderList(), component);
    }

    public void update() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.setProjectionMatrix(camera.projection);
        spriteBatch.setTransformMatrix(camera.view);

        polygonSpriteBatch.setProjectionMatrix(camera.projection);
        polygonSpriteBatch.setTransformMatrix(camera.view);

        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);

        for (RenderComponent component : components.get(RenderComponent.RenderList.DEFAULT)) {
            component.render();
        }

        for (RenderComponent component : components.get(RenderComponent.RenderList.UI)) {
            component.render();
        }
    }

    public void setViewportSize(int width, int height) {
        viewportWidth = width;
        viewportHeight = height;
        updateCameras();
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
        updateCameras();
    }

    private void updateCameras() {
        float w = (viewportWidth / zoom);
        float h = (viewportHeight / zoom);
        camera.projection.setToOrtho(-w / 2, w / 2, -h / 2, h / 2, 0, 1000);
        standardCamera.projection.setToOrtho(-viewportWidth / 2, viewportWidth / 2, -viewportHeight / 2, viewportHeight / 2, 0, 1000);
    }

    public Vector2 screenToWorld(Vector2 screen) {
        Vector2 cameraPosition = new Vector2(camera.position.x, camera.position.y);
        Vector2 v = new Vector2(screen).sub(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2));
        v.y *= -1;
        return cameraPosition.add(v.scl(1 / zoom));
    }

    public Vector2 screenToWorldWithoutZoom(Vector2 screen) {
        Vector2 cameraPosition = new Vector2(camera.position.x, camera.position.y);
        Vector2 v = new Vector2(screen).sub(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2));
        v.y *= -1;
        return cameraPosition.add(v);
    }

    public float screenToWorld(float distance) {
        return distance * zoom;
    }
}
