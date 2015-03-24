package org.vertexarmy.dsr.leveleditor.polygoneditor.actions;

import com.badlogic.gdx.math.Vector2;
import com.beust.jcommander.internal.Lists;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.leveleditor.polygoneditor.PolygonEditor;
import org.vertexarmy.dsr.leveleditor.polygoneditor.VertexHandler;
import org.vertexarmy.dsr.math.Algorithms;

import java.util.List;

/**
 * Created by alex
 * on 24.03.2015.
 */
public class MoveMultipleHandlersAction extends ActionManager.ActionAdapter {
    private final List<VertexHandler> vertexHandlers = Lists.newArrayList();

    private final List<Vector2> originalPositions = Lists.newArrayList();

    private final List<Vector2> newPositions = Lists.newArrayList();

    private final PolygonEditor polygonEditor;

    public MoveMultipleHandlersAction(
            PolygonEditor polygonEditor,
            List<VertexHandler> vertexHandlers,
            List<Vector2> originalPositions,
            List<Vector2> newPositions) {
        this.polygonEditor = polygonEditor;
        this.vertexHandlers.addAll(vertexHandlers);
        this.originalPositions.addAll(originalPositions);
        this.newPositions.addAll(newPositions);
    }

    @Override
    public void doAction() {
        for (int i = 0; i < vertexHandlers.size(); ++i) {
            polygonEditor.setVertex(vertexHandlers.get(i), newPositions.get(i));
        }
    }

    @Override
    public void undoAction() {
        for (int i = 0; i < vertexHandlers.size(); ++i) {
            polygonEditor.setVertex(vertexHandlers.get(i), originalPositions.get(i));
        }
    }

    @Override
    public boolean isValid() {
        return originalPositions.size() >= 2 && Algorithms.distanceSq(originalPositions.get(0), newPositions.get(0)) >= 1;

    }
}
