package org.vertexarmy.dsr.game;

import com.beust.jcommander.internal.Lists;
import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.vertexarmy.dsr.math.Polygon;

import java.io.Serializable;
import java.util.List;

/**
 * created by Alex
 * on 3/6/2015.
 */
@EqualsAndHashCode
@RequiredArgsConstructor
public class Level implements Serializable {

    public enum BackgroundLayerType {
        BACKGROUND(0),
        FAR(1),
        MIDDLE(2),
        NEAR(3),
        FOREGROUND(4);

        @Getter
        private final int zOrder;

        private BackgroundLayerType(int zOrder) {
            this.zOrder = zOrder;
        }
    }

    public static class BackgroundLayer implements Serializable {
        @Getter
        @Setter
        private String textureName;

        @Getter
        @Setter
        private float parallaxSpeedScale;

        @Getter
        @Setter
        private BackgroundLayerType type;

        public BackgroundLayer(String textureName, float parallaxSpeedScale) {
            this(textureName, parallaxSpeedScale, BackgroundLayerType.BACKGROUND);
        }

        public BackgroundLayer(String textureName, float parallaxSpeedScale, BackgroundLayerType type) {
            this.textureName = textureName;
            this.parallaxSpeedScale = parallaxSpeedScale;
            this.type = type;
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

    public BackgroundLayer getBackgroundLayerByType(BackgroundLayerType type) {
        for (BackgroundLayer layer : backgroundLayers) {
            if (layer.type == type) {
                return layer;
            }
        }

        return null;
    }

    public static Level createDefaultLevel() {
        return new Level(null, null, ImmutableList.of(
                new Polygon(new float[]{100, 100, 100, -100, -100, -100, -100, 100}),
                new Polygon(new float[]{200, 200, 200, 100, 100, 100, 100, 200})));
    }
}
