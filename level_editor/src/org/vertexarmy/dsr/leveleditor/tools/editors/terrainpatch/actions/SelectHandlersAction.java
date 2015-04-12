package org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch.actions;

import com.beust.jcommander.internal.Lists;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch.TerrainPatchEditTool;
import org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch.VertexHandler;

import java.util.List;

/**
 * Created by alex
 * on 24.03.2015.
 */
public class SelectHandlersAction extends ActionManager.ActionAdapter {
    private final TerrainPatchEditTool terrainPatchEditTool;

    private final List<VertexHandler> selectedHandlers = Lists.newArrayList();

    public SelectHandlersAction(TerrainPatchEditTool terrainPatchEditTool, List<VertexHandler> selectedHandlers) {
        this.terrainPatchEditTool = terrainPatchEditTool;
        this.selectedHandlers.addAll(selectedHandlers);
    }

    @Override
    public void doAction() {
        for (VertexHandler vertexHandler : selectedHandlers) {
            terrainPatchEditTool.findHandlerByIndex(vertexHandler.getVertexIndex()).setSelected(true);
        }
    }

    @Override
    public void undoAction() {
        for (VertexHandler vertexHandler : selectedHandlers) {
            terrainPatchEditTool.findHandlerByIndex(vertexHandler.getVertexIndex()).setSelected(false);
        }
    }

    @Override
    public boolean isValid() {
        return !selectedHandlers.isEmpty();
    }
}
