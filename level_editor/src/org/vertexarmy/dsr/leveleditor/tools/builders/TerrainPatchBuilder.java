package org.vertexarmy.dsr.leveleditor.tools.builders;

import org.vertexarmy.dsr.game.level.Level;
import org.vertexarmy.dsr.game.level.TerrainPatch;
import org.vertexarmy.dsr.math.Polygon;

/**
 * created by Alex
 * on 10-Apr-2015.
 */
public class TerrainPatchBuilder {
    public TerrainPatch addRectangleTerrainPatch(Level level, int x, int y, int w, int h) {
        TerrainPatch terrainPatch = new TerrainPatch(new Polygon(new float[]{
                x - w / 2, y + h / 2,
                x + w / 2, y + h / 2,
                x + w / 2, y - h / 2,
                x - w / 2, y - h / 2
        }));

        level.getTerrainPatches().add(terrainPatch);
        return terrainPatch;
    }
}
