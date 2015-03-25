package org.vertexarmy.dsr.leveleditor.polygoneditor;

import com.badlogic.gdx.math.Vector2;
import com.beust.jcommander.internal.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import org.vertexarmy.dsr.core.component.ComponentType;
import org.vertexarmy.dsr.core.component.Node;
import org.vertexarmy.dsr.leveleditor.DebugItems;
import org.vertexarmy.dsr.leveleditor.DebugValues;
import org.vertexarmy.dsr.math.Polygon;

/**
 * created by Alex
 * on 3/21/2015.
 */
public class PolygonEditor {
    @Getter(value = AccessLevel.PACKAGE)
    private final List<VertexHandler> vertexHandlers = Lists.newArrayList();

    @Getter
    private final Polygon polygon;

    @Getter
    private final Node node;


    public static enum EditModeType {
        DEFAULT,
        ADD_VERTEX,
        DELETE_VERTEX
    }

    private EditModeType currentEditModeType = EditModeType.DEFAULT;

    private final Map<EditModeType, EditMode> editModes = Maps.newHashMap();

    public PolygonEditor(Polygon polygon) {
        this.polygon = polygon;

        for (int i = 0; i < polygon.getVertexCount(); ++i) {
            vertexHandlers.add(new VertexHandler(i));
        }

        editModes.put(EditModeType.DEFAULT, new EditModeDefault(this));
        editModes.put(EditModeType.ADD_VERTEX, new EditModeDefault(this));
        editModes.put(EditModeType.DELETE_VERTEX, new EditModeDefault(this));

        setEditMode(EditModeType.DEFAULT);

        node = new Node();
        node.addComponent(ComponentType.RENDER, new PolygonEditorRenderComponent(this));
        node.addComponent(ComponentType.INPUT, new PolygonEditorInputComponent(this));
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

    public List<VertexHandler> getSelectedHandlers() {
        List<VertexHandler> selectedHandlers = Lists.newArrayList();

        for (VertexHandler handler : vertexHandlers) {
            if (handler.isSelected()) {
                selectedHandlers.add(handler);
            }
        }

        return selectedHandlers;
    }

    public Vector2 getVertex(VertexHandler handler) {
        return new Vector2(polygon.getVertices()[handler.getVertexIndex() * 2], polygon.getVertices()[handler.getVertexIndex() * 2 + 1]);
    }

    public void setVertex(VertexHandler handler, Vector2 position) {
        setVertex(handler, position.x, position.y);
    }

    public void setVertex(VertexHandler handler, float x, float y) {
        polygon.getVertices()[handler.getVertexIndex() * 2] = x;
        polygon.getVertices()[handler.getVertexIndex() * 2 + 1] = y;
    }
}
