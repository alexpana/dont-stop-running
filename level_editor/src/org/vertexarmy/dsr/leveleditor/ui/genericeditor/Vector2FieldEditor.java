package org.vertexarmy.dsr.leveleditor.ui.genericeditor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import org.vertexarmy.dsr.core.ReflectionHelper;
import org.vertexarmy.dsr.leveleditor.ui.Spinner;

import java.lang.reflect.Field;

/**
 * created by Alex
 * on 05-Apr-2015.
 */
public class Vector2FieldEditor extends FieldEditor {
    private final Spinner textFieldX;

    private final Spinner textFieldY;

    private final Table contentTable;

    private Object boundObject;

    public Vector2FieldEditor(final Field field, Skin skin) {
        super(field);

        contentTable = new Table(skin);

        textFieldX = new Spinner("", skin);
        textFieldX.setIncrement(ReflectionHelper.getPrecisionAnnotationValue(field));
        textFieldX.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (boundObject != null) {
                    try {
                        Vector2 fieldValue = (Vector2) ReflectionHelper.findGetter(boundObject.getClass(), field).invoke(boundObject);
                        fieldValue.x = Float.valueOf(textFieldX.getText());
                    } catch (Exception ignored) {
                    }
                }
            }
        });

        textFieldY = new Spinner("", skin);
        textFieldY.setIncrement(ReflectionHelper.getPrecisionAnnotationValue(field));

        textFieldY.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (boundObject != null) {
                    try {
                        Vector2 fieldValue = (Vector2) ReflectionHelper.findGetter(boundObject.getClass(), field).invoke(boundObject);
                        fieldValue.y = Float.valueOf(textFieldY.getText());
                    } catch (Exception ignored) {
                    }
                }
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
            textFieldX.setFloatModel(new Vector2FieldModel(Vector2FieldModel.Element.X, getBoundField(), boundObject));

            textFieldY.setText(String.valueOf(ReflectionHelper.getField(object, getBoundField(), Vector2.Zero).y));
            textFieldY.setFloatModel(new Vector2FieldModel(Vector2FieldModel.Element.Y, getBoundField(), boundObject));
        }
    }
}
