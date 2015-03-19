package org.vertexarmy.dsr.game;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.vertexarmy.dsr.math.Polygon;

/**
 * created by Alex
 * on 3/6/2015.
 */
@RequiredArgsConstructor
public class Level {

    @Getter
    private final Polygon startArea;

    @Getter
    private final Polygon endArea;

    @Getter
    private final List<Polygon> terrainPatches;
}
