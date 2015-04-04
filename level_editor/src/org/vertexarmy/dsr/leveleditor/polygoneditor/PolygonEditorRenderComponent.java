package org.vertexarmy.dsr.leveleditor.polygoneditor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.vertexarmy.dsr.core.component.RenderComponent;
import org.vertexarmy.dsr.core.systems.RenderSystem;
import org.vertexarmy.dsr.math.Polygon;

import java.util.List;

/**
 * created by Alex
 * on 3/23/2015.
 */
class PolygonEditorRenderComponent extends RenderComponent {
    private static final Color DRAGGED_VERTEX_HANDLER_COLOR = Color.GREEN;
    private static final Color SELECTED_VERTEX_HANDLER_COLOR = Color.RED;
    private static final Color DEFAULT_VERTEX_HANDLER_COLOR = Color.YELLOW;
    private final List<VertexHandler> vertexHandlers;

    private final PolygonEditor polygonEditor;

    private final ShapeRenderer shapeRenderer = RenderSystem.instance().getShapeRenderer();

    public PolygonEditorRenderComponent(PolygonEditor polygonEditor) {
        this.polygonEditor = polygonEditor;
        vertexHandlers = polygonEditor.getVertexHandlers();
    }

    @Override
    public void render() {
        if (!polygonEditor.isBoundToPolygon()) {
            return;
        }

        Polygon polygon = polygonEditor.getPolygon();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(new Color(0.35f, 0.35f, 0.35f, 1.0f));
        for (int i = 1; i < polygon.getVertexCount(); ++i) {
            drawEdge(i - 1, i);
        }
        drawEdge(0, polygon.getVertexCount() - 1);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
        for (VertexHandler handler : vertexHandlers) {
            drawHandler(handler);
        }
        shapeRenderer.end();

        polygonEditor.getEditMode().render();
    }

    private void drawHandler(VertexHandler handler) {
        Polygon polygon = polygonEditor.getPolygon();

        float zoom = RenderSystem.instance().getZoom();
        Vector2 vertex = polygon.getVertex(handler.getVertexIndex());

        if (handler.isDragged() || handler.isHovered()) {
            shapeRenderer.setColor(DRAGGED_VERTEX_HANDLER_COLOR);
        } else if (handler.isSelected()) {
            shapeRenderer.setColor(SELECTED_VERTEX_HANDLER_COLOR);
        } else {
            shapeRenderer.setColor(DEFAULT_VERTEX_HANDLER_COLOR);
        }
        drawRect(vertex.x, vertex.y, handler.getHitSize() / zoom);
    }

    private void drawRect(float x, float y, float size) {
        shapeRenderer.rect(x - size / 2, y - size / 2, size, size);
    }

    private void drawEdge(int indexFrom, int indexTo) {
        Polygon polygon = polygonEditor.getPolygon();

        Vector2 from = polygon.getVertex(indexFrom);
        Vector2 to = polygon.getVertex(indexTo);

        shapeRenderer.line(from.x, from.y, to.x, to.y);
    }
}
