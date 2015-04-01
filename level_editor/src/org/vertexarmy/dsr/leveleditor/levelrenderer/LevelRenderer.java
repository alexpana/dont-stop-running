package org.vertexarmy.dsr.leveleditor.levelrenderer;

import org.vertexarmy.dsr.game.Level;

/**
 * Created by alex
 * on 01.04.2015.
 */
public class LevelRenderer {

    private Level level;

    private final LevelBackgroundRenderer backgroundRenderer = new LevelBackgroundRenderer();

    private final LevelTerrainRenderer terrainRenderer = new LevelTerrainRenderer();

    public void setLevel(Level level) {
        this.level = level;
        backgroundRenderer.setLevel(level);
        terrainRenderer.setLevel(level);
    }

    public void render() {
        backgroundRenderer.render();
        terrainRenderer.render();
    }

    public void reloadLevel() {
        backgroundRenderer.reloadLevel();
        terrainRenderer.reloadLevel();
    }

}
