package org.vertexarmy.dsr.leveleditor.cameracontroller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.vertexarmy.dsr.core.component.InputComponent;
import org.vertexarmy.dsr.core.systems.RenderSystem;
import org.vertexarmy.dsr.math.Algorithms;

/**
 * created by Alex
 * on 3/16/2015.
 */
public class UserPanningCameraController extends InputAdapter implements InputComponent {
    private static final float[] ZOOM_STEPS = new float[]{0.1f, 0.25f, 0.33f, 0.5f, 0.66f, 0.75f, 1.0f, 1.5f, 2f, 3f, 4f, 5f};

    private static final int DEFAULT_ZOOM_LEVEL_INDEX = 6;

    private final Vector3 tmp = new Vector3();

    private final Vector2 dragPosition = new Vector2(0, 0);

    private boolean enableCameraPanning = false;

    private int zoomStepIndex = DEFAULT_ZOOM_LEVEL_INDEX;

    private boolean enabled = false;

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!enabled || !enableCameraPanning) {
            return false;
        }

        Camera camera = RenderSystem.instance().getCamera();
        float zoomLevel = RenderSystem.instance().getZoom();
        if (camera != null) {
            camera.translate((dragPosition.x - screenX) / zoomLevel, (screenY - dragPosition.y) / zoomLevel, 0);
            camera.view.setToLookAt(camera.position, tmp.set(camera.position).add(camera.direction), camera.up);
        }
        dragPosition.set(screenX, screenY);
        return true;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        if (!enabled) {
            return false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            enableCameraPanning = true;
            dragPosition.set(x, y);
            return true;
        } else {
            enableCameraPanning = false;
            return false;
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        if (!enabled) {
            return false;
        }

        if (keycode == Input.Keys.SPACE) {
            enableCameraPanning = false;
            return true;
        }

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if (!enabled) {
            return false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            zoomStepIndex = (int) Algorithms.clamp(zoomStepIndex - Math.signum(amount), 0, ZOOM_STEPS.length - 1);
            RenderSystem.instance().setZoom(ZOOM_STEPS[zoomStepIndex]);
        }
        return false;
    }

    @Override
    public InputProcessor getInputAdapter() {
        return this;
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;

            if (!enabled) {
                enableCameraPanning = false;
            }
        }
    }
}
