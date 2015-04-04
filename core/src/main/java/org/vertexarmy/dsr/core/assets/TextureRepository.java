package org.vertexarmy.dsr.core.assets;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.beust.jcommander.internal.Maps;
import com.google.common.collect.Lists;
import org.vertexarmy.dsr.core.Log;

import java.util.List;
import java.util.Map;

/**
 * Created by alex
 * on 30.03.2015.
 */
public class TextureRepository {
    private static final TextureRepository INSTANCE = new TextureRepository();

    private final Log log = Log.create();

    private final Map<String, TextureRegion> textureRegionMap = Maps.newHashMap();

    public static TextureRepository instance() {
        return INSTANCE;
    }

    private TextureRepository() {
    }

    public void initialize() {
        log.info("Initialized ok.");
    }

    public List<String> textureNames() {
        return Lists.newArrayList(textureRegionMap.keySet());
    }

    public TextureRegion getTexture(String name) {
        return textureRegionMap.get(name);
    }

    public void addTexture(String name, TextureRegion textureRegion) {
        log.debug("Adding texture \"" + name + "\"" + " with region " + regionToString(textureRegion));
        textureRegionMap.put(name, textureRegion);
    }

    public void loadTextureAtlas(FileHandle atlasFile) {
        addTextureAtlas(new TextureAtlas(atlasFile));
    }

    public void addTextureAtlas(TextureAtlas textureAtlas) {
        for (TextureAtlas.AtlasRegion region : textureAtlas.getRegions()) {
            addTexture(region.name, region);
        }
    }

    private String regionToString(TextureRegion textureRegion) {
        return "[" + textureRegion.getRegionX() + ", " + textureRegion.getRegionY() + ", " + textureRegion.getRegionWidth() + ", " + textureRegion.getRegionHeight() + "]";
    }
}
