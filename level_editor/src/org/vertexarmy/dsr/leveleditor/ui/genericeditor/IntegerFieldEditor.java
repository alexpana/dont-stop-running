package org.vertexarmy.dsr.leveleditor.ui.genericeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import org.vertexarmy.dsr.core.ReflectionHelper;

import java.lang.reflect.Field;

/**
 * created by Alex
 * on 05-Apr-2015.
 */
public class IntegerFieldEditor extends FieldEditor {
    private TextField textField;

    private Object boundObject;

    public IntegerFieldEditor(final Field field, Skin skin) {
        super(field);

        textField = new TextField("", skin);

        textField.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                if (boundObject != null) {
                    try {
                        Integer value = Integer.valueOf(textField.getText());
                        ReflectionHelper.setField(boundObject, field, value);
                        return true;
                    } catch (Exception ignored) {
                        return false;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public Actor getUiComponent() {
        return textField;
    }

    @Override
    public void bindToObject(Object object) {
        this.boundObject = object;
        if (object != null) {
            textField.setText(String.valueOf(ReflectionHelper.getField(object, getBoundField(), 0)));
        }
    }
}
