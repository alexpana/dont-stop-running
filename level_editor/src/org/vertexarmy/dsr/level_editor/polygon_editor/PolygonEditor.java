package org.vertexarmy.dsr.level_editor.polygon_editor;

import com.badlogic.gdx.math.Vector2;
import com.beust.jcommander.internal.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import org.vertexarmy.dsr.core.component.ComponentType;
import org.vertexarmy.dsr.core.component.Node;
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
        SELECT,
        ADD_VERTEX,
        DELETE_VERTEX;
    }

    private EditModeType currentEditModeType = EditModeType.SELECT;

    private Map<EditModeType, EditMode> editModes = Maps.newHashMap();

    public PolygonEditor(Polygon polygon) {
        this.polygon = polygon;

        for (int i = 0; i < polygon.getVertexCount(); ++i) {
            vertexHandlers.add(new VertexHandler(i));
        }

        editModes.put(EditModeType.DEFAULT, new EditModeDefault(this));
        editModes.put(EditModeType.SELECT, new EditModeSelect(this));

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
        return new Vector2(polygon.getVertices()[handler.vertexIndex * 2], polygon.getVertices()[handler.vertexIndex * 2 + 1]);
    }

    public void setVertex(VertexHandler handler, float x, float y) {
        polygon.getVertices()[handler.vertexIndex * 2] = x;
        polygon.getVertices()[handler.vertexIndex * 2 + 1] = y;
    }
}
