package org.vertexarmy.dsr.leveleditor.levelrenderer;

import lombok.Getter;
import lombok.Setter;
import org.vertexarmy.dsr.game.level.Level;

/**
 * Created by alex
 * on 01.04.2015.
 */
public class LevelRenderer {
    private final BackgroundRenderer backgroundRenderer = new BackgroundRenderer();

    private final TerrainRenderer terrainRenderer = new TerrainRenderer();

    private final SpritesRenderer spritesRenderer = new SpritesRenderer();

    @Getter
    @Setter
    private boolean showBackground = true;

    @Getter
    @Setter
    private boolean showLevelSprites = true;

    @Getter
    @Setter
    private boolean showTerrainPatches = true;

    public void setLevel(Level level) {
        backgroundRenderer.setLevel(level);
        terrainRenderer.setLevel(level);
        spritesRenderer.setLevel(level);
    }

    public void render() {
        if (showBackground) {
            backgroundRenderer.render();
        }

        if (showLevelSprites) {
            spritesRenderer.renderBackgroundSprites();
        }
        if (showTerrainPatches) {
            terrainRenderer.render();
        }
        if (showLevelSprites) {
            spritesRenderer.renderForegroundSprites();
        }
    }

    public void reloadLevel() {
        backgroundRenderer.reloadLevel();
        terrainRenderer.reloadLevel();
        spritesRenderer.reloadLevel();
    }

}
