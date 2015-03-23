package org.vertexarmy.dsr.level_editor.polygon_editor;

import com.badlogic.gdx.InputProcessor;

/**
 * created by Alex
 * on 3/23/2015.
 */
public interface EditMode extends InputProcessor {
    void start();

    void stop();

    void render();
}
