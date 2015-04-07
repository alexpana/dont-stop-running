package org.vertexarmy.dsr.leveleditor.editors.polygon.actions;

import com.beust.jcommander.internal.Lists;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.leveleditor.editors.polygon.PolygonEditor;
import org.vertexarmy.dsr.leveleditor.editors.polygon.VertexHandler;

import java.util.List;

/**
 * Created by alex
 * on 24.03.2015.
 */
public class SelectHandlersAction extends ActionManager.ActionAdapter {
    private final PolygonEditor polygonEditor;

    private final List<VertexHandler> selectedHandlers = Lists.newArrayList();

    public SelectHandlersAction(PolygonEditor polygonEditor, List<VertexHandler> selectedHandlers) {
        this.polygonEditor = polygonEditor;
        this.selectedHandlers.addAll(selectedHandlers);
    }

    @Override
    public void doAction() {
        for (VertexHandler vertexHandler : selectedHandlers) {
            polygonEditor.findHandlerByIndex(vertexHandler.getVertexIndex()).setSelected(true);
        }
    }

    @Override
    public void undoAction() {
        for (VertexHandler vertexHandler : selectedHandlers) {
            polygonEditor.findHandlerByIndex(vertexHandler.getVertexIndex()).setSelected(false);
        }
    }

    @Override
    public boolean isValid() {
        return !selectedHandlers.isEmpty();
    }
}