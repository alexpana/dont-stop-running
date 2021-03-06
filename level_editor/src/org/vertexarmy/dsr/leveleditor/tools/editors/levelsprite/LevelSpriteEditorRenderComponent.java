package org.vertexarmy.dsr.leveleditor.tools.editors.levelsprite;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import lombok.RequiredArgsConstructor;
import org.vertexarmy.dsr.core.component.RenderComponent;
import org.vertexarmy.dsr.core.systems.RenderSystem;
import org.vertexarmy.dsr.leveleditor.LevelSpriteUtils;

/**
 * created by Alex
 * on 04-Apr-2015.
 */
@RequiredArgsConstructor
public class LevelSpriteEditorRenderComponent extends RenderComponent {

    private final LevelSpriteEditTool editor;

    @Override
    public void render() {
        if (!editor.isBound()) {
            return;
        }

        renderOutline();

//        renderRotateHandler();

//        renderScaleHandler();
    }

    private void renderOutline() {
        ShapeRenderer shapeRenderer = RenderSystem.instance().getShapeRenderer();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.polygon(LevelSpriteUtils.getSpriteBounds(editor.getBoundObject()).toFloatArray());
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
