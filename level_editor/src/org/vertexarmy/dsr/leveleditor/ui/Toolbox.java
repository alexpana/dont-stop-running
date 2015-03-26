package org.vertexarmy.dsr.leveleditor.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.vertexarmy.dsr.graphics.GraphicsUtils;

/**
 * Created by alex
 * on 26.03.2015.
 */
public class Toolbox extends Table {
    private TextButton openLevelButton;

    private TextButton saveLevelButton;

    public Toolbox(Skin uiSkin) {
        this.setBackground(new TextureRegionDrawable(new TextureRegion(GraphicsUtils.getColorTexture(new Color(0x202020ff)))));

        createComponents(uiSkin);

        layoutComponents();
    }

    private void createComponents(Skin uiSkin) {
        openLevelButton = new TextButton("Open", uiSkin);
        saveLevelButton = new TextButton("Save", uiSkin);
    }

    private void layoutComponents() {
        this.align(Align.left);
        this.pad(2, 2, 2, 2);

        this.add(openLevelButton);
        this.add(saveLevelButton);
    }

}
