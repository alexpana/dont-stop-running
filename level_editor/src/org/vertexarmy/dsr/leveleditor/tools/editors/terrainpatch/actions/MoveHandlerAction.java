package org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch.actions;

import com.badlogic.gdx.math.Vector2;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch.TerrainPatchEditTool;

/**
 * Created by alex
 * on 24.03.2015.
 */
public class MoveHandlerAction extends ActionManager.ActionAdapter {
    private final Vector2 originalPosition = new Vector2();

    private final Vector2 newPosition = new Vector2();

    private final Integer vertexHandlerIndex;

    private final TerrainPatchEditTool editorTool;

    public MoveHandlerAction(TerrainPatchEditTool editorTool, Integer vertexHandlerIndex, Vector2 originalPosition, Vector2 newPosition) {
        this.editorTool = editorTool;
        this.vertexHandlerIndex = vertexHandlerIndex;
        this.originalPosition.set(originalPosition);
        this.newPosition.set(newPosition);
    }


    @Override
    public void doAction() {
        editorTool.setVertex(editorTool.findHandlerByIndex(vertexHandlerIndex), newPosition.x, newPosition.y);
    }

    @Override
    public void undoAction() {
        editorTool.setVertex(editorTool.findHandlerByIndex(vertexHandlerIndex), originalPosition.x, originalPosition.y);
    }

    @Override
    public boolean isValid() {
        return Vector2.dst2(originalPosition.x, originalPosition.y, newPosition.x, newPosition.y) >= 1;
    }
}
