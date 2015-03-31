package org.vertexarmy.dsr.game;

import com.beust.jcommander.internal.Lists;
import com.google.common.collect.ImmutableList;
import java.io.Serializable;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.vertexarmy.dsr.math.Polygon;

/**
 * created by Alex
 * on 3/6/2015.
 */
@EqualsAndHashCode
@RequiredArgsConstructor
public class Level implements Serializable {

    public static class BackgroundLayer implements Serializable {
        @Getter
        @Setter
        private String textureName;

        @Getter
        @Setter
        private float parallaxSpeedScale;

        @Getter
        @Setter
        private int zOrder;

        public BackgroundLayer(String textureName, float parallaxSpeedScale) {
            this(textureName, parallaxSpeedScale, 0);
        }

        public BackgroundLayer(String textureName, float parallaxSpeedScale, int zOrder) {
            this.textureName = textureName;
            this.parallaxSpeedScale = parallaxSpeedScale;
            this.zOrder = zOrder;
        }
    }

    @Getter
    private final List<BackgroundLayer> backgroundLayers = Lists.newArrayList();

    @Getter
    private final Polygon startArea;

    @Getter
    private final Polygon endArea;

    @Getter
    private final List<Polygon> terrainPatches;

    public static Level createDefaultLevel() {
        return new Level(null, null, ImmutableList.of(new Polygon(new float[]{100, 100, 100, -100, -100, -100, -100, 100})));
    }
}
