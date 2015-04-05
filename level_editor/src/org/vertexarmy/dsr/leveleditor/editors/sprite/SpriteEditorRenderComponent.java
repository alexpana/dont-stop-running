package org.vertexarmy.dsr.leveleditor.editors.sprite;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import lombok.RequiredArgsConstructor;
import org.vertexarmy.dsr.core.component.RenderComponent;
import org.vertexarmy.dsr.core.systems.RenderSystem;

/**
 * created by Alex
 * on 04-Apr-2015.
 */
@RequiredArgsConstructor
public class SpriteEditorRenderComponent extends RenderComponent {

    private final SpriteEditor editor;

    @Override
    public void render() {
        if (!editor.isBoundToSprite()) {
            return;
        }

        renderOutline();

//        renderRotateHandler();

//        renderScaleHandler();
    }

    private void renderOutline() {
        ShapeRenderer shapeRenderer = RenderSystem.instance().getShapeRenderer();
        Vector2 bottomLeftCorner = editor.getSpriteBottomLeftCorner();
        Vector2 topRightCorner = editor.getSpriteTopRightCorner();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(bottomLeftCorner.x, bottomLeftCorner.y, topRightCorner.x - bottomLeftCorner.x, topRightCorner.y - bottomLeftCorner.y);
        shapeRenderer.end();
    }

    private void renderScaleHandler() {
        float zoom = RenderSystem.instance().getZoom();
        ShapeRenderer shapeRenderer = RenderSystem.instance().getShapeRenderer();
        Vector2 topRightCorner = editor.getSpriteTopRightCorner();

        float handlerSize = editor.getScaleHandler().getSize();

        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(topRightCorner.x - handlerSize / 2 / zoom, topRightCorner.y - handlerSize / 2 / zoom, handlerSize / zoom, handlerSize / zoom);
        shapeRenderer.end();
    }

    private void renderRotateHandler() {
        float zoom = RenderSystem.instance().getZoom();
        ShapeRenderer shapeRenderer = RenderSystem.instance().getShapeRenderer();
        Vector2 bottomLeftCorner = editor.getSpriteBottomLeftCorner();
        Vector2 topRightCorner = editor.getSpriteTopRightCorner();

        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(topRightCorner.x, bottomLeftCorner.y, editor.getRotateHandler().getSize() / 2 / zoom);
        shapeRenderer.end();
    }
}
