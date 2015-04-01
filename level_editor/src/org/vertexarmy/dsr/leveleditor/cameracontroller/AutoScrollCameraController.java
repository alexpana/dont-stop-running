package org.vertexarmy.dsr.leveleditor.cameracontroller;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import lombok.Setter;
import org.vertexarmy.dsr.core.component.UpdateComponent;
import org.vertexarmy.dsr.core.systems.RenderSystem;

/**
 * Created by alex
 * on 01.04.2015.
 */
public class AutoScrollCameraController implements UpdateComponent {

    @Setter
    private float speedX = 0.0f;

    private float speedY = 0.0f;

    private final Vector3 tmp = new Vector3();

    public void increaseSpeed() {
        speedX += 1.5f;
    }

    public void decreaseSpeed() {
        speedX -= 1.5f;
    }

    public void resetSpeed() {
        speedX = 0.0f;
    }

    @Override
    public void update() {
        Camera camera = RenderSystem.instance().getCamera();
        camera.translate(speedX, speedY, 0);
        camera.view.setToLookAt(camera.position, tmp.set(camera.position).add(camera.direction), camera.up);
    }
}
