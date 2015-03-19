package org.vertexarmy.dsr.game.behavior;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * created by Alex
 * on 2/2/2015.
 */
public class SpriteRotateBehavior implements Behavior<Sprite> {
    private float speed;

    public SpriteRotateBehavior(float speed) {
        this.speed = speed;
    }

    public SpriteRotateBehavior() {
        speed = 1.0f;
    }

    @Override
    public void update(Sprite object, float deltaTime) {
        object.rotate(deltaTime / speed);
    }
}
