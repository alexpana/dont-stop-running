package org.vertexarmy.dsr.leveleditor.polygoneditor.actions;

import com.badlogic.gdx.math.Vector2;
import org.vertexarmy.dsr.core.ActionManager;
import org.vertexarmy.dsr.leveleditor.polygoneditor.PolygonEditor;

/**
 * Created by alex
 * on 25.03.2015.
 */
public class AddNewVertexAction implements ActionManager.Action {
    private final PolygonEditor polygonEditor;

    private final int index;

    private final Vector2 position;

    public AddNewVertexAction(PolygonEditor polygonEditor, int index, Vector2 position) {
        this.polygonEditor = polygonEditor;
        this.index = index;
        this.position = position;
    }

    @Override
    public void doAction() {
        polygonEditor.addVertex(index, position);
    }

    @Override
    public void undoAction() {
        polygonEditor.removeVertex(index);
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
