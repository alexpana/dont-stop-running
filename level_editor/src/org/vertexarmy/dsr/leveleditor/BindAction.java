package org.vertexarmy.dsr.leveleditor;

import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.leveleditor.editors.Bindable;

/**
 * created by Alex
 * on 08-Apr-2015.
 */
public class BindAction<T> implements ActionManager.Action {
    private final Bindable<T> bindable;
    private final T object;
    private final T oldObject;

    public BindAction(Bindable<T> bindable, T object) {
        this.bindable = bindable;
        this.object = object;
        this.oldObject = bindable.getBoundObject();
    }


    @Override
    public void doAction() {
        bindable.bind(object);
    }

    @Override
    public void undoAction() {
        bindable.bind(oldObject);
    }

    @Override
    public boolean isValid() {
        return object != oldObject;
    }
}
