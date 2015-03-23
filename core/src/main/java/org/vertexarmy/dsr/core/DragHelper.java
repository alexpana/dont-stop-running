package org.vertexarmy.dsr.core;

import com.badlogic.gdx.math.Vector2;
import lombok.Getter;

/**
 * created by Alex
 * on 3/23/2015.
 */
public class DragHelper {
    @Getter
    private final Vector2 dragStartPosition = new Vector2(0, 0);

    @Getter
    private final Vector2 lastPosition = new Vector2(0, 0);

    private final Vector2 tmp = new Vector2(0, 0);

    @Getter
    private boolean isDragging = false;

    public void beginDrag(Vector2 dragStartPosition) {
        beginDrag(dragStartPosition.x, dragStartPosition.y);
        notifyMouseMoved(dragStartPosition);
    }

    public void beginDrag(float x, float y) {
        this.dragStartPosition.set(x, y);
        isDragging = true;
    }

    public void endDrag() {
        this.dragStartPosition.set(0, 0);
        isDragging = false;
    }

    public void reset(Vector2 resetPosition) {
        reset(resetPosition.x, resetPosition.y);
    }

    public void reset(float x, float y) {
        dragStartPosition.set(x, y);
    }

    public void notifyMouseMoved(Vector2 mousePosition) {
        lastPosition.set(mousePosition.x, mousePosition.y);
    }

    public void notifyMouseMoved(float x, float y) {
        lastPosition.set(x, y);
    }

    public Vector2 getDragOffset(Vector2 currentPosition) {
        return getDragOffset(currentPosition.x, currentPosition.y);
    }

    public Vector2 getDragOffset(float x, float y) {
        if (isDragging) {
            return tmp.set(x, y).sub(dragStartPosition);
        } else {
            return Vector2.Zero;
        }
    }
}
