package org.vertexarmy.dsr.core;

import com.badlogic.gdx.math.Vector2;

/**
 * created by Alex
 * on 14-Apr-2015.
 */
public class CopyUtils {
    /**
     * Helper method for creating deep copies of generic objects which may or may not implement
     * the {@link DeepCopyable} interface.
     *
     * @param object the object to be copied
     * @return a deep copy of the object if possible
     * @throws RuntimeException if the object cannot be copied
     */
    public static Object deepCopy(Object object) {
        if (object == null) {
            return null;
        }

        if (object instanceof DeepCopyable) {
            return ((DeepCopyable) object).copy();
        }

        if (object instanceof Vector2) {
            return ((Vector2) object).cpy();
        }

        if (isBoxedPrimitive(object) || isString(object)) {
            return object;
        }

        // TODO: Handle arrays / lists / sets / maps / etc.

        throw new RuntimeException("Object cannot be cloned " + object);
    }

    private static boolean isBoxedPrimitive(Object object) {
        return object instanceof Integer || object instanceof Float || object instanceof Character || object instanceof Boolean;
    }

    private static boolean isString(Object object) {
        return object instanceof String;
    }
}
