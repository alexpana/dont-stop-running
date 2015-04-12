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
public class DeselectAllHandlersAction extends ActionManager.ActionAdapter {
    private final TerrainPatchEditTool terrainPatchEditTool;

    private final List<VertexHandler> allHandlers = Lists.newArrayList();

    private final List<VertexHandler> selectedHandlers = Lists.newArrayList();

    public DeselectAllHandlersAction(TerrainPatchEditTool terrainPatchEditTool, List<VertexHandler> allHandlers, List<VertexHandler> selectedHandlers) {
        this.terrainPatchEditTool = terrainPatchEditTool;
        this.allHandlers.addAll(allHandlers);
        this.selectedHandlers.addAll(selectedHandlers);
    }

    @Override
    public void doAction() {
        for (VertexHandler vertexHandler : allHandlers) {
            terrainPatchEditTool.findHandlerByIndex(vertexHandler.getVertexIndex()).setSelected(false);
        }
    }

    @Override
    public void undoAction() {
        for (VertexHandler vertexHandler : selectedHandlers) {
            terrainPatchEditTool.findHandlerByIndex(vertexHandler.getVertexIndex()).setSelected(true);
        }
    }

    @Override
    public boolean isValid() {
        return !selectedHandlers.isEmpty();
    }
}
