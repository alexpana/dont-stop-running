package org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch;

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
import org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch.actions.*;
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

    private final TerrainPatchEditTool terrainPatchEditTool;

    private final Vector2 originalVertexPosition = new Vector2();

    private boolean multipleSelections = false;

    private boolean multipleSelectionsDisabled = false;

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        VertexHandler hoveredHandler = terrainPatchEditTool.getHoveredVertexHandler();

        if (hoveredHandler != null) {
            if (terrainPatchEditTool.getSelectedHandlers().contains(hoveredHandler)) {
                moveVertexDragHelper.beginDrag(mouseWorld(screenX, screenY));
            } else {
                hoveredHandler.setDragged(true);

                clearSelection();
                originalVertexPosition.set(terrainPatchEditTool.getVertex(hoveredHandler));

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
        VertexHandler hoveredHandler = terrainPatchEditTool.getHoveredVertexHandler();

        if (moveVertexDragHelper.isDragging()) {
            if (multipleHandlersSelected()) {
                newVertexPositions.clear();
                for (VertexHandler selectedHandler : terrainPatchEditTool.getSelectedHandlers()) {
                    newVertexPositions.add(terrainPatchEditTool.getVertex(selectedHandler));
                }
                ActionManager.instance().runAction(new MoveMultipleHandlersAction(
                        terrainPatchEditTool,
                        terrainPatchEditTool.getSelectedHandlers(),
                        originalVertexPositions,
                        newVertexPositions));
            } else {
                ActionManager.instance().runAction(new MoveHandlerAction(terrainPatchEditTool, hoveredHandler, originalVertexPosition, terrainPatchEditTool.getVertex(hoveredHandler).cpy()));
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
                ActionManager.instance().runAction(new SelectHandlersAction(terrainPatchEditTool, newlySelectedHandlers));
            }

            // preserve original positions
            originalVertexPositions.clear();
            for (VertexHandler selectedHandler : terrainPatchEditTool.getSelectedHandlers()) {
                originalVertexPositions.add(terrainPatchEditTool.getVertex(selectedHandler));
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
                for (VertexHandler selectedHandler : terrainPatchEditTool.getSelectedHandlers()) {
                    terrainPatchEditTool.setVertex(selectedHandler, terrainPatchEditTool.getVertex(selectedHandler).cpy().add(dragOffset));
                }
            } else {
                VertexHandler draggedHandler = terrainPatchEditTool.getDraggedVertexHandler();
                terrainPatchEditTool.setVertex(draggedHandler, terrainPatchEditTool.getVertex(draggedHandler).cpy().add(dragOffset));
            }

            return true;
        }

        if (selectionDragHelper.isDragging()) {
            selectionDragHelper.notifyMouseMoved(mouseWorld(screenX, screenY));
            Vector2 vertexPosition = new Vector2();
            Rectangle selectionRect = Algorithms.createRectangle(selectionDragHelper.getDragStartPosition(), selectionDragHelper.getLastPosition());

            for (VertexHandler handler : terrainPatchEditTool.getVertexHandlers()) {
                vertexPosition.set(terrainPatchEditTool.getVertex(handler));

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
        return !terrainPatchEditTool.getSelectedHandlers().isEmpty();
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        for (VertexHandler handler : terrainPatchEditTool.getVertexHandlers()) {
            Vector2 vertex = terrainPatchEditTool.getVertex(handler);
            Vector2 m = mouseWorld(screenX, screenY);

            handler.setHovered(Math.abs(vertex.x - m.x) < handler.getHitSize() / 2 / RenderSystem.instance().getZoom() &&
                    Math.abs(vertex.y - m.y) < handler.getHitSize() / 2 / RenderSystem.instance().getZoom());
        }

        return false;
    }


    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.X) {
            ActionManager.instance().runAction(new AlignHandlersVerticallyAction(terrainPatchEditTool, terrainPatchEditTool.getSelectedHandlers()));
            return true;
        }

        if (keycode == Input.Keys.Y) {
            ActionManager.instance().runAction(new AlignHandlersHorizontallyAction(terrainPatchEditTool, terrainPatchEditTool.getSelectedHandlers()));
            return true;
        }


        if (keycode == Input.Keys.E && terrainPatchEditTool.isBound()) {
            terrainPatchEditTool.getTextureOverlayEditor().show();
            return true;
        }

        if (keycode == Input.Keys.ESCAPE && terrainPatchEditTool.getTextureOverlayEditor().isVisible()) {
            terrainPatchEditTool.getTextureOverlayEditor().hide();
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

        for (VertexHandler handler : terrainPatchEditTool.getVertexHandlers()) {
            allVertices.add(terrainPatchEditTool.getVertex(handler));
            if (!handler.isSelected()) {
                remainingVertices.add(terrainPatchEditTool.getVertex(handler));
            }
        }

        if (remainingVertices.size() < 3 || terrainPatchEditTool.getSelectedHandlers().size() == 0) {
            terrainPatchEditTool.deleteBoundPolygon();
        } else {
            ActionManager.instance().runAction(new ActionManager.CompositeAction(ImmutableList.of(
                    new DeselectAllHandlersAction(terrainPatchEditTool, terrainPatchEditTool.getVertexHandlers(), terrainPatchEditTool.getSelectedHandlers()),
                    new RemoveVerticesAction(terrainPatchEditTool, allVertices, remainingVertices))));
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
        if (terrainPatchEditTool.getHoveredVertexHandler() != null) {
            terrainPatchEditTool.getHoveredVertexHandler().setHovered(false);
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
        ActionManager.instance().runAction(new DeselectAllHandlersAction(terrainPatchEditTool, terrainPatchEditTool.getVertexHandlers(), terrainPatchEditTool.getSelectedHandlers()));
        originalVertexPositions.clear();
        newlySelectedHandlers.clear();
    }

    private Vector2 mouseWorld(int screenX, int screenY) {
        return RenderSystem.instance().screenToWorld(new Vector2(screenX, screenY));
    }
}
