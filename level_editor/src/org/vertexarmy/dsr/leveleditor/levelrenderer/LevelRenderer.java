package org.vertexarmy.dsr.leveleditor.levelrenderer;

import org.vertexarmy.dsr.game.level.Level;

/**
 * Created by alex
 * on 01.04.2015.
 */
public class LevelRenderer {

    private Level level;

    private final LevelBackgroundRenderer backgroundRenderer = new LevelBackgroundRenderer();

    private final LevelTerrainRenderer terrainRenderer = new LevelTerrainRenderer();

    private final LevelSpritesRenderer spritesRenderer = new LevelSpritesRenderer();

    public void setLevel(Level level) {
        this.level = level;
        backgroundRenderer.setLevel(level);
        terrainRenderer.setLevel(level);
        spritesRenderer.setLevel(level);
    }

    public void render() {
        backgroundRenderer.render();

        spritesRenderer.renderBackgroundSprites();
        terrainRenderer.render();
        spritesRenderer.renderForegroundSprites();
    }

    public void reloadLevel() {
        backgroundRenderer.reloadLevel();
        terrainRenderer.reloadLevel();
        spritesRenderer.reloadLevel();
    }

}
