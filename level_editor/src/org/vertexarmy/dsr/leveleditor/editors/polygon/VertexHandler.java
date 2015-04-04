package org.vertexarmy.dsr.leveleditor.editors.polygon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * created by Alex
 * on 3/23/2015.
 */
@RequiredArgsConstructor
public class VertexHandler {
    private static final int MAGNIFIED_SIZE = 10;

    private static final int DEFAULT_SIZE = 6;

    @Getter
    private boolean dragged = false;

    @Getter
    private boolean hovered = false;

    @Getter
    private boolean selected = false;

    @Getter
    private float hitSize = DEFAULT_SIZE;

    @Getter
    private final int vertexIndex;

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
        update();
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        update();
    }

    public void setDragged(boolean dragged) {
        this.dragged = dragged;
        update();
    }

    private void update() {
        if (hovered || dragged || selected) {
            hitSize = MAGNIFIED_SIZE;
        } else {
            hitSize = DEFAULT_SIZE;
        }
    }
}
