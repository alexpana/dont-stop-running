package org.vertexarmy.dsr.leveleditor.editors.polygon;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.core.systems.RenderSystem;
import org.vertexarmy.dsr.leveleditor.DebugValues;
import org.vertexarmy.dsr.leveleditor.editors.polygon.actions.AddNewVertexAction;
import org.vertexarmy.dsr.math.Algorithms;
import org.vertexarmy.dsr.math.Edge;

import java.util.List;

/**
 * Created by alex
 * on 25.03.2015.
 */
public class EditModeAddVertex extends InputAdapter implements EditMode {
    private static final float MIN_PROJECTION_DISTANCE = 10.0f;

    private final PolygonEditor polygonEditor;

    public final Vector2 shadowVertex = new Vector2();

    public int shadowEdgeIndex;

    private boolean closeToEdge = false;

    public EditModeAddVertex(PolygonEditor polygonEditor) {
        this.polygonEditor = polygonEditor;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        List<Edge> edges = polygonEditor.getBoundObject().getEdgeList();

        Vector2 mousePosition = RenderSystem.instance().screenToWorld(new Vector2(screenX, screenY));

        float min = MIN_PROJECTION_DISTANCE;

        closeToEdge = false;

        int edgeIndex = 0;

        for (Edge e : edges) {
            Vector2 projection = Algorithms.vertexToEdgeProjection(e, mousePosition);

            if (Algorithms.segmentContainsVertex(e.start, e.end, projection)) {
                float mouseToEdgeDistance = Algorithms.vertexToEdgeDistance(e, mousePosition);
                if (mouseToEdgeDistance < min) {
                    min = mouseToEdgeDistance;
                    shadowVertex.set(projection);
                    shadowEdgeIndex = edgeIndex;
                    closeToEdge = true;
                }
            }

            edgeIndex += 1;
        }

        DebugValues.instance().setValue("edge distance", String.valueOf(min));
        DebugValues.instance().setValue("new vertex shadow", String.valueOf(shadowVertex));

        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (closeToEdge) {
            ActionManager.instance().runAction(new AddNewVertexAction(polygonEditor, shadowEdgeIndex + 1, shadowVertex.cpy()));
            return true;
        }
        return false;
    }

    @Override
    public void render() {
        if (closeToEdge) {
            ShapeRenderer shapeRenderer = RenderSystem.instance().getShapeRenderer();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(new Color(0x70FF70ff));
            shapeRenderer.circle(shadowVertex.x, shadowVertex.y, 4);
            shapeRenderer.end();
        }
    }
}
