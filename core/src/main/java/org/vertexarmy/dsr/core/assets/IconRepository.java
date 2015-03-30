package org.vertexarmy.dsr.core.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.vertexarmy.dsr.core.Log;

/**
 * created by Alex
 * on 3/26/2015.
 */
public class IconRepository {
    private static final IconRepository INSTANCE = new IconRepository();

    private final Log log = Log.create();

    private TextureAtlas atlas;

    private IconRepository() {
    }

    public static IconRepository instance() {
        return INSTANCE;
    }

    public void initialize() {
        atlas = new TextureAtlas(Gdx.files.internal("ui/ui_icons.atlas"));
        log.info("Initialized ok.");
    }

    public TextureRegion getIcon(String iconName) {
        return atlas.findRegion(iconName);
    }
}
