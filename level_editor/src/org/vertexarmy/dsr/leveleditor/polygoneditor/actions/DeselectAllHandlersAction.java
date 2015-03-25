package org.vertexarmy.dsr.leveleditor.polygoneditor.actions;

import com.beust.jcommander.internal.Lists;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.leveleditor.polygoneditor.VertexHandler;

import java.util.List;

/**
 * Created by alex
 * on 24.03.2015.
 */
public class DeselectAllHandlersAction extends ActionManager.ActionAdapter {
    private final List<VertexHandler> selectedHandlers = Lists.newArrayList();

    private List<VertexHandler> allHandlers;

    public DeselectAllHandlersAction(List<VertexHandler> allHandlers, List<VertexHandler> selectedHandlers) {
        this.allHandlers = allHandlers;
        this.selectedHandlers.addAll(selectedHandlers);
    }

    @Override
    public void doAction() {
        for (VertexHandler vertexHandler : allHandlers) {
            vertexHandler.setSelected(false);
        }
    }

    @Override
    public void undoAction() {
        for (VertexHandler vertexHandler : selectedHandlers) {
            vertexHandler.setSelected(true);
        }
    }

    @Override
    public boolean isValid() {
        return !selectedHandlers.isEmpty();
    }
}
