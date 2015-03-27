package org.vertexarmy.dsr.game;

import java.io.Serializable;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.vertexarmy.dsr.math.Polygon;

/**
 * created by Alex
 * on 3/6/2015.
 */
@EqualsAndHashCode
@RequiredArgsConstructor
public class Level implements Serializable {

    @Getter
    private final Polygon startArea;

    @Getter
    private final Polygon endArea;

    @Getter
    private final List<Polygon> terrainPatches;
}
