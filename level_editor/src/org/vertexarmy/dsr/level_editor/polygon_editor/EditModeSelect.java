package org.vertexarmy.dsr.level_editor.polygon_editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.beust.jcommander.internal.Lists;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.vertexarmy.dsr.core.DragHelper;
import org.vertexarmy.dsr.core.systems.RenderSystem;
import org.vertexarmy.dsr.level_editor.DebugValues;
import org.vertexarmy.dsr.math.Algorithms;

/**
 * created by Alex
 * on 3/23/2015.
 */
@RequiredArgsConstructor
public class EditModeSelect extends InputAdapter implements EditMode {
    private final PolygonEditor polygonEditor;

    private final DragHelper dragHelper = new DragHelper();

    private boolean multipleSelections = false;

    private boolean multipleSelectionsDisabled = false;

    private final List<VertexHandler> newlySelectedHandlers = Lists.newArrayList();

    // TODO: handle inverse selection via SHIFT

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        dragHelper.beginDrag(mouseWorld(screenX, screenY));
        newlySelectedHandlers.clear();
        if (!multipleSelections) {
            clearSelection();
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (dragHelper.isDragging()) {
            dragHelper.endDrag();

            if (multipleSelectionsDisabled) {
                multipleSelectionsDisabled = false;
                multipleSelections = false;
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (dragHelper.isDragging()) {
            dragHelper.notifyMouseMoved(mouseWorld(screenX, screenY));
            Vector2 vertexPosition = new Vector2();
            Rectangle selectionRect = Algorithms.createRectangle(dragHelper.getDragStartPosition(), dragHelper.getLastPosition());

            for (VertexHandler handler : polygonEditor.getVertexHandlers()) {
                vertexPosition.set(polygonEditor.getPolygon().getVertices()[handler.vertexIndex * 2], polygonEditor.getPolygon().getVertices()[handler.vertexIndex * 2 + 1]);
                if (selectionRect.contains(vertexPosition)) {
                    if (!handler.isSelected()) {
                        newlySelectedHandlers.add(handler);
                    }
                    handler.setSelected(true);
                } else if (newlySelectedHandlers.contains(handler)) {
                    handler.setSelected(false);
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.X) {
            alignVertically();
            return true;
        }

        if (keycode == Input.Keys.Y) {
            alignHorizontally();
            return true;
        }

        if (keycode == Input.Keys.CONTROL_LEFT) {
            multipleSelectionsDisabled = false;
            multipleSelections = true;
            return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.CONTROL_LEFT) {
            if (dragHelper.isDragging()) {
                multipleSelectionsDisabled = true;
            } else {
                multipleSelections = false;
                multipleSelectionsDisabled = false;
            }
            return true;
        }

        return false;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        dragHelper.endDrag();
        clearSelection();
    }

    @Override
    public void render() {
        DebugValues.instance().setValue("multiple select", String.valueOf(multipleSelections));
        if (dragHelper.isDragging()) {
            ShapeRenderer shapeRenderer = RenderSystem.instance().getShapeRenderer();
            float x1 = dragHelper.getDragStartPosition().x;
            float y1 = dragHelper.getDragStartPosition().y;
            float x2 = dragHelper.getLastPosition().x;
            float y2 = dragHelper.getLastPosition().y;

            Gdx.gl20.glEnable(Gdx.gl.GL_BLEND);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(new Color(0x40508080));
            shapeRenderer.rect(x1, y1, x2 - x1, y2 - y1);
            shapeRenderer.end();

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(new Color(0x405080FF));
            shapeRenderer.rect(x1, y1, x2 - x1, y2 - y1);
            shapeRenderer.end();
        }
    }

    private void alignVertically() {
        float medianX = 0;
        List<VertexHandler> selectedHandlers = polygonEditor.getSelectedHandlers();
        if (selectedHandlers.size() > 0) {
            for (VertexHandler handler : selectedHandlers) {
                medianX += polygonEditor.getVertex(handler).x;
            }

            medianX /= selectedHandlers.size();
            for (VertexHandler handler : selectedHandlers) {
                polygonEditor.getPolygon().getVertices()[handler.vertexIndex * 2] = medianX;
            }
        }
    }

    private void alignHorizontally() {
        float medianY = 0;
        List<VertexHandler> selectedHandlers = polygonEditor.getSelectedHandlers();
        if (selectedHandlers.size() > 0) {
            for (VertexHandler handler : selectedHandlers) {
                medianY += polygonEditor.getVertex(handler).y;
            }

            medianY /= selectedHandlers.size();
            for (VertexHandler handler : selectedHandlers) {
                polygonEditor.getPolygon().getVertices()[handler.vertexIndex * 2 + 1] = medianY;
            }
        }
    }

    private void clearSelection() {
        for (VertexHandler handler : polygonEditor.getVertexHandlers()) {
            handler.setSelected(false);
        }
    }

    private Vector2 mouseWorld(int screenX, int screenY) {
        return RenderSystem.instance().screenToWorld(new Vector2(screenX, screenY));
    }
}
