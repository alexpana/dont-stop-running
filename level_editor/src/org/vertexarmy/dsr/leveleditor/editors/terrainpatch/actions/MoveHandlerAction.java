package org.vertexarmy.dsr.leveleditor.editors.terrainpatch.actions;

import com.badlogic.gdx.math.Vector2;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.leveleditor.editors.terrainpatch.TerrainPatchEditor;
import org.vertexarmy.dsr.leveleditor.editors.terrainpatch.VertexHandler;

/**
 * Created by alex
 * on 24.03.2015.
 */
public class MoveHandlerAction extends ActionManager.ActionAdapter {
    private final Vector2 originalPosition = new Vector2();

    private final Vector2 newPosition = new Vector2();

    private final VertexHandler vertexHandler;

    private final TerrainPatchEditor terrainPatchEditor;

    public MoveHandlerAction(TerrainPatchEditor terrainPatchEditor, VertexHandler vertexHandler, Vector2 originalPosition, Vector2 newPosition) {
        this.terrainPatchEditor = terrainPatchEditor;
        this.vertexHandler = vertexHandler;
        this.originalPosition.set(originalPosition);
        this.newPosition.set(newPosition);
    }


    @Override
    public void doAction() {
        terrainPatchEditor.setVertex(vertexHandler, newPosition.x, newPosition.y);
    }

    @Override
    public void undoAction() {
        terrainPatchEditor.setVertex(vertexHandler, originalPosition.x, originalPosition.y);
    }

    @Override
    public boolean isValid() {
        return Vector2.dst2(originalPosition.x, originalPosition.y, newPosition.x, newPosition.y) >= 1;
    }
}
