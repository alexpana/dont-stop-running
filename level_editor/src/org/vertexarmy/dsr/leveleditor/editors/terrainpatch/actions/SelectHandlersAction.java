package org.vertexarmy.dsr.leveleditor.editors.terrainpatch.actions;

import com.beust.jcommander.internal.Lists;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.leveleditor.editors.terrainpatch.TerrainPatchEditor;
import org.vertexarmy.dsr.leveleditor.editors.terrainpatch.VertexHandler;

import java.util.List;

/**
 * Created by alex
 * on 24.03.2015.
 */
public class SelectHandlersAction extends ActionManager.ActionAdapter {
    private final TerrainPatchEditor terrainPatchEditor;

    private final List<VertexHandler> selectedHandlers = Lists.newArrayList();

    public SelectHandlersAction(TerrainPatchEditor terrainPatchEditor, List<VertexHandler> selectedHandlers) {
        this.terrainPatchEditor = terrainPatchEditor;
        this.selectedHandlers.addAll(selectedHandlers);
    }

    @Override
    public void doAction() {
        for (VertexHandler vertexHandler : selectedHandlers) {
            terrainPatchEditor.findHandlerByIndex(vertexHandler.getVertexIndex()).setSelected(true);
        }
    }

    @Override
    public void undoAction() {
        for (VertexHandler vertexHandler : selectedHandlers) {
            terrainPatchEditor.findHandlerByIndex(vertexHandler.getVertexIndex()).setSelected(false);
        }
    }

    @Override
    public boolean isValid() {
        return !selectedHandlers.isEmpty();
    }
}
