package org.vertexarmy.dsr.core;

import com.badlogic.gdx.Gdx;
import lombok.RequiredArgsConstructor;

/**
 * created by Alex
 * on 3/19/2015.
 */
@RequiredArgsConstructor
public class Log {
    private final String identifier;

    public static Log create() {
        String callingClass = getCallingClass();
        return new Log(callingClass);
    }

    private static String getCallingClass() {
        String[] classPath = Thread.currentThread().getStackTrace()[3].getClassName().split("\\.");
        return classPath[classPath.length - 1];
    }

    public void error(String message) {
        Gdx.app.error(identifier, message);
    }

    public void debug(String message) {
        Gdx.app.debug(identifier, message);
    }
}
