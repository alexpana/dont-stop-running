package org.vertexarmy.dsr.leveleditor;

import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.leveleditor.tools.editors.BindableTool;

/**
 * created by Alex
 * on 08-Apr-2015.
 */
public class BindAction<T> implements ActionManager.Action {
    private final BindableTool<T> tool;
    private final T object;
    private final T oldObject;

    public BindAction(BindableTool<T> tool, T object) {
        this.tool = tool;
        this.object = object;
        this.oldObject = tool.getBoundObject();
    }


    @Override
    public void doAction() {
        tool.bind(object);
    }

    @Override
    public void undoAction() {
        tool.bind(oldObject);
    }

    @Override
    public boolean isValid() {
        return object != oldObject;
    }
}
