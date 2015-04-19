package org.vertexarmy.dsr.leveleditor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.google.common.collect.ImmutableList;
import javax.annotation.Nonnull;
import org.vertexarmy.dsr.core.assets.TextureRepository;
import org.vertexarmy.dsr.game.level.LevelSprite;
import org.vertexarmy.dsr.math.Polygon;

/**
 * created by Alex
 * on 17-Apr-2015.
 */
public class LevelSpriteUtils {

    private static final Polygon NULL_POLYGON = new Polygon(ImmutableList.of(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f));

    private static final Polygon UNIT_SQUARE_POLYGON = new Polygon(ImmutableList.of(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f));

    @Nonnull
    public static Polygon getSpriteBounds(LevelSprite sprite) {
        TextureRegion region = TextureRepository.instance().getTexture(sprite.getTextureName());
        if (region == null) {
            return (Polygon) NULL_POLYGON.copy();
        }

        Polygon bounds = (Polygon) UNIT_SQUARE_POLYGON.copy();

        float width = region.getRegionWidth() * sprite.getScale().x;
        float height = region.getRegionHeight() * sprite.getScale().y;

        bounds.scale(new Vector2(width, height));
        bounds.rotate(sprite.getRotation());
        bounds.translate(sprite.getPosition());

        return bounds;
    }
}
