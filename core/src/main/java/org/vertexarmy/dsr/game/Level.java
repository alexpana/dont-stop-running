package org.vertexarmy.dsr.game;

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

    public static class BackgroundLayer implements Serializable {
        @Getter
        @Setter
        private String textureName;

        @Getter
        @Setter
        private float parallaxSpeedScale;

        public BackgroundLayer(String textureName, float parallaxSpeedScale) {
            this.textureName = textureName;
            this.parallaxSpeedScale = parallaxSpeedScale;
        }
    }

    @Getter
    private final Polygon startArea;

    @Getter
    private final Polygon endArea;

    @Getter
    private final List<Polygon> terrainPatches;
}
