package org.vertexarmy.dsr.leveleditor.tools.editors.terrainpatch;

import com.badlogic.gdx.math.Vector2;
import com.beust.jcommander.internal.Lists;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.vertexarmy.dsr.core.Root;
import org.vertexarmy.dsr.core.component.ComponentType;
import org.vertexarmy.dsr.core.component.Node;
import org.vertexarmy.dsr.game.level.TerrainPatch;
import org.vertexarmy.dsr.graphics.TextureOverlay;
import org.vertexarmy.dsr.leveleditor.DebugItems;
import org.vertexarmy.dsr.leveleditor.DebugValues;
import org.vertexarmy.dsr.leveleditor.tools.BindableTool;
import org.vertexarmy.dsr.leveleditor.ui.genericeditor.GenericEditor;
import org.vertexarmy.dsr.math.Polygon;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * created by Alex
 * on 3/21/2015.
 */
public class TerrainPatchEditTool extends BindableTool<TerrainPatch> {
    @Getter(value = AccessLevel.PACKAGE)
    private final List<VertexHandler> vertexHandlers = Lists.newArrayList();

    @Getter
    private Node node;

    @Getter
    @Setter
    private PolygonEditorListener listener;

    @Getter
    private GenericEditor textureOverlayEditor;

    public enum EditModeType {
        DEFAULT,
        ADD_VERTEX,
    }

    private EditModeType currentEditModeType = EditModeType.DEFAULT;

    private final Map<EditModeType, EditMode> editModes = Maps.newHashMap();

    public TerrainPatchEditTool(Root root) {
        editModes.put(EditModeType.DEFAULT, new EditModeDefault(this));
        editModes.put(EditModeType.ADD_VERTEX, new EditModeAddVertex(this));
        setEditMode(EditModeType.DEFAULT);

        textureOverlayEditor = new GenericEditor(root.getUiNode().getStage(), "Edit Overlay", root.getUiNode().getUiSkin(), TextureOverlay.class);

        node = new Node();
        node.addComponent(ComponentType.RENDER, new PolygonEditorRenderComponent(this));
        node.addComponent(ComponentType.INPUT, new PolygonEditorInputComponent(this));
    }

    public TerrainPatchEditTool(TerrainPatch terrainPatch) {
        super();
        bind(terrainPatch);
    }

    EditMode getEditMode() {
        return editModes.get(currentEditModeType);
    }

    void setEditMode(EditModeType editEditModeType) {
        if (currentEditModeType != editEditModeType) {
            getEditMode().stop();
            currentEditModeType = editEditModeType;
            getEditMode().start();

        }
        DebugValues.instance().setValue(DebugItems.EDIT_MODE, editEditModeType.name());
    }

    VertexHandler getHoveredVertexHandler() {
        for (VertexHandler handler : vertexHandlers) {
            if (handler.isHovered()) {
                return handler;
            }
        }
        return null;
    }

    VertexHandler getDraggedVertexHandler() {
        for (VertexHandler handler : vertexHandlers) {
            if (handler.isDragged()) {
                return handler;
            }
        }
        return null;
    }

    @Override
    public void doBind(TerrainPatch terrainPatch) {
        if (terrainPatch != null) {
            updateVertexHandlers();
            textureOverlayEditor.bindToObject(terrainPatch.getTextureOverlay());
            setEditMode(EditModeType.DEFAULT);
        } else {
            vertexHandlers.clear();
            textureOverlayEditor.bindToObject(null);
        }
    }


    public List<VertexHandler> getSelectedHandlers() {
        List<VertexHandler> selectedHandlers = Lists.newArrayList();

        for (VertexHandler handler : vertexHandlers) {
            if (handler.isSelected()) {
                selectedHandlers.add(handler);
            }
        }

        return selectedHandlers;
    }

    public Polygon getBoundPolygon() {
        if (isBound()) {
            return getBoundObject().getShape();
        } else {
            return null;
        }
    }

    public Vector2 getVertex(VertexHandler handler) {
        return getBoundPolygon().getVertex(handler.getVertexIndex());
    }

    public void setVertex(VertexHandler handler, Vector2 position) {
        setVertex(handler, position.x, position.y);
    }

    public void setVertex(VertexHandler handler, float x, float y) {
        getBoundPolygon().setVertex(handler.getVertexIndex(), new Vector2(x, y));
    }

    public void addVertex(int index, Vector2 position) {
        getBoundPolygon().addVertex(index, position);
        updateVertexHandlers();
    }

    public void removeVertex(int index) {
        VertexHandler vertexHandler = findHandlerByIndex(index);

        if (vertexHandler != null) {
            removeVertices(Collections.singletonList(vertexHandler));
        }
    }

    public void removeVertices(List<VertexHandler> vertexHandlers) {
        List<Integer> indexList = Lists.newArrayList();
        for (VertexHandler vertexHandler : vertexHandlers) {
            indexList.add(vertexHandler.getVertexIndex());
        }

        Collections.sort(indexList);

        for (int i = 0; i < indexList.size(); ++i) {
            getBoundPolygon().removeVertex(indexList.get(i) - i);
        }

        updateVertexHandlers();
    }

    public void setVertices(List<Vector2> vertices) {
        getBoundPolygon().setVertices(vertices);
        updateVertexHandlers();
    }

    private void updateVertexHandlers() {
        vertexHandlers.clear();
        for (int i = 0; i < getBoundPolygon().getVertexCount(); ++i) {
            vertexHandlers.add(new VertexHandler(i));
        }
    }

    public VertexHandler findHandlerByIndex(int index) {
        for (VertexHandler handler : vertexHandlers) {
            if (handler.getVertexIndex() == index) {
                return handler;
            }
        }
        return null;
    }

    public Vector2 getHoveredVertex() {
        for (VertexHandler handler : vertexHandlers) {
            if (handler.isHovered()) {
                return getVertex(handler);
            }
        }

        return null;
    }

    void deleteBoundPolygon() {
        if (isBound()) {
            notifyDeletePolygonRequested();
            unbind();
        }
    }

    private void notifyDeletePolygonRequested() {
        if (listener != null) {
            listener.deletePolygonRequested();
        }
    }
}
