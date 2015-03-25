package org.vertexarmy.dsr.leveleditor.polygoneditor.actions;

import com.badlogic.gdx.math.Vector2;
import com.beust.jcommander.internal.Lists;
import java.util.List;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.leveleditor.polygoneditor.PolygonEditor;
import org.vertexarmy.dsr.leveleditor.polygoneditor.VertexHandler;

/**
 * Created by alex
 * on 24.03.2015.
 */
public class AlignHandlersHorizontallyAction extends ActionManager.ActionAdapter {
    private final PolygonEditor polygonEditor;

    private final List<VertexHandler> selectedVertexHandlers = Lists.newArrayList();

    private final List<Vector2> originalVertexPositions = Lists.newArrayList();

    public AlignHandlersHorizontallyAction(PolygonEditor editor, List<VertexHandler> selectedVertexHandlers) {
        polygonEditor = editor;
        this.selectedVertexHandlers.addAll(selectedVertexHandlers);

        for (VertexHandler handler : selectedVertexHandlers) {
            originalVertexPositions.add(editor.getVertex(handler));
        }
    }

    @Override
    public void doAction() {
        float medianY = 0;
        List<VertexHandler> selectedHandlers = polygonEditor.getSelectedHandlers();
        if (!selectedHandlers.isEmpty()) {
            for (VertexHandler handler : selectedHandlers) {
                medianY += polygonEditor.getVertex(handler).y;
            }

            medianY /= selectedHandlers.size();
            for (VertexHandler handler : selectedHandlers) {
                Vector2 originalPosition = polygonEditor.getVertex(handler);
                polygonEditor.setVertex(handler, originalPosition.x, medianY);
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
            if (polygonEditor.getVertex(handler).y != polygonEditor.getVertex(selectedVertexHandlers.get(0)).y) {
                return true;
            }
        }

        return false;
    }
}
