package org.vertexarmy.dsr.leveleditor.ui.genericeditor;

import org.vertexarmy.dsr.core.ReflectionHelper;
import org.vertexarmy.dsr.leveleditor.ui.Model;

import java.lang.reflect.Field;

/**
 * created by Alex
 * on 10-Apr-2015.
 */
public class ObjectFieldModel<T> implements Model<T> {
    private Field field;

    private Object object;
    private T defaultValue;

    public ObjectFieldModel(Field field, Object object, T defaultValue) {
        this.field = field;
        this.object = object;
        this.defaultValue = defaultValue;
    }

    @Override
    public T getValue() {
        return ReflectionHelper.getField(object, field, defaultValue);
    }

    @Override
    public void setValue(T value) {
        ReflectionHelper.setField(object, field, value);
    }
}
