package org.vertexarmy.dsr.leveleditor.ui.genericeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import org.vertexarmy.dsr.core.ReflectionHelper;
import org.vertexarmy.dsr.leveleditor.ui.UIToolkit;

import java.lang.reflect.Field;

/**
 * created by Alex
 * on 05-Apr-2015.
 */
public class BooleanFieldEditor extends FieldEditor {

    private CheckBox checkBox;

    private Object boundObject;

    public BooleanFieldEditor(final Field field, Skin skin) {
        super(field);

        checkBox = new CheckBox("", skin);
        checkBox.align(Align.left);

        UIToolkit.addActionListener(checkBox, new UIToolkit.ActionListener() {
            @Override
            public void action() {
                if (boundObject != null) {
                    ReflectionHelper.setField(boundObject, field, checkBox.isChecked());
                }
            }
        });
    }

    @Override
    public Actor getUiComponent() {
        return checkBox;
    }

    @Override
    public void bindToObject(Object object) {
        this.boundObject = object;
        if (object != null) {
            checkBox.setChecked(ReflectionHelper.getField(object, getBoundField(), false));
        }
    }
}
