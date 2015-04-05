package org.vertexarmy.dsr.leveleditor.ui.genericeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import lombok.Getter;

import java.lang.reflect.Field;

/**
 * created by Alex
 * on 05-Apr-2015.
 */
public abstract class FieldEditor {
    @Getter
    private final Field boundField;

    public FieldEditor(Field boundField) {
        this.boundField = boundField;
    }

    abstract public Actor getUiComponent();

    abstract public void bindToObject(Object object);
}
