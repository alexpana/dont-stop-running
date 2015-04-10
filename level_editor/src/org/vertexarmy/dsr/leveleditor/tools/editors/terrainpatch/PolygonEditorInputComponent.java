package org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.beust.jcommander.internal.Maps;
import org.vertexarmy.dsr.core.component.InputComponent;

import java.util.Map;

/**
 * created by Alex
 * on 3/23/2015.
 */
class PolygonEditorInputComponent implements InputComponent, InputProcessor {
    private final TerrainPatchEditor terrainPatchEditor;

    private final Map<Integer, TerrainPatchEditor.EditModeType> hotkeys = Maps.newHashMap();

    public PolygonEditorInputComponent(final TerrainPatchEditor terrainPatchEditor) {
        this.terrainPatchEditor = terrainPatchEditor;

        hotkeys.put(Input.Keys.NUM_1, TerrainPatchEditor.EditModeType.DEFAULT);
        hotkeys.put(Input.Keys.NUM_2, TerrainPatchEditor.EditModeType.ADD_VERTEX);
    }

    @Override
    public InputProcessor getInputAdapter() {
        return this;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (!terrainPatchEditor.isBound()) {
            return false;
        }

        if (hotkeys.containsKey(keycode)) {
            terrainPatchEditor.setEditMode(hotkeys.get(keycode));
            return true;
        }
        return terrainPatchEditor.getEditMode().keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return terrainPatchEditor.isBound() && terrainPatchEditor.getEditMode().keyUp(keycode);

    }

    @Override
    public boolean keyTyped(char character) {
        return terrainPatchEditor.isBound() && terrainPatchEditor.getEditMode().keyTyped(character);

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return terrainPatchEditor.isBound() && terrainPatchEditor.getEditMode().touchDown(screenX, screenY, pointer, button);

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return terrainPatchEditor.isBound() && terrainPatchEditor.getEditMode().touchUp(screenX, screenY, pointer, button);

    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return terrainPatchEditor.isBound() && terrainPatchEditor.getEditMode().touchDragged(screenX, screenY, pointer);

    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return terrainPatchEditor.isBound() && terrainPatchEditor.getEditMode().mouseMoved(screenX, screenY);

    }

    @Override
    public boolean scrolled(int amount) {
        return terrainPatchEditor.isBound() && terrainPatchEditor.getEditMode().scrolled(amount);
    }
}
