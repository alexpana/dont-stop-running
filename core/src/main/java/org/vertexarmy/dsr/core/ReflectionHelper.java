package org.vertexarmy.dsr.core;

import com.beust.jcommander.internal.Maps;
import com.google.common.collect.ImmutableList;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * created by Alex
 * on 05-Apr-2015.
 */
@SuppressWarnings("unused")
public class ReflectionHelper {
    public static boolean hasReadWriteAccess(Class objectClass, Field field) {
        return hasSetter(objectClass, field) && hasGetter(objectClass, field);
    }

    public static boolean classHasMethod(Class objectClass, String methodName) {
        for (Method method : objectClass.getMethods()) {
            if (method.getName().equalsIgnoreCase(methodName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean classHasField(Class objectClass, String fieldName) {
        for (Field field : objectClass.getDeclaredFields()) {
            if (field.getName().equalsIgnoreCase(fieldName)) {
                return true;
            }
        }
        return false;
    }

    public static void setField(Object object, Field field, Object... values) {
        Method setter = findSetter(object.getClass(), field);
        if (setter != null) {
            try {
                setter.invoke(object, values);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getField(Object object, Field field, T defaultValue) {
        Method getter = findGetter(object.getClass(), field);
        if (getter != null) {
            try {
                return (T) getter.invoke(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }

    public static Method findSetter(Class objectClass, Field field) {
        List<String> setterNames = setterNames(field.getName());

        for (Method method : objectClass.getMethods()) {
            if (setterNames.contains(method.getName())) {
                return method;
            }
        }
        return null;
    }

    public static Method findGetter(Class objectClass, Field field) {
        List<String> getterNames = getterNames(field.getName());

        for (Method method : objectClass.getMethods()) {
            if (getterNames.contains(method.getName())) {
                return method;
            }
        }
        return null;
    }

    public static float getPrecisionAnnotationValue(Field field) {
        Precision precision = field.getAnnotation(Precision.class);
        if (precision != null) {
            return precision.value();
        } else {
            return 1.0f;
        }
    }

    public static Memento extractMemento(Object object) {
        Memento memento = new Memento();
        for (Field field : object.getClass().getDeclaredFields()) {
            memento.fieldValues.put(field, CopyUtils.deepCopy(ReflectionHelper.getField(object, field, null)));
        }
        return memento;
    }

    public static void applyMemento(Object object, Memento memento) {
        for (Map.Entry<Field, Object> entry : memento.fieldValues.entrySet()) {
            ReflectionHelper.setField(object, entry.getKey(), entry.getValue());
        }
    }

    public static class Memento {
        private Map<Field, Object> fieldValues = Maps.newHashMap();

        @Override
        public boolean equals(Object other) {
            return other instanceof Memento && this.fieldValues.equals(((Memento) other).fieldValues);
        }
    }

    private static boolean hasGetter(Class objectClass, Field field) {
        return findGetter(objectClass, field) != null;
    }

    private static boolean hasSetter(Class objectClass, Field field) {
        return findSetter(objectClass, field) != null;
    }

    private static List<String> getterNames(String fieldName) {
        String capitalFieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        return ImmutableList.of("get" + capitalFieldName, fieldName, "is" + capitalFieldName, "has" + capitalFieldName);
    }

    private static List<String> setterNames(String fieldName) {
        String capitalFieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        return ImmutableList.of("set" + capitalFieldName, "set" + fieldName.substring(2));
    }
}
