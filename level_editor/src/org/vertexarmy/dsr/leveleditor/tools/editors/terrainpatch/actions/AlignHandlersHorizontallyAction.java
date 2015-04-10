package org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch.actions;

import com.badlogic.gdx.math.Vector2;
import com.beust.jcommander.internal.Lists;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch.TerrainPatchEditor;
import org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch.VertexHandler;

import java.util.List;

/**
 * Created by alex
 * on 24.03.2015.
 */
public class AlignHandlersHorizontallyAction extends ActionManager.ActionAdapter {
    private final TerrainPatchEditor terrainPatchEditor;

    private final List<VertexHandler> selectedVertexHandlers = Lists.newArrayList();

    private final List<Vector2> originalVertexPositions = Lists.newArrayList();

    public AlignHandlersHorizontallyAction(TerrainPatchEditor editor, List<VertexHandler> selectedVertexHandlers) {
        terrainPatchEditor = editor;
        this.selectedVertexHandlers.addAll(selectedVertexHandlers);

        for (VertexHandler handler : selectedVertexHandlers) {
            originalVertexPositions.add(editor.getVertex(handler));
        }
    }

    @Override
    public void doAction() {
        float medianY = 0;
        List<VertexHandler> selectedHandlers = terrainPatchEditor.getSelectedHandlers();
        if (!selectedHandlers.isEmpty()) {
            for (VertexHandler handler : selectedHandlers) {
                medianY += terrainPatchEditor.getVertex(handler).y;
            }

            medianY /= selectedHandlers.size();
            for (VertexHandler handler : selectedHandlers) {
                Vector2 originalPosition = terrainPatchEditor.getVertex(handler);
                terrainPatchEditor.setVertex(handler, originalPosition.x, medianY);
            }
        }
    }

    @Override
    public void undoAction() {
        for (int i = 0; i < selectedVertexHandlers.size(); ++i) {
            terrainPatchEditor.setVertex(selectedVertexHandlers.get(i), originalVertexPositions.get(i));
        }
    }

    @Override
    public boolean isValid() {
        if (selectedVertexHandlers.size() < 2) {
            return false;
        }

        for (VertexHandler handler : selectedVertexHandlers) {
            if (terrainPatchEditor.getVertex(handler).y != terrainPatchEditor.getVertex(selectedVertexHandlers.get(0)).y) {
                return true;
            }
        }

        return false;
    }
}
