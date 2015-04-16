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
public class AlignHandlersVerticallyAction extends ActionManager.ActionAdapter {
    private final TerrainPatchEditTool editorTool;

    private final List<Integer> vertexHandlersIndices = Lists.newArrayList();

    private final List<Vector2> originalVertexPositions = Lists.newArrayList();

    public AlignHandlersVerticallyAction(TerrainPatchEditTool editor, List<Integer> vertexHandlersIndices) {
        editorTool = editor;
        this.vertexHandlersIndices.addAll(vertexHandlersIndices);

        for (Integer handler : vertexHandlersIndices) {
            originalVertexPositions.add(editor.getVertex(handler));
        }
    }

    @Override
    public void doAction() {
        float medianX = 0;
        List<VertexHandler> selectedHandlers = editorTool.getSelectedHandlers();
        if (!selectedHandlers.isEmpty()) {
            for (VertexHandler handler : selectedHandlers) {
                medianX += editorTool.getVertex(handler).x;
            }

            medianX /= selectedHandlers.size();
            for (VertexHandler handler : selectedHandlers) {
                Vector2 originalPosition = editorTool.getVertex(handler);
                editorTool.setVertex(handler, medianX, originalPosition.y);
            }
        }
    }

    @Override
    public void undoAction() {
        for (int i = 0; i < vertexHandlersIndices.size(); ++i) {
            VertexHandler vertexHandler = editorTool.findHandlerByIndex(vertexHandlersIndices.get(i));
            editorTool.setVertex(vertexHandler, originalVertexPositions.get(i));
        }
    }

    @Override
    public boolean isValid() {
        if (vertexHandlersIndices.size() < 2) {
            return false;
        }

        for (Integer handlerIndex : vertexHandlersIndices) {
            if (editorTool.getVertex(handlerIndex).x != editorTool.getVertex(vertexHandlersIndices.get(0)).x) {
                return true;
            }
        }

        return false;
    }
}
