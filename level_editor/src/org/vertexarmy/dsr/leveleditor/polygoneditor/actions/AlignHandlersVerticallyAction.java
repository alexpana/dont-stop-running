package org.vertexarmy.dsr.leveleditor.polygoneditor.actions;

import com.badlogic.gdx.math.Vector2;
import com.beust.jcommander.internal.Lists;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.leveleditor.polygoneditor.PolygonEditor;
import org.vertexarmy.dsr.leveleditor.polygoneditor.VertexHandler;

import java.util.List;

/**
 * Created by alex
 * on 24.03.2015.
 */
public class AlignHandlersVerticallyAction extends ActionManager.ActionAdapter {
    private final PolygonEditor polygonEditor;

    private final List<VertexHandler> selectedVertexHandlers = Lists.newArrayList();

    private final List<Vector2> originalVertexPositions = Lists.newArrayList();

    public AlignHandlersVerticallyAction(PolygonEditor editor, List<VertexHandler> selectedVertexHandlers) {
        polygonEditor = editor;
        this.selectedVertexHandlers.addAll(selectedVertexHandlers);

        for (VertexHandler handler : selectedVertexHandlers) {
            originalVertexPositions.add(editor.getVertex(handler));
        }
    }

    @Override
    public void doAction() {
        float medianX = 0;
        List<VertexHandler> selectedHandlers = polygonEditor.getSelectedHandlers();
        if (selectedHandlers.size() > 0) {
            for (VertexHandler handler : selectedHandlers) {
                medianX += polygonEditor.getVertex(handler).x;
            }

            medianX /= selectedHandlers.size();
            for (VertexHandler handler : selectedHandlers) {
                polygonEditor.getPolygon().getVertices()[handler.getVertexIndex() * 2] = medianX;
            }
        }
    }

    @Override
    public void undoAction() {
        for (int i = 0; i < selectedVertexHandlers.size(); ++i) {
            polygonEditor.setVertex(selectedVertexHandlers.get(i), originalVertexPositions.get(i));
        }
    }

    @Override
    public boolean isValid() {
        if (selectedVertexHandlers.size() < 2) {
            return false;
        }

        for (VertexHandler handler : selectedVertexHandlers) {
            if (polygonEditor.getVertex(handler).x != polygonEditor.getVertex(selectedVertexHandlers.get(0)).x) {
                return true;
            }
        }

        return false;
    }
}
