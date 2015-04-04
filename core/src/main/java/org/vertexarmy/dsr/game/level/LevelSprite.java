package org.vertexarmy.dsr.game.level;

import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
    private Vector2 position;

    @Getter
    @Setter
    private float rotation;

    @Getter
    @Setter
    private Vector2 scale;

    @Getter
    @Setter
    private int zOrder;

    @Getter
    @Setter
    private boolean isForeground;
}
