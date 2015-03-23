package org.vertexarmy.dsr.level_editor.polygon_editor;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.beust.jcommander.internal.Maps;
import java.util.Map;
import org.vertexarmy.dsr.core.component.InputComponent;

/**
 * created by Alex
 * on 3/23/2015.
 */
// TODO: change modes using hotkeys: SHIFT, CTRL, etc.
class PolygonEditorInputComponent implements InputComponent, InputProcessor {
    private final PolygonEditor polygonEditor;

    private final Map<Integer, PolygonEditor.EditModeType> hotkeys = Maps.newHashMap();

    public PolygonEditorInputComponent(final PolygonEditor polygonEditor) {
        this.polygonEditor = polygonEditor;

        hotkeys.put(Input.Keys.NUM_1, PolygonEditor.EditModeType.DEFAULT);
        hotkeys.put(Input.Keys.NUM_2, PolygonEditor.EditModeType.SELECT);
        hotkeys.put(Input.Keys.NUM_3, PolygonEditor.EditModeType.ADD_VERTEX);
        hotkeys.put(Input.Keys.NUM_4, PolygonEditor.EditModeType.DELETE_VERTEX);
    }

    @Override
    public InputProcessor getInputAdapter() {
        return this;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (hotkeys.containsKey(keycode)) {
            polygonEditor.setEditMode(hotkeys.get(keycode));
            return true;
        }
        return polygonEditor.getEditMode().keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return polygonEditor.getEditMode().keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return polygonEditor.getEditMode().keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return polygonEditor.getEditMode().touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return polygonEditor.getEditMode().touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return polygonEditor.getEditMode().touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return polygonEditor.getEditMode().mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(int amount) {
        return polygonEditor.getEditMode().scrolled(amount);
    }
}
