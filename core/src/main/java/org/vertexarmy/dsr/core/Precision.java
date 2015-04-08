package org.vertexarmy.dsr.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * created by Alex
 * on 08-Apr-2015.
 */
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Precision {
    float value();
}
