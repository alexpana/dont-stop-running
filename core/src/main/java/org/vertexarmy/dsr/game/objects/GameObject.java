package org.vertexarmy.dsr.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

/**
 * created by Alex
 * on 2/2/2015.
 */
public abstract class GameObject {
    public Vector2 position = new Vector2(0, 0);

    abstract public void update(float time);

    abstract public void render(Batch batch);
}
