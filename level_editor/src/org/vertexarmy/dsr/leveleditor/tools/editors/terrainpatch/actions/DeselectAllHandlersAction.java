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

    private final List<Integer> allHandlerIndices = Lists.newArrayList();

    private final List<Integer> selectedHandlerIndices = Lists.newArrayList();

    public DeselectAllHandlersAction(TerrainPatchEditTool terrainPatchEditTool, List<Integer> allHandlerIndices, List<Integer> selectedHandlerIndices) {
        this.terrainPatchEditTool = terrainPatchEditTool;
        this.allHandlerIndices.addAll(allHandlerIndices);
        this.selectedHandlerIndices.addAll(selectedHandlerIndices);
    }

    @Override
    public void doAction() {
        for (Integer vertexHandlerIndex : allHandlerIndices) {
            terrainPatchEditTool.findHandlerByIndex(vertexHandlerIndex).setSelected(false);
        }
    }

    @Override
    public void undoAction() {
        for (Integer vertexHandlerIndex : selectedHandlerIndices) {
            terrainPatchEditTool.findHandlerByIndex(vertexHandlerIndex).setSelected(true);
        }
    }

    @Override
    public boolean isValid() {
        return !selectedHandlerIndices.isEmpty();
    }
}
