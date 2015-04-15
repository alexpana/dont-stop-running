package org.vertexarmy.dsr.game.level;

import com.beust.jcommander.internal.Lists;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.vertexarmy.dsr.math.Polygon;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode
public class Level implements Serializable {

    @Getter
    private final List<BackgroundLayer> backgroundLayers = Lists.newArrayList();

    @Getter
    @Setter
    private Polygon startArea;

    @Getter
    @Setter
    private Polygon endArea;

    @Getter
    private final List<TerrainPatch> terrainPatches = Lists.newArrayList();

    @Getter
    private final List<LevelSprite> levelSprites = Lists.newArrayList();

    public BackgroundLayer getBackgroundLayerByType(BackgroundLayer.Type type) {
        for (BackgroundLayer layer : backgroundLayers) {
            if (layer.getType() == type) {
                return layer;
            }
        }

        return null;
    }
}
