package org.vertexarmy.dsr.graphics;

import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.Setter;
import org.vertexarmy.dsr.core.Precision;

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
    @Precision(value = 0.01f)
    private Vector2 textureOffset = new Vector2(0, 0);

    @Getter
    @Setter
    @Precision(value = 0.1f)
    private Vector2 textureScale = new Vector2(1, 1);

    @Getter
    @Setter
    @Precision(value = 0.1f)
    private float textureRotation = 0;
}
