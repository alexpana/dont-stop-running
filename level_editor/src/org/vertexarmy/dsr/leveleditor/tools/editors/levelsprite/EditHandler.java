package org.vertexarmy.dsr.leveleditor.tools.editors.levelsprite;

import lombok.Getter;
import lombok.Setter;

/**
 * created by Alex
 * on 04-Apr-2015.
 */
public class EditHandler {
    @Getter
    @Setter
    private boolean isHovered;

    @Getter
    @Setter
    private boolean isActive;

    public float getSize() {
        if (isHovered) {
            return 6;
        } else {
            return 8;
        }
    }
}
