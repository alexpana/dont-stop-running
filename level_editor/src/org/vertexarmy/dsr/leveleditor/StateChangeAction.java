package org.vertexarmy.dsr.leveleditor;

import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.core.ReflectionHelper;

/**
 * created by Alex
 * on 13-Apr-2015.
 */
public class StateChangeAction extends ActionManager.ActionAdapter {
    private final Object object;
    private final ReflectionHelper.Memento originalState;
    private final ReflectionHelper.Memento newState;

    public StateChangeAction(Object object, ReflectionHelper.Memento originalState, ReflectionHelper.Memento newState) {
        this.object = object;
        this.originalState = originalState;
        this.newState = newState;
    }

    @Override
    public void doAction() {
        ReflectionHelper.applyMemento(object, newState);
    }

    @Override
    public void undoAction() {
        ReflectionHelper.applyMemento(object, originalState);
    }

    @Override
    public boolean isValid() {
        return !originalState.equals(newState);
    }
}
