package org.vertexarmy.dsr.leveleditor.tools.editors.levelsprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import lombok.RequiredArgsConstructor;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.core.DragHelper;
import org.vertexarmy.dsr.core.ReflectionHelper;
import org.vertexarmy.dsr.core.component.InputComponent;
import org.vertexarmy.dsr.core.systems.RenderSystem;
import org.vertexarmy.dsr.leveleditor.StateChangeAction;
import org.vertexarmy.dsr.math.Algorithms;

/**
 * created by Alex
 * on 04-Apr-2015.
 */
@RequiredArgsConstructor
public class LevelSpriteEditorInputComponent extends InputAdapter implements InputComponent {
    private final DragHelper dragHelper = new DragHelper();

    private final LevelSpriteEditTool editor;

    private ReflectionHelper.Memento originalState;

    private ReflectionHelper.Memento newState;

    enum EditMode {
        MOVE,
        SCALE,
        ROTATE,
        NONE
    }

    private EditMode editMode = EditMode.NONE;

    @Override
    public InputProcessor getInputAdapter() {
        return this;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.E && editor.isBound()) {
            editor.getEditDialog().show();
            return true;
        }

        if (keycode == Input.Keys.ESCAPE && editor.getEditDialog().isVisible()) {
            editor.getEditDialog().hide();
            return true;
        }

        if (keycode == Input.Keys.FORWARD_DEL) {
            editor.deleteSprite();
            return true;
        }

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (!editor.isBound()) {
            return false;
        }

        Vector2 mouseWorldPosition = RenderSystem.instance().screenToWorld(screenX, screenY);

        if (!editor.getRotateHandler().isHovered() && !editor.getScaleHandler().isHovered() &&
                Algorithms.createRectangle(editor.getSpriteBottomLeftCorner(), editor.getSpriteTopRightCorner()).contains(mouseWorldPosition)) {
            editMode = EditMode.MOVE;
            dragHelper.beginDrag(mouseWorldPosition);
            originalState = ReflectionHelper.extractMemento(editor.getBoundObject());
            return true;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (dragHelper.isDragging()) {
            editMode = EditMode.NONE;
            dragHelper.endDrag();
            newState = ReflectionHelper.extractMemento(editor.getBoundObject());
            ActionManager.instance().runAction(new StateChangeAction(editor.getBoundObject(), originalState, newState));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!editor.isBound()) {
            return false;
        }

        Vector2 mouseWorldPosition = RenderSystem.instance().screenToWorld(screenX, screenY);

        if (editMode == EditMode.MOVE) {
            Vector2 moveOffset = dragHelper.getDragOffset(mouseWorldPosition);
            dragHelper.reset(mouseWorldPosition);
            editor.getBoundObject().getPosition().add(moveOffset);
            return true;
        }

        return false;
    }
}
