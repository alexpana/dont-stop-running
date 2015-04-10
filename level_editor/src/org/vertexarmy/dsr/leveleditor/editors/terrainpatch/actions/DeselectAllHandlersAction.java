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
public class DeselectAllHandlersAction extends ActionManager.ActionAdapter {
    private final TerrainPatchEditor terrainPatchEditor;

    private final List<VertexHandler> allHandlers = Lists.newArrayList();

    private final List<VertexHandler> selectedHandlers = Lists.newArrayList();

    public DeselectAllHandlersAction(TerrainPatchEditor terrainPatchEditor, List<VertexHandler> allHandlers, List<VertexHandler> selectedHandlers) {
        this.terrainPatchEditor = terrainPatchEditor;
        this.allHandlers.addAll(allHandlers);
        this.selectedHandlers.addAll(selectedHandlers);
    }

    @Override
    public void doAction() {
        for (VertexHandler vertexHandler : allHandlers) {
            terrainPatchEditor.findHandlerByIndex(vertexHandler.getVertexIndex()).setSelected(false);
        }
    }

    @Override
    public void undoAction() {
        for (VertexHandler vertexHandler : selectedHandlers) {
            terrainPatchEditor.findHandlerByIndex(vertexHandler.getVertexIndex()).setSelected(true);
        }
    }

    @Override
    public boolean isValid() {
        return !selectedHandlers.isEmpty();
    }
}
