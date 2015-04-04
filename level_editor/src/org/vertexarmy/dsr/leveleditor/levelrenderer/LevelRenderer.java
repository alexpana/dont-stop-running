package org.vertexarmy.dsr.leveleditor.levelrenderer;

import org.vertexarmy.dsr.game.level.Level;

/**
 * Created by alex
 * on 01.04.2015.
 */
public class LevelRenderer {
    private final BackgroundRenderer backgroundRenderer = new BackgroundRenderer();

    private final TerrainRenderer terrainRenderer = new TerrainRenderer();

    private final SpritesRenderer spritesRenderer = new SpritesRenderer();

    public void setLevel(Level level) {
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
