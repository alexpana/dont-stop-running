package org.vertexarmy.dsr.leveleditor.levelrenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.beust.jcommander.internal.Maps;
import org.vertexarmy.dsr.core.assets.TextureRepository;
import org.vertexarmy.dsr.core.systems.RenderSystem;
import org.vertexarmy.dsr.game.level.BackgroundLayer;
import org.vertexarmy.dsr.game.level.Level;

import java.util.Map;

/**
 * created by Alex
 * on 3/31/2015.
 */
public class BackgroundRenderer {

    private Level level;

    private final Map<BackgroundLayer.Type, Sprite> backgroundSprites = Maps.newHashMap();

    public void setLevel(Level level) {
        this.level = level;
        reloadLevel();
    }

    public void render() {
        RenderSystem renderSystem = RenderSystem.instance();
        SpriteBatch batch = renderSystem.getSpriteBatch();

        batch.begin();

        for (BackgroundLayer.Type layerType : BackgroundLayer.Type.values()) {
            Sprite sprite = backgroundSprites.get(layerType);
            if (sprite == null) {
                continue;
            }

            float parallaxSpeedScale = level.getBackgroundLayerByType(layerType).getParallaxSpeedScale();

            sprite.setSize(renderSystem.screenToWorld(Gdx.graphics.getWidth()), 800);

            float spriteScaling = 800.0f / sprite.getTexture().getHeight();

            float uOffset = Math.max(0, renderSystem.screenToWorld(new Vector2(0, 0)).x) / sprite.getTexture().getWidth() / spriteScaling;
            uOffset *= parallaxSpeedScale;

            float uSize = sprite.getWidth() / sprite.getTexture().getWidth() / spriteScaling;

            sprite.setU(uOffset);
            sprite.setU2(uOffset + uSize);


            float x = Math.max(0, renderSystem.screenToWorld(new Vector2(0, 0)).x);
            float y = 0;
            float w = sprite.getWidth();
            float h = 800;

            batch.draw(sprite, x, y, w, h);
        }

        batch.end();
    }

    public void reloadLevel() {
        backgroundSprites.clear();
        for (BackgroundLayer.Type layerType : BackgroundLayer.Type.values()) {
            BackgroundLayer layer = level.getBackgroundLayerByType(layerType);
            if (layer != null && layer.getTextureName() != null) {
                TextureRegion texture = TextureRepository.instance().getTexture(layer.getTextureName());

                Sprite sprite = new Sprite(texture);
                sprite.getTexture().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
                sprite.setU(0);
                sprite.setV(0);
                sprite.setV2(1); //800.0f / sprite.getTexture().getHeight());

                backgroundSprites.put(layer.getType(), sprite);
            }
        }
    }
}
