package org.vertexarmy.dsr.leveleditor.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import java.awt.Container;
import org.vertexarmy.dsr.graphics.GraphicsUtils;

/**
 * Created by alex
 * on 26.03.2015.
 */
public class Toolbox extends Table {
    private ImageButton openLevelButton;

    private ImageButton saveLevelButton;

    public Toolbox(Skin uiSkin) {
        this.setBackground(new NinePatchDrawable(new NinePatch(uiSkin.getRegion("panel"), 1, 1, 2, 1)));

        createComponents(uiSkin);

        layoutComponents();
    }

    private void createComponents(Skin uiSkin) {
        openLevelButton = createImageButton(IconRepository.FILE_OPEN);
        saveLevelButton = createImageButton(IconRepository.FILE_SAVE);
    }

    private ImageButton createImageButton(String iconName) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = createImageUp(iconName);
        style.imageOver = createImageOver(iconName);
        style.imageDown = createImageDown(iconName);

        return new ImageButton(style);
    }

    private void layoutComponents() {
        this.align(Align.left);
        this.pad(2, 2, 2, 2);

        this.add(openLevelButton);
        this.add(saveLevelButton);
    }

    private Drawable createImageUp(String iconName) {
        return new TextureRegionDrawable(IconRepository.instance().getIcon(iconName));
    }

    private Drawable createImageOver(String iconName) {
        return new CompositeDrawable(
                IconRepository.instance().getIcon(IconRepository.COMPONENT_HOVER),
                IconRepository.instance().getIcon(iconName));
    }

    private Drawable createImageDown(String iconName) {
        return new CompositeDrawable(
                IconRepository.instance().getIcon(IconRepository.COMPONENT_PRESSED),
                IconRepository.instance().getIcon(iconName));
    }

    private class CompositeDrawable extends TextureRegionDrawable {
        private final TextureRegion first;
        private final TextureRegion second;

        public CompositeDrawable(TextureRegion first, TextureRegion second) {
            super(second);
            this.first = first;
            this.second = second;
        }

        @Override
        public void draw(Batch batch, float x, float y, float width, float height) {
            batch.draw(first, x, y, width, height);
            batch.draw(second, x, y, width, height);
        }

        @Override
        public void draw(Batch batch, float x, float y, float originX, float originY, float width, float height, float scaleX,
                         float scaleY, float rotation) {
            batch.draw(first, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
            batch.draw(second, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
        }
    }
}
