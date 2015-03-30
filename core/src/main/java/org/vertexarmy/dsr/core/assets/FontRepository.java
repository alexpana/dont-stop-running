package org.vertexarmy.dsr.core.assets;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.beust.jcommander.internal.Maps;
import java.util.Map;
import org.vertexarmy.dsr.core.Log;

/**
 * created by Alex
 * on 3/28/2015.
 */
public class FontRepository {
    private static final FontRepository INSTANCE = new FontRepository();

    private final Map<String, BitmapFont> fontMap = Maps.newHashMap();

    private final Log log = Log.create();

    private FontRepository() {
    }

    public static FontRepository instance() {
        return INSTANCE;
    }

    public void initialize() {
        log.info("Initialized ok.");
    }

    public boolean loadFont(String fontName, FileHandle fontFile) {
        try {
            fontMap.put(fontName, new BitmapFont(fontFile));
            log.info("Loaded font " + fontName);
            return true;
        } catch (Exception e) {
            log.error("Failed to load font " + fontName);
            return false;
        }
    }

    public BitmapFont getFont(String fontName) {
        return fontMap.get(fontName);
    }
}
