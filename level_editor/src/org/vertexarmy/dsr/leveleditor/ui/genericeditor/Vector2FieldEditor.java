package org.vertexarmy.dsr.leveleditor.ui.genericeditor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import org.vertexarmy.dsr.core.ReflectionHelper;

import java.lang.reflect.Field;

/**
 * created by Alex
 * on 05-Apr-2015.
 */
public class Vector2FieldEditor extends FieldEditor {
    private final TextField textFieldX;

    private final TextField textFieldY;

    private final Table contentTable;

    private Object boundObject;

    public Vector2FieldEditor(final Field field, Skin skin) {
        super(field);

        contentTable = new Table(skin);

        textFieldX = new TextField("", skin);

        textFieldX.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                if (boundObject != null) {
                    try {
                        Vector2 fieldValue = (Vector2) ReflectionHelper.findGetter(boundObject.getClass(), field).invoke(boundObject);
                        fieldValue.x = Float.valueOf(textFieldX.getText());
                        return true;
                    } catch (Exception ignored) {
                        return false;
                    }
                }
                return false;
            }
        });

        textFieldY = new TextField("", skin);

        textFieldY.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                if (boundObject != null) {
                    try {
                        Vector2 fieldValue = (Vector2) ReflectionHelper.findGetter(boundObject.getClass(), field).invoke(boundObject);
                        fieldValue.y = Float.valueOf(textFieldY.getText());
                        return true;
                    } catch (Exception ignored) {
                        return false;
                    }
                }
                return false;
            }
        });

        contentTable.add(new Label("X:", skin)).left().padRight(4);
        contentTable.add(textFieldX).width(50).left().padRight(4);
        contentTable.add(new Label("Y:", skin)).left().padRight(4);
        contentTable.add(textFieldY).width(50).left().padRight(4);
        contentTable.align(Align.left);
    }

    @Override
    public Actor getUiComponent() {
        return contentTable;
    }

    @Override
    public void bindToObject(Object object) {
        this.boundObject = object;
        if (object != null) {
            textFieldX.setText(String.valueOf(ReflectionHelper.getField(object, getBoundField(), Vector2.Zero).x));
            textFieldY.setText(String.valueOf(ReflectionHelper.getField(object, getBoundField(), Vector2.Zero).y));
        }
    }
}
