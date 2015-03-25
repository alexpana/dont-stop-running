package org.vertexarmy.dsr.leveleditor.polygoneditor;

import com.badlogic.gdx.InputAdapter;

/**
 * Created by alex
 * on 25.03.2015.
 */
public class EditModeAddVertex extends InputAdapter implements EditMode {
    private boolean closeToEdge = false;

    public int edgeIndex = -1;

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public void render() {
        if (closeToEdge) {

        }
    }
}
