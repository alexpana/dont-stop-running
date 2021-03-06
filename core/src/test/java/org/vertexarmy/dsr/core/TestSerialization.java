package org.vertexarmy.dsr.core;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.vertexarmy.dsr.game.level.Level;
import org.vertexarmy.dsr.game.level.TerrainPatch;
import org.vertexarmy.dsr.math.Polygon;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by alex
 * on 27.03.2015.
 */
public class TestSerialization {

    @Test
    public void testLevelSerialization() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();

        Level originalLevel = createLevel();

        Serialization.serialize(byteOutputStream, originalLevel);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteOutputStream.toByteArray());

        Level deserializedLevel = Serialization.deserialize(inputStream, Level.class);

        Assert.assertEquals(originalLevel, deserializedLevel);
    }

    private Level createLevel() {
        Polygon startAreaPolygon = new Polygon(new float[]{0, 0, 1, 1, 2, 2, 3, 3});
        Polygon endAreaPolygon = new Polygon(new float[]{0, 0, 1, 1, 2, 2});
        Polygon terrainPolygon = new Polygon(new float[]{10, 20, 30, 40, 50, 60, 70, 80, 90, 100});
        Level level = new Level();
        level.getTerrainPatches().add(new TerrainPatch(terrainPolygon));
        level.setStartArea(startAreaPolygon);
        level.setEndArea(endAreaPolygon);

        return level;
    }
}
