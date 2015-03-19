package org.vertexarmy.dsr.level_editor;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.Setter;

/**
 * created by Alex
 * on 3/16/2015.
 */
public class CameraController extends InputAdapter {
    @Setter
    @Getter
    private Camera managedCamera;

    private Vector2 dragPosition = new Vector2(0, 0);

    public CameraController() {
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        managedCamera.translate(dragPosition.x - screenX, dragPosition.y - screenY, 0);
        managedCamera.update();
        dragPosition.set(screenX, screenY);
        return true;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        dragPosition.set(x, y);
        return true;
    }
}
