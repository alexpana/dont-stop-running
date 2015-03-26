package org.vertexarmy.dsr.leveleditor.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * created by Alex
 * on 3/26/2015.
 */
public class IconRepository {
    private final static IconRepository INSTANCE = new IconRepository();

    private TextureAtlas atlas;

    public final static String VERTEX_ALIGN_HORIZONTALLY = "alignh";
    public final static String VERTEX_ALIGN_VERTICALLY = "alignv";
    public final static String FILE_OPEN = "file_open";
    public final static String FILE_SAVE = "file_save";
    public final static String COMPONENT_UP = "bg_up";
    public final static String COMPONENT_HOVER = "bg_over";
    public final static String COMPONENT_PRESSED = "bg_down";

    private IconRepository() {
    }

    public static IconRepository instance() {
        return INSTANCE;
    }

    public void initialize() {
        atlas = new TextureAtlas(Gdx.files.internal("ui/ui_icons.atlas"));
    }

    public TextureRegion getIcon(String iconName) {
        return atlas.findRegion(iconName);
    }
}
