package org.vertexarmy.dsr.leveleditor.editors;

import javax.annotation.Nullable;

/**
 * created by Alex
 * on 08-Apr-2015.
 */
public abstract class Bindable<T> {
    private T boundObject;

    public boolean bind(T object) {
        if (boundObject != object) {
            boundObject = object;
            doBind(object);
            return true;
        } else {
            return false;
        }
    }

    public boolean unbind() {
        if (boundObject != null) {
            boundObject = null;
            doBind(null);
            return true;
        } else {
            return false;
        }
    }

    public boolean isBound() {
        return boundObject != null;
    }

    public T getBoundObject() {
        return boundObject;
    }

    protected abstract void doBind(@Nullable T object);
}
