package org.vertexarmy.dsr.leveleditor.polygoneditor.actions;

import com.badlogic.gdx.math.Vector2;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.leveleditor.polygoneditor.PolygonEditor;
import org.vertexarmy.dsr.leveleditor.polygoneditor.VertexHandler;

/**
 * Created by alex
 * on 24.03.2015.
 */
public class MoveHandlerAction extends ActionManager.ActionAdapter {
    private final Vector2 originalPosition = new Vector2();

    private final Vector2 newPosition = new Vector2();

    private final VertexHandler vertexHandler;

    private final PolygonEditor polygonEditor;

    public MoveHandlerAction(PolygonEditor polygonEditor, VertexHandler vertexHandler, Vector2 originalPosition, Vector2 newPosition) {
        this.polygonEditor = polygonEditor;
        this.vertexHandler = vertexHandler;
        this.originalPosition.set(originalPosition);
        this.newPosition.set(newPosition);
    }


    @Override
    public void doAction() {
        polygonEditor.setVertex(vertexHandler, newPosition.x, newPosition.y);
    }

    @Override
    public void undoAction() {
        polygonEditor.setVertex(vertexHandler, originalPosition.x, originalPosition.y);
    }

    @Override
    public boolean isValid() {
        return Vector2.dst2(originalPosition.x, originalPosition.y, newPosition.x, newPosition.y) >= 1;
    }
}
