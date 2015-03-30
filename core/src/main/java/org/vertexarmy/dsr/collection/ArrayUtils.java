package org.vertexarmy.dsr.collection;

import com.badlogic.gdx.utils.Array;

import java.util.List;

/**
 * Created by alex
 * on 30.03.2015.
 */
public class ArrayUtils {

    @SuppressWarnings("unchecked")
    public static <T> Array<T> toArray(List<T> list) {
        if (list == null) {
            return new Array<>();
        }
        return new Array<>((T[]) list.toArray());
    }
}
