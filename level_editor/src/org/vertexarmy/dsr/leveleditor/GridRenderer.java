package org.vertexarmy.dsr.leveleditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.vertexarmy.dsr.core.assets.FontRepository;
import org.vertexarmy.dsr.core.systems.RenderSystem;

/**
 * created by Alex
 * on 3/28/2015.
 */
public class GridRenderer {
    public static final Color RULER_DEFAULT_COLOR = new Color(0x7d7d7dff);
    public static final Color RULER_ACTIVE_COLOR = Color.WHITE;

    public static final int GRID_SIZE = 30;

    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch spriteBatch;
    private final BitmapFont gridFont;

    public GridRenderer() {
        shapeRenderer = RenderSystem.instance().getShapeRenderer();
        spriteBatch = RenderSystem.instance().getSpriteBatch();

        gridFont = FontRepository.instance().getFont(AssetName.FONT_MARKE_8);
        gridFont.setColor(RULER_DEFAULT_COLOR);

    }

    public void renderGrid() {
        Vector2 mousePosition = RenderSystem.instance().screenToWorld(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        boolean xIndicatorRequired = false;
        boolean yIndicatorRequired = false;

        float gridSize = GRID_SIZE / RenderSystem.instance().getZoom();

        Vector2 topLeft = RenderSystem.instance().screenToWorld(Vector2.Zero);
        Vector2 topRight = RenderSystem.instance().screenToWorld(new Vector2(Gdx.graphics.getWidth(), 0));
        Vector2 bottomRight = RenderSystem.instance().screenToWorld(new Vector2(0, Gdx.graphics.getHeight()));

        float xStart = ((int) (topLeft.x / gridSize) * gridSize);
        float yStart = ((int) (topLeft.y / gridSize) * gridSize);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(new Color(0x494d4dff));

        while (xStart <= topRight.x) {
            if (Math.abs(xStart) < 1) {
                xIndicatorRequired = true;
            }
            shapeRenderer.line(xStart, topLeft.y, xStart, bottomRight.y);
            xStart += gridSize;
        }

        while (yStart >= bottomRight.y) {
            if (Math.abs(yStart) < 1) {
                yIndicatorRequired = true;
            }
            shapeRenderer.line(topLeft.x, yStart, topRight.x, yStart);
            yStart -= gridSize;
        }

        if (xIndicatorRequired) {
            shapeRenderer.setColor(new Color(0xff4d4dff));
            shapeRenderer.line(0, topLeft.y, 0, bottomRight.y);
        }

        if (yIndicatorRequired) {
            shapeRenderer.setColor(new Color(0x49ff4dff));
            shapeRenderer.line(topLeft.x, 0, topRight.x, 0);
        }
        shapeRenderer.end();

        xStart = ((int) (topLeft.x / gridSize) * gridSize);
        yStart = ((int) (topLeft.y / gridSize) * gridSize);

        spriteBatch.begin();

        while (xStart <= topRight.x) {
            if (mousePosition.x >= xStart && mousePosition.x < xStart + gridSize) {
                gridFont.setColor(RULER_ACTIVE_COLOR);
            } else {
                gridFont.setColor(RULER_DEFAULT_COLOR);
            }
            gridFont.draw(spriteBatch, String.valueOf((int) xStart), xStart, bottomRight.y + gridFont.getLineHeight());
            xStart += gridSize;
        }

        while (yStart >= bottomRight.y) {
            if (mousePosition.y >= yStart && mousePosition.y < yStart + gridSize) {
                gridFont.setColor(RULER_ACTIVE_COLOR);
            } else {
                gridFont.setColor(RULER_DEFAULT_COLOR);
            }
            gridFont.draw(spriteBatch, String.valueOf((int) yStart), topLeft.x, yStart + gridFont.getLineHeight());
            yStart -= gridSize;
        }

        spriteBatch.end();

    }
}
