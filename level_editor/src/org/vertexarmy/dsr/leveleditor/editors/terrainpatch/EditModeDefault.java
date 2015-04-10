package org.vertexarmy.dsr.leveleditor.editors.terrainpatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.beust.jcommander.internal.Lists;
import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.core.DragHelper;
import org.vertexarmy.dsr.core.systems.RenderSystem;
import org.vertexarmy.dsr.leveleditor.DebugItems;
import org.vertexarmy.dsr.leveleditor.DebugValues;
import org.vertexarmy.dsr.leveleditor.editors.terrainpatch.actions.*;
import org.vertexarmy.dsr.math.Algorithms;

import java.util.List;

/**
 * created by Alex
 * on 3/23/2015.
 */
@RequiredArgsConstructor
public class EditModeDefault extends InputAdapter implements EditMode {
    private final DragHelper selectionDragHelper = new DragHelper();

    private final DragHelper moveVertexDragHelper = new DragHelper();

    private final List<VertexHandler> newlySelectedHandlers = Lists.newArrayList();

    private final List<Vector2> originalVertexPositions = Lists.newArrayList();

    private final List<Vector2> newVertexPositions = Lists.newArrayList();

    private final TerrainPatchEditor terrainPatchEditor;

    private final Vector2 originalVertexPosition = new Vector2();

    private boolean multipleSelections = false;

    private boolean multipleSelectionsDisabled = false;

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        VertexHandler hoveredHandler = terrainPatchEditor.getHoveredVertexHandler();

        if (hoveredHandler != null) {
            if (terrainPatchEditor.getSelectedHandlers().contains(hoveredHandler)) {
                moveVertexDragHelper.beginDrag(mouseWorld(screenX, screenY));
            } else {
                hoveredHandler.setDragged(true);

                clearSelection();
                originalVertexPosition.set(terrainPatchEditor.getVertex(hoveredHandler));

                moveVertexDragHelper.beginDrag(mouseWorld(screenX, screenY));
            }
            return true;
        }

        // begin a new selection
        selectionDragHelper.beginDrag(mouseWorld(screenX, screenY));
        if (!multipleSelections) {
            clearSelection();
        }
        newlySelectedHandlers.clear();

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        VertexHandler hoveredHandler = terrainPatchEditor.getHoveredVertexHandler();

        if (moveVertexDragHelper.isDragging()) {
            if (multipleHandlersSelected()) {
                newVertexPositions.clear();
                for (VertexHandler selectedHandler : terrainPatchEditor.getSelectedHandlers()) {
                    newVertexPositions.add(terrainPatchEditor.getVertex(selectedHandler));
                }
                ActionManager.instance().runAction(new MoveMultipleHandlersAction(
                        terrainPatchEditor,
                        terrainPatchEditor.getSelectedHandlers(),
                        originalVertexPositions,
                        newVertexPositions));
            } else {
                ActionManager.instance().runAction(new MoveHandlerAction(terrainPatchEditor, hoveredHandler, originalVertexPosition, terrainPatchEditor.getVertex(hoveredHandler).cpy()));
            }

            hoveredHandler.setDragged(false);
            moveVertexDragHelper.endDrag();


            return true;
        }

        if (selectionDragHelper.isDragging()) {
            selectionDragHelper.endDrag();

            if (multipleSelectionsDisabled) {
                multipleSelectionsDisabled = false;
                multipleSelections = false;
            }

            if (!newlySelectedHandlers.isEmpty()) {
                ActionManager.instance().runAction(new SelectHandlersAction(terrainPatchEditor, newlySelectedHandlers));
            }

            // preserve original positions
            originalVertexPositions.clear();
            for (VertexHandler selectedHandler : terrainPatchEditor.getSelectedHandlers()) {
                originalVertexPositions.add(terrainPatchEditor.getVertex(selectedHandler));
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (moveVertexDragHelper.isDragging()) {
            Vector2 dragOffset = moveVertexDragHelper.getDragOffset(mouseWorld(screenX, screenY));
            moveVertexDragHelper.reset(mouseWorld(screenX, screenY));

            if (multipleHandlersSelected()) {
                for (VertexHandler selectedHandler : terrainPatchEditor.getSelectedHandlers()) {
                    terrainPatchEditor.setVertex(selectedHandler, terrainPatchEditor.getVertex(selectedHandler).cpy().add(dragOffset));
                }
            } else {
                VertexHandler draggedHandler = terrainPatchEditor.getDraggedVertexHandler();
                terrainPatchEditor.setVertex(draggedHandler, terrainPatchEditor.getVertex(draggedHandler).cpy().add(dragOffset));
            }

            return true;
        }

        if (selectionDragHelper.isDragging()) {
            selectionDragHelper.notifyMouseMoved(mouseWorld(screenX, screenY));
            Vector2 vertexPosition = new Vector2();
            Rectangle selectionRect = Algorithms.createRectangle(selectionDragHelper.getDragStartPosition(), selectionDragHelper.getLastPosition());

            for (VertexHandler handler : terrainPatchEditor.getVertexHandlers()) {
                vertexPosition.set(terrainPatchEditor.getVertex(handler));

                boolean selectionCoversHandler = selectionRect.contains(vertexPosition);

                if (selectionCoversHandler && !handler.isSelected()) {
                    newlySelectedHandlers.add(handler);
                    handler.setSelected(true);
                }

                if (!selectionCoversHandler && newlySelectedHandlers.contains(handler)) {
                    handler.setSelected(false);
                    newlySelectedHandlers.remove(handler);
                }
            }
        }

        return false;
    }

    private boolean multipleHandlersSelected() {
        return !terrainPatchEditor.getSelectedHandlers().isEmpty();
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        for (VertexHandler handler : terrainPatchEditor.getVertexHandlers()) {
            Vector2 vertex = terrainPatchEditor.getVertex(handler);
            Vector2 m = mouseWorld(screenX, screenY);

            handler.setHovered(Math.abs(vertex.x - m.x) < handler.getHitSize() / 2 / RenderSystem.instance().getZoom() &&
                    Math.abs(vertex.y - m.y) < handler.getHitSize() / 2 / RenderSystem.instance().getZoom());
        }

        return false;
    }


    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.X) {
            ActionManager.instance().runAction(new AlignHandlersVerticallyAction(terrainPatchEditor, terrainPatchEditor.getSelectedHandlers()));
            return true;
        }

        if (keycode == Input.Keys.Y) {
            ActionManager.instance().runAction(new AlignHandlersHorizontallyAction(terrainPatchEditor, terrainPatchEditor.getSelectedHandlers()));
            return true;
        }


        if (keycode == Input.Keys.E && terrainPatchEditor.isBound()) {
            terrainPatchEditor.getTextureOverlayEditor().show();
            return true;
        }

        if (keycode == Input.Keys.ESCAPE && terrainPatchEditor.getTextureOverlayEditor().isVisible()) {
            terrainPatchEditor.getTextureOverlayEditor().hide();
            return true;
        }

        if (keycode == Input.Keys.CONTROL_LEFT) {
            multipleSelectionsDisabled = false;
            multipleSelections = true;
            return true;
        }

        if (keycode == Input.Keys.FORWARD_DEL) {
            deleteSelectedVertices();
            return true;
        }

        return false;
    }

    private void deleteSelectedVertices() {
        List<Vector2> allVertices = Lists.newArrayList();
        List<Vector2> remainingVertices = Lists.newArrayList();

        for (VertexHandler handler : terrainPatchEditor.getVertexHandlers()) {
            allVertices.add(terrainPatchEditor.getVertex(handler));
            if (!handler.isSelected()) {
                remainingVertices.add(terrainPatchEditor.getVertex(handler));
            }
        }

        if (remainingVertices.size() < 3 || terrainPatchEditor.getSelectedHandlers().size() == 0) {
            terrainPatchEditor.deleteBoundPolygon();
        } else {
            ActionManager.instance().runAction(new ActionManager.CompositeAction(ImmutableList.of(
                    new DeselectAllHandlersAction(terrainPatchEditor, terrainPatchEditor.getVertexHandlers(), terrainPatchEditor.getSelectedHandlers()),
                    new RemoveVerticesAction(terrainPatchEditor, allVertices, remainingVertices))));
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.CONTROL_LEFT) {
            if (selectionDragHelper.isDragging()) {
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
        DebugValues.instance().setValue(DebugItems.MULTIPLE_SELECT, String.valueOf(multipleSelections));
    }

    @Override
    public void stop() {
        moveVertexDragHelper.endDrag();
        if (terrainPatchEditor.getHoveredVertexHandler() != null) {
            terrainPatchEditor.getHoveredVertexHandler().setHovered(false);
        }

        DebugValues.instance().clearValue(DebugItems.MULTIPLE_SELECT);
        selectionDragHelper.endDrag();
        clearSelection();
    }

    @Override
    public void render() {
        DebugValues.instance().setValue(DebugItems.MULTIPLE_SELECT, String.valueOf(multipleSelections));
        if (selectionDragHelper.isDragging()) {
            ShapeRenderer shapeRenderer = RenderSystem.instance().getShapeRenderer();
            float x1 = selectionDragHelper.getDragStartPosition().x;
            float y1 = selectionDragHelper.getDragStartPosition().y;
            float x2 = selectionDragHelper.getLastPosition().x;
            float y2 = selectionDragHelper.getLastPosition().y;

            Gdx.gl20.glEnable(GL20.GL_BLEND);

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

    private void clearSelection() {
        ActionManager.instance().runAction(new DeselectAllHandlersAction(terrainPatchEditor, terrainPatchEditor.getVertexHandlers(), terrainPatchEditor.getSelectedHandlers()));
        originalVertexPositions.clear();
        newlySelectedHandlers.clear();
    }

    private Vector2 mouseWorld(int screenX, int screenY) {
        return RenderSystem.instance().screenToWorld(new Vector2(screenX, screenY));
    }
}
