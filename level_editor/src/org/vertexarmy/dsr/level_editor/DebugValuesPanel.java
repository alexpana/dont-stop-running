package org.vertexarmy.dsr.level_editor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.vertexarmy.dsr.graphics.GraphicsUtils;

/**
 * created by Alex
 * on 3/20/2015.
 */
class DebugValuesPanel extends Table {

    public DebugValuesPanel(final Skin skin) {
        DebugValues.instance().addListener(new DebugValues.Listener() {
            @Override
            public void valuesChanged() {
                clear();
                for (String key : DebugValues.instance().keySet()) {
                    add(new Label(key + ":", skin)).right().pad(0, 0, 0, 4);
                    add(new Label(DebugValues.instance().getValue(key), skin)).left().row();
                }
            }
        });
        this.setBackground(new TextureRegionDrawable(new TextureRegion(GraphicsUtils.getColorTexture(new Color(0x00000040)))));
        this.pad(10, 10, 10, 10);
    }
}
