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

    public void error(String message) {
        Gdx.app.error(identifier, message);
    }
}
