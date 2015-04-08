package org.vertexarmy.dsr.leveleditor;

import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.game.level.Level;
import org.vertexarmy.dsr.game.level.TerrainPatch;

/**
 * created by Alex
 * on 08-Apr-2015.
 */
class RemoveTerrainPatchAction implements ActionManager.Action {
    private final TerrainPatch patchToRemove;
    private Level level;

    public RemoveTerrainPatchAction(Level level, TerrainPatch patchToRemove) {
        this.level = level;
        this.patchToRemove = patchToRemove;
    }

    @Override
    public void doAction() {
        level.getTerrainPatches().remove(patchToRemove);
    }

    @Override
    public void undoAction() {
        level.getTerrainPatches().add(patchToRemove);
    }

    @Override
    public boolean isValid() {
        return patchToRemove != null;
    }
}
