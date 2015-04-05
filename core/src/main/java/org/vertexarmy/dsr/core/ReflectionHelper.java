package org.vertexarmy.dsr.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * created by Alex
 * on 05-Apr-2015.
 */
@SuppressWarnings("unused")
public class ReflectionHelper {
    public static boolean classHasProperty(Class objectClass, String fieldName) {
        // TODO: replace these ugly hacks
        return (classHasMethod(objectClass, "set" + fieldName) || classHasMethod(objectClass, "set" + fieldName.substring(2))) &&
                (classHasMethod(objectClass, "get" + fieldName) || classHasMethod(objectClass, "has" + fieldName) || classHasMethod(objectClass, "is" + fieldName) || classHasMethod(objectClass, fieldName)) &&
                classHasField(objectClass, fieldName);
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
        for (Method method : objectClass.getMethods()) {
            if (method.getName().equalsIgnoreCase("set" + field.getName()) || method.getName().equalsIgnoreCase("set" + field.getName().substring(2))) {
                return method;
            }
        }
        return null;
    }

    public static Method findGetter(Class objectClass, Field field) {
        for (Method method : objectClass.getMethods()) {
            if (method.getName().equalsIgnoreCase("get" + field.getName()) || method.getName().equalsIgnoreCase(field.getName()) || method.getName().equalsIgnoreCase("is" + field.getName()) || method.getName().equalsIgnoreCase("has" + field.getName())) {
                return method;
            }
        }
        return null;
    }

}
