package org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch.actions;

import com.badlogic.gdx.math.Vector2;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch.TerrainPatchEditTool;

/**
 * Created by alex
 * on 25.03.2015.
 */
public class AddNewVertexAction implements ActionManager.Action {
    private final TerrainPatchEditTool terrainPatchEditTool;

    private final int index;

    private final Vector2 position;

    public AddNewVertexAction(TerrainPatchEditTool terrainPatchEditTool, int index, Vector2 position) {
        this.terrainPatchEditTool = terrainPatchEditTool;
        this.index = index;
        this.position = position;
    }

    @Override
    public void doAction() {
        terrainPatchEditTool.addVertex(index, position);
    }

    @Override
    public void undoAction() {
        terrainPatchEditTool.removeVertex(index);
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
