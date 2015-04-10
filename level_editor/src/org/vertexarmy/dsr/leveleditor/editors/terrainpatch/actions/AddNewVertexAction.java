package org.vertexarmy.dsr.leveleditor.editors.terrainpatch.actions;

import com.badlogic.gdx.math.Vector2;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.leveleditor.editors.terrainpatch.TerrainPatchEditor;

/**
 * Created by alex
 * on 25.03.2015.
 */
public class AddNewVertexAction implements ActionManager.Action {
    private final TerrainPatchEditor terrainPatchEditor;

    private final int index;

    private final Vector2 position;

    public AddNewVertexAction(TerrainPatchEditor terrainPatchEditor, int index, Vector2 position) {
        this.terrainPatchEditor = terrainPatchEditor;
        this.index = index;
        this.position = position;
    }

    @Override
    public void doAction() {
        terrainPatchEditor.addVertex(index, position);
    }

    @Override
    public void undoAction() {
        terrainPatchEditor.removeVertex(index);
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
