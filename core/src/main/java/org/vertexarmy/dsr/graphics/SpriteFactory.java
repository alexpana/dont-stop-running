package org.vertexarmy.dsr.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.vertexarmy.dsr.math.EarClippingTriangulation;
import org.vertexarmy.dsr.math.Polygon;

/**
 * created by Alex
 * on 3/6/2015.
 */
public class SpriteFactory {
    private static SpriteFactory INSTANCE = null;

    private SpriteFactory() {
    }

    public static SpriteFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SpriteFactory();
        }

        return INSTANCE;
    }

    public PolygonSprite createSprite(Polygon polygon) {
        short[] indices = EarClippingTriangulation.triangulate(polygon);

        PolygonRegion polygonRegion = new PolygonRegion(
                new TextureRegion(GraphicsUtils.getColorTexture(Color.WHITE), 1, 1),
                polygon.getVertices(),
                indices);

        PolygonSprite sprite = new PolygonSprite(polygonRegion);
        sprite.setScale(1, 1);
        sprite.setPosition(0, 0);

        return sprite;
    }

    public PolygonSprite createSprite(Polygon polygon, int triangleIndex) {
        short[] indices = EarClippingTriangulation.triangulate(polygon);

        short[] indices3 = new short[3];
        indices3[0] = indices[triangleIndex * 3];
        indices3[1] = indices[triangleIndex * 3 + 1];
        indices3[2] = indices[triangleIndex * 3 + 2];

        PolygonRegion polygonRegion = new PolygonRegion(
                new TextureRegion(GraphicsUtils.getColorTexture(Color.WHITE), 1, 1),
                polygon.getVertices(),
                indices3);

        PolygonSprite sprite = new PolygonSprite(polygonRegion);
        sprite.setScale(1, 1);
        sprite.setPosition(0, 0);

        return sprite;
    }
}
