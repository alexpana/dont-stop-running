package org.vertexarmy.dsr.game.level;

import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.vertexarmy.dsr.core.Precision;

/**
 * ceated by Alex
 * on 4/4/2015.
 */
@AllArgsConstructor
public class LevelSprite {
    @Getter
    @Setter
    private String textureName;

    @Getter
    @Setter
    @Precision(value = 1)
    private Vector2 position;

    @Getter
    @Setter
    @Precision(value = 0.1f)
    private float rotation;

    @Getter
    @Setter
    @Precision(value = 0.1f)
    private Vector2 scale;

    @Getter
    @Setter
    @Precision(value = 1)
    private int zOrder;

    @Getter
    @Setter
    private boolean isForeground;
}
