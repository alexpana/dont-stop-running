package org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch.actions;

import com.badlogic.gdx.math.Vector2;
import com.beust.jcommander.internal.Lists;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch.TerrainPatchEditTool;
import org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch.VertexHandler;

import java.util.List;

/**
 * Created by alex
 * on 24.03.2015.
 */
public class AlignHandlersHorizontallyAction extends ActionManager.ActionAdapter {
    private final TerrainPatchEditTool editorTool;

    private final List<Integer> vertexHandlerIndices = Lists.newArrayList();

    private final List<Vector2> originalVertexPositions = Lists.newArrayList();

    public AlignHandlersHorizontallyAction(TerrainPatchEditTool editor, List<Integer> vertexHandlerIndices) {
        editorTool = editor;
        this.vertexHandlerIndices.addAll(vertexHandlerIndices);

        for (Integer handlerIndex : vertexHandlerIndices) {
            originalVertexPositions.add(editor.getVertex(handlerIndex));
        }
    }

    @Override
    public void doAction() {
        float medianY = 0;
        List<VertexHandler> selectedHandlers = editorTool.getSelectedHandlers();
        if (!selectedHandlers.isEmpty()) {
            for (VertexHandler handler : selectedHandlers) {
                medianY += editorTool.getVertex(handler).y;
            }

            medianY /= selectedHandlers.size();
            for (VertexHandler handler : selectedHandlers) {
                Vector2 originalPosition = editorTool.getVertex(handler);
                editorTool.setVertex(handler, originalPosition.x, medianY);
            }
        }
    }

    @Override
    public void undoAction() {
        for (int i = 0; i < vertexHandlerIndices.size(); ++i) {
            VertexHandler vertexHandler = editorTool.findHandlerByIndex(vertexHandlerIndices.get(i));
            editorTool.setVertex(vertexHandler, originalVertexPositions.get(i));
        }
    }

    @Override
    public boolean isValid() {
        if (vertexHandlerIndices.size() < 2) {
            return false;
        }

        for (Integer handler : vertexHandlerIndices) {
            if (editorTool.getVertex(handler).y != editorTool.getVertex(vertexHandlerIndices.get(0)).y) {
                return true;
            }
        }

        return false;
    }
}
