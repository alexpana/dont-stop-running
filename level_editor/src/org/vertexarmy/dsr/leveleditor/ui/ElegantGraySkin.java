package org.vertexarmy.dsr.leveleditor.ui;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by alex
 * on 30.03.2015.
 */
public class ElegantGraySkin {
    public static void install(Skin skin) {
        installScrollPaneStyle(skin);

    }

    private static void installScrollPaneStyle(Skin skin) {
        ScrollPane.ScrollPaneStyle scrollPaneStyle = skin.get(ScrollPane.ScrollPaneStyle.class);
        scrollPaneStyle.vScrollKnob.setMinWidth(7);
        scrollPaneStyle.vScroll.setMinWidth(7);
    }
}
