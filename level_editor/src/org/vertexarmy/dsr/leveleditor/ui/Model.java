package org.vertexarmy.dsr.leveleditor.ui;

/**
 * created by Alex
 * on 10-Apr-2015.
 */
public interface Model<T> {
    T getValue();

    void setValue(T value);
}
