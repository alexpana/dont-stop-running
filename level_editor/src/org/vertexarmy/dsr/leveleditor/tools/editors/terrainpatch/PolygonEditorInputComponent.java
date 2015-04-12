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
    private final TerrainPatchEditTool terrainPatchEditTool;

    private final Map<Integer, TerrainPatchEditTool.EditModeType> hotkeys = Maps.newHashMap();

    public PolygonEditorInputComponent(final TerrainPatchEditTool terrainPatchEditTool) {
        this.terrainPatchEditTool = terrainPatchEditTool;

        hotkeys.put(Input.Keys.NUM_1, TerrainPatchEditTool.EditModeType.DEFAULT);
        hotkeys.put(Input.Keys.NUM_2, TerrainPatchEditTool.EditModeType.ADD_VERTEX);
    }

    @Override
    public InputProcessor getInputAdapter() {
        return this;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (!terrainPatchEditTool.isBound()) {
            return false;
        }

        if (hotkeys.containsKey(keycode)) {
            terrainPatchEditTool.setEditMode(hotkeys.get(keycode));
            return true;
        }
        return terrainPatchEditTool.getEditMode().keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return terrainPatchEditTool.isBound() && terrainPatchEditTool.getEditMode().keyUp(keycode);

    }

    @Override
    public boolean keyTyped(char character) {
        return terrainPatchEditTool.isBound() && terrainPatchEditTool.getEditMode().keyTyped(character);

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return terrainPatchEditTool.isBound() && terrainPatchEditTool.getEditMode().touchDown(screenX, screenY, pointer, button);

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return terrainPatchEditTool.isBound() && terrainPatchEditTool.getEditMode().touchUp(screenX, screenY, pointer, button);

    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return terrainPatchEditTool.isBound() && terrainPatchEditTool.getEditMode().touchDragged(screenX, screenY, pointer);

    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return terrainPatchEditTool.isBound() && terrainPatchEditTool.getEditMode().mouseMoved(screenX, screenY);

    }

    @Override
    public boolean scrolled(int amount) {
        return terrainPatchEditTool.isBound() && terrainPatchEditTool.getEditMode().scrolled(amount);
    }
}
