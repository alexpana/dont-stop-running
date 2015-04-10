package org.vertexarmy.dsr.leveleditor.ui.genericeditor;

import com.badlogic.gdx.math.Vector2;
import org.vertexarmy.dsr.core.ReflectionHelper;
import org.vertexarmy.dsr.leveleditor.ui.Model;

import java.lang.reflect.Field;

/**
 * created by Alex
 * on 10-Apr-2015.
 */
public class Vector2FieldModel implements Model<Float> {
    private final Element element;
    private final Field field;
    private final Object object;

    enum Element {
        X, Y
    }

    public Vector2FieldModel(Element element, Field field, Object object) {
        this.element = element;
        this.field = field;
        this.object = object;
    }

    @Override
    public Float getValue() {
        Vector2 fieldValue = ReflectionHelper.getField(object, field, Vector2.Zero.cpy());
        if (element == Element.X) {
            return fieldValue.x;
        } else {
            return fieldValue.y;
        }
    }

    @Override
    public void setValue(Float value) {
        Vector2 fieldValue = ReflectionHelper.getField(object, field, Vector2.Zero.cpy());
        if (element == Element.X) {
            fieldValue.x = value;
        } else {
            fieldValue.y = value;
        }
        ReflectionHelper.setField(object, field, fieldValue);
    }
}
