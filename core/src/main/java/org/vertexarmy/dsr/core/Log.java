package org.vertexarmy.dsr.core;

import com.badlogic.gdx.Gdx;
import lombok.RequiredArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * created by Alex
 * on 3/19/2015.
 */
@RequiredArgsConstructor
public class Log {
    private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private final Date date = new Date();

    private final String identifier;

    private Level level = Level.DEBUG;

    public enum Level {
        DEBUG,
        INFO,
        WARNING,
        ERROR,
        NONE;
    }

    public static Log create() {
        String callingClass = getCallingClass();
        return new Log(callingClass);
    }

    private static String getCallingClass() {
        String[] classPath = Thread.currentThread().getStackTrace()[3].getClassName().split("\\.");
        return classPath[classPath.length - 1];
    }

    public void error(String message) {
        if (isLevelEnabled(Level.ERROR)) {
            Gdx.app.error(formatPrefix("ERROR"), message);
        }
    }

    public void warning(String message) {
        if (isLevelEnabled(Level.WARNING)) {
            Gdx.app.error(formatPrefix("DEBUG"), message);
        }
    }

    public void info(String message) {
        if (isLevelEnabled(Level.INFO)) {
            Gdx.app.log(formatPrefix("INFO"), message);
        }
    }

    public void debug(String message) {
        if (isLevelEnabled(Level.DEBUG)) {
            Gdx.app.debug(formatPrefix("DEBUG"), message);
        }
    }

    public void exception(Exception e) {
        e.printStackTrace();
    }

    private boolean isLevelEnabled(Level level) {
        return this.level.ordinal() <= level.ordinal();

    }

    private String formatPrefix(String level) {
        date.setTime(System.currentTimeMillis());

        return DATE_FORMAT.format(date) + " " + level + " " + identifier;
    }
}
