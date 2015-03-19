package org.vertexarmy.dsr.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * created by Alex
 * on 2/2/2015.
 */
public class SpriteObject extends GameObject {
    private Sprite sprite;

    public SpriteObject(Sprite sprite) {
        this.sprite = new Sprite(sprite);
    }

    public SpriteObject(Sprite sprite, Vector2 position) {
        this.sprite = new Sprite(sprite);
        this.position = position;
    }

    @Override
    public void update(float time) {
    }

    @Override
    public void render(Batch batch) {
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }

    public Sprite getSprite() {
        return sprite;
    }
}
