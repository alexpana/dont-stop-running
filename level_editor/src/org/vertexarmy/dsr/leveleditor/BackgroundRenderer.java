package org.vertexarmy.dsr.leveleditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import org.vertexarmy.dsr.core.assets.TextureRepository;
import org.vertexarmy.dsr.core.systems.RenderSystem;
import org.vertexarmy.dsr.game.Level;

/**
 * created by Alex
 * on 3/31/2015.
 */
public class BackgroundRenderer {

    private Level level;

    private Sprite sprite;

    public void setLevel(Level level) {
        this.level = level;
        loadSprite();
    }

    public void render() {
        if (level != null && sprite == null) {
        }
        loadSprite();

        if (sprite != null) {
            SpriteBatch batch = RenderSystem.instance().getSpriteBatch();

            sprite.setU(5 * Math.max(0, RenderSystem.instance().screenToWorld(new Vector2(0, 0)).x) / sprite.getWidth());
            sprite.setU2(5 + 5 * Math.max(0, RenderSystem.instance().screenToWorld(new Vector2(0, 0)).x) / sprite.getWidth());

            batch.begin();
            batch.draw(sprite, Math.max(0, RenderSystem.instance().screenToWorld(new Vector2(0, 0)).x), 0, sprite.getWidth(), RenderSystem.instance().screenToWorld(800));
            batch.end();
        }
    }

    private void loadSprite() {
        if (level != null && level.getBackgroundLayers().size() > 0) {
            for (Level.BackgroundLayer layer : level.getBackgroundLayers()) {
                if (layer.getTextureName() != null) {
                    TextureRegion texture = TextureRepository.instance().getTexture(layer.getTextureName());
                    sprite = new Sprite(texture);
                    sprite.getTexture().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
                    sprite.setU(5);
                    sprite.setV(5);

                    sprite.setSize(Gdx.graphics.getWidth(), 800);
                }
            }
        }
    }
}
