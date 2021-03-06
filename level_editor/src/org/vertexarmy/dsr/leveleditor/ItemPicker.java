package org.vertexarmy.dsr.leveleditor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.vertexarmy.dsr.core.Log;
import org.vertexarmy.dsr.core.assets.TextureRepository;
import org.vertexarmy.dsr.core.systems.RenderSystem;
import org.vertexarmy.dsr.game.level.Level;
import org.vertexarmy.dsr.game.level.LevelSprite;
import org.vertexarmy.dsr.game.level.TerrainPatch;
import org.vertexarmy.dsr.math.Algorithms;
import org.vertexarmy.dsr.math.Polygon;

/**
 * created by Alex
 * on 04-Apr-2015.
 */
public class ItemPicker {

    enum ItemType {
        TERRAIN_POLYGON,
        LEVEL_SPRITE,
        NONE
    }

    @RequiredArgsConstructor
    public static class PickResult {

        @Getter
        private final ItemType type;

        @Getter
        private final Object object;

        public static final PickResult NOTHING = new PickResult(ItemType.NONE, null);
    }

    private final static Log log = Log.create();

    public static PickResult pickObject(Level level, int screenX, int screenY) {
        LevelSprite foregroundSprite = pickForegroundSprite(level, screenX, screenY);
        if (foregroundSprite != null) {
            return new PickResult(ItemType.LEVEL_SPRITE, foregroundSprite);
        }

        TerrainPatch pickedPolygon = pickTerrainPolygon(level, screenX, screenY);
        if (pickedPolygon != null) {
            return new PickResult(ItemType.TERRAIN_POLYGON, pickedPolygon);
        }

        LevelSprite backgroundSprite = pickBackgroundSprite(level, screenX, screenY);
        if (backgroundSprite != null) {
            return new PickResult(ItemType.LEVEL_SPRITE, backgroundSprite);
        }

        return PickResult.NOTHING;
    }

    private static TerrainPatch pickTerrainPolygon(Level level, int screenX, int screenY) {
        Vector2 mouseWorldPosition = RenderSystem.instance().screenToWorld(screenX, screenY);

        for (TerrainPatch terrainPatch : level.getTerrainPatches()) {
            if (Algorithms.polygonContainsVertex(mouseWorldPosition, terrainPatch.getShape())) {
                log.debug("Picked a polygon");
                return terrainPatch;
            }
        }

        return null;
    }

    private static LevelSprite pickForegroundSprite(Level level, int screenX, int screenY) {
        Vector2 mouseWorldPosition = RenderSystem.instance().screenToWorld(screenX, screenY);

        for (LevelSprite levelSprite : level.getLevelSprites()) {
            if (levelSprite.isForeground() && spriteContainsPoint(levelSprite, mouseWorldPosition)) {
                log.debug("Picked a foreground sprite");
                return levelSprite;
            }
        }

        return null;
    }

    private static LevelSprite pickBackgroundSprite(Level level, int screenX, int screenY) {
        Vector2 mouseWorldPosition = RenderSystem.instance().screenToWorld(screenX, screenY);

        for (LevelSprite levelSprite : level.getLevelSprites()) {
            if (!levelSprite.isForeground() && spriteContainsPoint(levelSprite, mouseWorldPosition)) {
                log.debug("Picked a background sprite");
                return levelSprite;
            }
        }

        return null;
    }

    public static boolean spriteContainsPoint(LevelSprite sprite, Vector2 point) {
        return LevelSpriteUtils.getSpriteBounds(sprite).containsVertex(point);
    }
}
