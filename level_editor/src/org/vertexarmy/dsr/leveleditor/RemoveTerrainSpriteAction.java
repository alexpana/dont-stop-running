package org.vertexarmy.dsr.leveleditor;

import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.game.level.Level;
import org.vertexarmy.dsr.game.level.LevelSprite;

/**
 * created by Alex
 * on 08-Apr-2015.
 */
public class RemoveTerrainSpriteAction extends ActionManager.ActionAdapter {
    private final Level level;
    private final LevelSprite sprite;

    public RemoveTerrainSpriteAction(Level level, LevelSprite sprite) {
        this.level = level;
        this.sprite = sprite;
    }

    @Override
    public void doAction() {
        level.getLevelSprites().remove(sprite);
    }

    @Override
    public void undoAction() {
        level.getLevelSprites().add(sprite);
    }

    @Override
    public boolean isValid() {
        return sprite != null;
    }
}
