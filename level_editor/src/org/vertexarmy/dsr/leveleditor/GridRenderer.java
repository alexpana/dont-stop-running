package org.vertexarmy.dsr.leveleditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import lombok.Getter;
import lombok.Setter;
import org.vertexarmy.dsr.core.assets.FontRepository;
import org.vertexarmy.dsr.core.systems.RenderSystem;

/**
 * created by Alex
 * on 3/28/2015.
 */
public class GridRenderer {
    private static final Color GRID_X_INDICATOR_COLOR = new Color(0xff4d4dff);

    private static final Color GRID_Y_INDICATOR_COLOR = new Color(0x49ff4dff);

    private static final Color GRID_DEFAULT_COLOR = new Color(0x494d4dff);

    private static final Color MAX_HEIGHT_INDICATOR_COLOR = new Color(0x728ad5ff);

    private static final Color RULER_DEFAULT_COLOR = new Color(0x7d7d7dff);

    private static final Color RULER_ACTIVE_COLOR = Color.WHITE;

    private static final int GRID_SIZE = 50;

    private static final int MAX_HEIGHT_INDICATOR_VALUE = 800;

    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch spriteBatch;
    private final BitmapFont gridFont;

    @Getter
    @Setter
    private boolean visible = true;

    @Getter
    @Setter
    private boolean gridVisible = true;

    @Getter
    @Setter
    private boolean rulersVisible = true;

    @Getter
    @Setter
    private boolean highMarkVisible = true;

    @Getter
    @Setter
    private boolean baseAxesVisible = true;

    public GridRenderer() {
        shapeRenderer = RenderSystem.instance().getShapeRenderer();
        spriteBatch = RenderSystem.instance().getSpriteBatch();

        gridFont = FontRepository.instance().getFont(AssetName.FONT_MARKE_8);
        gridFont.setColor(RULER_DEFAULT_COLOR);
    }

    public void renderGrid() {
        if (!visible) {
            return;
        }

        Vector2 topLeft = RenderSystem.instance().screenToWorld(Vector2.Zero);
        Vector2 bottomRight = RenderSystem.instance().screenToWorld(new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        float screenLeft = topLeft.x;
        float screenRight = bottomRight.x;
        float screenTop = topLeft.y;
        float screenBottom = bottomRight.y;

        float xStart = ((int) (screenLeft / GRID_SIZE) * GRID_SIZE);
        float yStart = ((int) (screenTop / GRID_SIZE) * GRID_SIZE);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(GRID_DEFAULT_COLOR);

        if (gridVisible) {
            for (float x = xStart; x <= screenRight; x += GRID_SIZE) {
                shapeRenderer.line(x, screenTop, x, screenBottom);
            }

            for (float y = yStart; y >= screenBottom; y -= GRID_SIZE) {
                shapeRenderer.line(screenLeft, y, screenRight, y);
            }
        }

        if (baseAxesVisible) {
            if (screenLeft <= 0 && screenRight >= 0) {
                shapeRenderer.setColor(GRID_X_INDICATOR_COLOR);
                shapeRenderer.line(0, screenTop, 0, screenBottom);
            }

            if (screenTop >= 0 && screenBottom <= 0) {
                shapeRenderer.setColor(GRID_Y_INDICATOR_COLOR);
                shapeRenderer.line(screenLeft, 0, screenRight, 0);
            }
        }

        if (highMarkVisible) {
            if (screenTop >= MAX_HEIGHT_INDICATOR_VALUE && screenBottom <= MAX_HEIGHT_INDICATOR_VALUE) {
                shapeRenderer.setColor(MAX_HEIGHT_INDICATOR_COLOR);
                shapeRenderer.line(screenLeft, MAX_HEIGHT_INDICATOR_VALUE, screenRight, MAX_HEIGHT_INDICATOR_VALUE);
            }
        }

        shapeRenderer.end();

        if (rulersVisible) {
            drawRulerIndicators();
        }
    }

    private void drawRulerIndicators() {
        float zoom = RenderSystem.instance().getZoom();
        float gridSize = GRID_SIZE * zoom;

        Vector2 topLeft = RenderSystem.instance().screenToWorld(Vector2.Zero);
        Vector2 bottomRight = RenderSystem.instance().screenToWorld(new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        float screenLeft = topLeft.x;
        float screenRight = bottomRight.x;
        float screenTop = topLeft.y;
        float screenBottom = bottomRight.y;

        float xStart = (((int) (screenLeft * zoom / gridSize) * gridSize));
        float yStart = (((int) (screenTop * zoom / gridSize) * gridSize));

        int horizontalSteps = zoom < 0.5 ? (zoom < 0.2 ? 4 : 2) : 1;

        Vector2 mousePosition = getMousePosition();

        Matrix4 viewMatrix = RenderSystem.instance().getCamera().view.cpy();

        Vector3 vector3 = new Vector3();
        viewMatrix.getTranslation(vector3).scl(zoom);

        viewMatrix.setTranslation(vector3);

        spriteBatch.setProjectionMatrix(RenderSystem.instance().getStandardCamera().projection);
        spriteBatch.setTransformMatrix(viewMatrix);

        spriteBatch.begin();

        // Horizontal ruler
        for (float x = xStart; x <= screenRight * zoom; x += horizontalSteps * gridSize) {
            final String indicator = getIndicatorValue(x);
            if (indicator.equals("0")) {
                continue;
            }
            gridFont.setColor(getRulerColor(mousePosition.x * zoom, x, x + gridSize));
            gridFont.draw(spriteBatch, indicator, (int) x + 1, (int) ((screenBottom * zoom) + gridFont.getLineHeight()));
        }

        // Vertical ruler
        for (float y = yStart; y >= screenBottom * zoom; y -= gridSize) {
            final String indicator = getIndicatorValue(y);
            if (indicator.equals("0")) {
                continue;
            }
            gridFont.setColor(getRulerColor(mousePosition.y * zoom, y, y + gridSize));
            gridFont.draw(spriteBatch, indicator, (int) screenLeft * zoom, (int) ((y + gridFont.getLineHeight()) / 1));
        }

        if (screenTop >= MAX_HEIGHT_INDICATOR_VALUE && screenBottom <= MAX_HEIGHT_INDICATOR_VALUE) {
            if (highMarkVisible) {
                gridFont.setColor(MAX_HEIGHT_INDICATOR_COLOR);
            } else {
                gridFont.setColor(getRulerColor(mousePosition.y, MAX_HEIGHT_INDICATOR_VALUE, MAX_HEIGHT_INDICATOR_VALUE + GRID_SIZE));
            }
            gridFont.draw(spriteBatch, String.valueOf(MAX_HEIGHT_INDICATOR_VALUE), (int) screenLeft * zoom, MAX_HEIGHT_INDICATOR_VALUE * zoom + gridFont.getLineHeight());
        }

        if (screenLeft <= 0 && screenRight >= 0) {
            if (baseAxesVisible) {
                gridFont.setColor(GRID_X_INDICATOR_COLOR);
            } else {
                gridFont.setColor(getRulerColor(mousePosition.x, 0, GRID_SIZE));
            }
            gridFont.draw(spriteBatch, "0", 1, (int) (screenBottom * zoom + gridFont.getLineHeight()));
        }

        if (screenTop >= 0 && screenBottom <= 0) {
            if (baseAxesVisible) {
                gridFont.setColor(GRID_Y_INDICATOR_COLOR);
            } else {
                gridFont.setColor(getRulerColor(mousePosition.y, 0, GRID_SIZE));
            }

            gridFont.draw(spriteBatch, "0", (int) (screenLeft * zoom), (int) (gridFont.getLineHeight()));
        }

        spriteBatch.end();
    }

    private String getIndicatorValue(float indicator) {
        float zoom = RenderSystem.instance().getZoom();
        float actualIndicatorValue = indicator / zoom;

        return String.valueOf(((int) (actualIndicatorValue / GRID_SIZE + Math.signum(actualIndicatorValue) * 0.5) * GRID_SIZE));
    }

    private Color getRulerColor(float mousePosition, float lower, float upper) {
        return mousePosition >= lower && mousePosition < upper ? RULER_ACTIVE_COLOR : RULER_DEFAULT_COLOR;
    }

    private Vector2 getMousePosition() {
        return RenderSystem.instance().screenToWorld(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
    }
}
