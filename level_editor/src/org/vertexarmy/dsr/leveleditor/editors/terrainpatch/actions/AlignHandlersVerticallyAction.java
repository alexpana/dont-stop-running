package org.vertexarmy.dsr.leveleditor.editors.terrainpatch.actions;

import com.badlogic.gdx.math.Vector2;
import com.beust.jcommander.internal.Lists;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.leveleditor.editors.terrainpatch.TerrainPatchEditor;
import org.vertexarmy.dsr.leveleditor.editors.terrainpatch.VertexHandler;

import java.util.List;

/**
 * Created by alex
 * on 24.03.2015.
 */
public class AlignHandlersVerticallyAction extends ActionManager.ActionAdapter {
    private final TerrainPatchEditor terrainPatchEditor;

    private final List<VertexHandler> selectedVertexHandlers = Lists.newArrayList();

    private final List<Vector2> originalVertexPositions = Lists.newArrayList();

    public AlignHandlersVerticallyAction(TerrainPatchEditor editor, List<VertexHandler> selectedVertexHandlers) {
        terrainPatchEditor = editor;
        this.selectedVertexHandlers.addAll(selectedVertexHandlers);

        for (VertexHandler handler : selectedVertexHandlers) {
            originalVertexPositions.add(editor.getVertex(handler));
        }
    }

    @Override
    public void doAction() {
        float medianX = 0;
        List<VertexHandler> selectedHandlers = terrainPatchEditor.getSelectedHandlers();
        if (!selectedHandlers.isEmpty()) {
            for (VertexHandler handler : selectedHandlers) {
                medianX += terrainPatchEditor.getVertex(handler).x;
            }

            medianX /= selectedHandlers.size();
            for (VertexHandler handler : selectedHandlers) {
                Vector2 originalPosition = terrainPatchEditor.getVertex(handler);
                terrainPatchEditor.setVertex(handler, medianX, originalPosition.y);
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
            if (terrainPatchEditor.getVertex(handler).x != terrainPatchEditor.getVertex(selectedVertexHandlers.get(0)).x) {
                return true;
            }
        }

        return false;
    }
}
