package org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch;

import com.badlogic.gdx.InputProcessor;

/**
 * created by Alex
 * on 3/23/2015.
 */
interface EditMode extends InputProcessor {
    void start();

    void stop();

    void render();
}
