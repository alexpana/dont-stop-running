package org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch.actions;

import com.badlogic.gdx.math.Vector2;
import com.beust.jcommander.internal.Lists;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch.TerrainPatchEditTool;
import org.vertexarmy.dsr.math.Algorithms;

import java.util.List;

/**
 * Created by alex
 * on 24.03.2015.
 */
public class MoveMultipleHandlersAction extends ActionManager.ActionAdapter {
    private final List<Integer> vertexHandlerIndices = Lists.newArrayList();

    private final List<Vector2> originalPositions = Lists.newArrayList();

    private final List<Vector2> newPositions = Lists.newArrayList();

    private final TerrainPatchEditTool editorTool;

    public MoveMultipleHandlersAction(
            TerrainPatchEditTool editorTool,
            List<Integer> vertexHandlerIndices,
            List<Vector2> originalPositions,
            List<Vector2> newPositions) {
        this.editorTool = editorTool;
        this.vertexHandlerIndices.addAll(vertexHandlerIndices);
        this.originalPositions.addAll(originalPositions);
        this.newPositions.addAll(newPositions);
    }

    @Override
    public void doAction() {
        for (int i = 0; i < vertexHandlerIndices.size(); ++i) {
            editorTool.setVertex(editorTool.findHandlerByIndex(vertexHandlerIndices.get(i)), newPositions.get(i));
        }
    }

    @Override
    public void undoAction() {
        for (int i = 0; i < vertexHandlerIndices.size(); ++i) {
            editorTool.setVertex(editorTool.findHandlerByIndex(vertexHandlerIndices.get(i)), originalPositions.get(i));
        }
    }

    @Override
    public boolean isValid() {
        return !originalPositions.isEmpty() && Algorithms.distanceSq(originalPositions.get(0), newPositions.get(0)) >= 1;

    }
}
