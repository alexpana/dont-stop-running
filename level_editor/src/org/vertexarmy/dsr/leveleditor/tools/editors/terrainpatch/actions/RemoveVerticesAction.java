package org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch.actions;

import com.badlogic.gdx.math.Vector2;
import com.beust.jcommander.internal.Lists;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch.TerrainPatchEditor;

import java.util.List;

/**
 * created by Alex
 * on 3/25/2015.
 */
public class RemoveVerticesAction implements ActionManager.Action {
    private final TerrainPatchEditor editor;

    private final List<Vector2> allVertices = Lists.newArrayList();

    private final List<Vector2> remainingVertices = Lists.newArrayList();

    public RemoveVerticesAction(TerrainPatchEditor editor, List<Vector2> allVertices, List<Vector2> remainingVertices) {
        this.editor = editor;
        this.allVertices.addAll(allVertices);
        this.remainingVertices.addAll(remainingVertices);
    }

    @Override
    public void doAction() {
        editor.setVertices(remainingVertices);
    }

    @Override
    public void undoAction() {
        editor.setVertices(allVertices);
    }

    @Override
    public boolean isValid() {
        return allVertices.size() > remainingVertices.size();
    }
}
