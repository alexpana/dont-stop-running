package org.vertexarmy.dsr.game.level;

import com.badlogic.gdx.math.Vector2;
import com.beust.jcommander.internal.Lists;
import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.vertexarmy.dsr.math.Polygon;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode
@RequiredArgsConstructor
public class Level implements Serializable {

    @Getter
    private final List<BackgroundLayer> backgroundLayers = Lists.newArrayList();

    @Getter
    private final Polygon startArea;

    @Getter
    private final Polygon endArea;

    @Getter
    private final List<Polygon> terrainPatches;

    @Getter
    private final List<LevelSprite> levelSprites;

    public BackgroundLayer getBackgroundLayerByType(BackgroundLayer.Type type) {
        for (BackgroundLayer layer : backgroundLayers) {
            if (layer.getType() == type) {
                return layer;
            }
        }

        return null;
    }

    public static Level createDefaultLevel() {
        return new Level(null, null, ImmutableList.of(
                new Polygon(new float[]{100, 100, 100, -100, -100, -100, -100, 100}),
                new Polygon(new float[]{200, 200, 200, 100, 100, 100, 100, 200})),
                ImmutableList.of(
                        new LevelSprite("dirt", Vector2.Zero, 0, new Vector2(1, 1), 0, true),
                        new LevelSprite("saw", new Vector2(70, 70), 0, new Vector2(1, 1), 0, false),
                        new LevelSprite("saw", new Vector2(400, 400), 0, new Vector2(1, 1), 0, false)
                ));
    }
}
