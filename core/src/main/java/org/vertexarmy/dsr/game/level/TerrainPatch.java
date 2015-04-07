package org.vertexarmy.dsr.game.level;

import lombok.Getter;
import lombok.Setter;
import org.vertexarmy.dsr.graphics.TextureOverlay;
import org.vertexarmy.dsr.math.Polygon;

/**
 * created by Alex
 * on 07-Apr-2015.
 */
public class TerrainPatch {
    @Getter
    @Setter
    private Polygon shape;

    @Getter
    @Setter
    private TextureOverlay textureOverlay;

    public TerrainPatch(Polygon shape) {
        this.shape = shape;
        textureOverlay = new TextureOverlay();
    }
}
