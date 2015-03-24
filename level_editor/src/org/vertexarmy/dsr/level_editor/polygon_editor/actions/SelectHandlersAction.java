package org.vertexarmy.dsr.level_editor.polygon_editor.actions;

import com.beust.jcommander.internal.Lists;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.level_editor.polygon_editor.VertexHandler;

import java.util.List;

/**
 * Created by alex
 * on 24.03.2015.
 */
public class SelectHandlersAction extends ActionManager.ActionAdapter {
    private final List<VertexHandler> selectedHandlers = Lists.newArrayList();

    public SelectHandlersAction(List<VertexHandler> selectedHandlers) {
        this.selectedHandlers.addAll(selectedHandlers);
    }

    @Override
    public void doAction() {
        for (VertexHandler vertexHandler : selectedHandlers) {
            vertexHandler.setSelected(true);
        }
    }

    @Override
    public void undoAction() {
        for (VertexHandler vertexHandler : selectedHandlers) {
            vertexHandler.setSelected(false);
        }
    }

    @Override
    public boolean isValid() {
        return !selectedHandlers.isEmpty();
    }
}
