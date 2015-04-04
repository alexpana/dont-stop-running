package org.vertexarmy.dsr.leveleditor.levelrenderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.beust.jcommander.internal.Lists;
import org.vertexarmy.dsr.core.systems.RenderSystem;
import org.vertexarmy.dsr.game.level.Level;
import org.vertexarmy.dsr.graphics.SpriteFactory;
import org.vertexarmy.dsr.math.Polygon;

import java.util.List;

/**
 * Created by alex
 * on 01.04.2015.
 */
public class TerrainRenderer {
    private Level level;

    private final List<PolygonSprite> polygonSprites = Lists.newArrayList();

    public void setLevel(Level level) {
        this.level = level;
        reloadLevel();
    }

    public void render() {
        PolygonSpriteBatch polygonSpriteBatch = RenderSystem.instance().getPolygonSpriteBatch();
        polygonSpriteBatch.begin();
        for (PolygonSprite polygonSprite : polygonSprites) {
            polygonSprite.draw(polygonSpriteBatch);
        }
        polygonSpriteBatch.end();

    }

    public void reloadLevel() {
        polygonSprites.clear();
        for (Polygon polygon : level.getTerrainPatches()) {
            PolygonSprite terrainPolygonSprite = SpriteFactory.instance().createSprite(polygon);
            terrainPolygonSprite.setColor(Color.BLACK);
            polygonSprites.add(terrainPolygonSprite);
        }
    }
}
