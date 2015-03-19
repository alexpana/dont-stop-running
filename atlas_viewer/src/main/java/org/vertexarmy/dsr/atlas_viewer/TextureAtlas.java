package org.vertexarmy.dsr.atlas_viewer;

import com.beust.jcommander.internal.Maps;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * created by Alex
 * on 3/14/2015.
 */
@RequiredArgsConstructor
public class TextureAtlas {

    @RequiredArgsConstructor
    public static class Region {
        public final float x;
        public final float y;
        public final float w;
        public final float h;
    }

    @Getter
    private final Map<String, Region> regionMap = Maps.newHashMap();

    @Getter
    private final String texturePath;

    private BufferedImage cachedImage;

    public BufferedImage getImage() {
        if (cachedImage == null) {
            createCacheImage();
        }
        return cachedImage;
    }

    public void addRegion(String name, Region region) {
        regionMap.put(name, region);
    }

    private void createCacheImage() {
        try {
            cachedImage = ImageIO.read(new File(texturePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
