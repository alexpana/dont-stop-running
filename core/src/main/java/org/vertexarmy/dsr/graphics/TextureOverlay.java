package org.vertexarmy.dsr.graphics;

import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.Setter;

/**
 * created by Alex
 * on 07-Apr-2015.
 */
public class TextureOverlay {
    @Getter
    @Setter
    private String textureName;

    @Getter
    @Setter
    private Vector2 textureOffset = new Vector2(0, 0);

    @Getter
    @Setter
    private Vector2 textureScale = new Vector2(1, 1);

    @Getter
    @Setter
    private float textureRotation = 0;
}
