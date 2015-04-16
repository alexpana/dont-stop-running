package org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch.actions;

import com.beust.jcommander.internal.Lists;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch.TerrainPatchEditTool;

import java.util.List;

/**
 * Created by alex
 * on 24.03.2015.
 */
public class SelectHandlersAction extends ActionManager.ActionAdapter {
    private final TerrainPatchEditTool terrainPatchEditTool;

    private final List<Integer> selectedHandlerIndices = Lists.newArrayList();

    public SelectHandlersAction(TerrainPatchEditTool terrainPatchEditTool, List<Integer> selectedHandlerIndices) {
        this.terrainPatchEditTool = terrainPatchEditTool;
        this.selectedHandlerIndices.addAll(selectedHandlerIndices);
    }

    @Override
    public void doAction() {
        for (Integer vertexHandlerIndex : selectedHandlerIndices) {
            terrainPatchEditTool.findHandlerByIndex(vertexHandlerIndex).setSelected(true);
        }
    }

    @Override
    public void undoAction() {
        for (Integer vertexHandlerIndex : selectedHandlerIndices) {
            terrainPatchEditTool.findHandlerByIndex(vertexHandlerIndex).setSelected(false);
        }
    }

    @Override
    public boolean isValid() {
        return !selectedHandlerIndices.isEmpty();
    }
}
