package org.vertexarmy.dsr.level_editor;

import com.badlogic.gdx.Gdx;
import com.google.common.collect.Lists;
import java.io.IOException;
import org.vertexarmy.dsr.game.Level;
import org.vertexarmy.dsr.level_editor.core.PolygonBuilder;
import org.vertexarmy.dsr.level_editor.core.SVGParser;
import org.vertexarmy.dsr.math.Polygon;
import org.w3c.dom.Node;

/**
 * created by Alex
 * on 3/7/2015.
 */
public class LevelLoader {
    private String filename;

    public LevelLoader(String filename) {
        this.filename = filename;
    }

    public Level load() throws CorruptLevel, IOException {
        Gdx.app.log("LevelLoader", "Loading level " + filename);

        SVGParser parser = SVGParser.fromFilename(filename);

        Node terrain = parser.xpath("//*[@id='terrain']/@d").first();
        if (terrain == null) {
            terrain = parser.xpath("//*[@id='terrain']/@path").first();
        }
        Node startPosition = parser.xpath("//*[@id='startPosition']/*").first();
        Node endPosition = parser.xpath("//*[@id='endPosition']/*").first();

        if (terrain == null) {
            throw new CorruptLevel("Could not find layer 'terrain' in " + filename);
        }

        if (startPosition == null) {
            Gdx.app.error("LevelLoader", "Could not find layer 'startPosition' in " + filename);
        }

        if (endPosition == null) {
            Gdx.app.error("LevelLoader", "Could not find layer 'endPosition' in " + filename);
        }

        Polygon terrainPoly = PolygonBuilder.fromNode(terrain);
        Polygon startPolygon = PolygonBuilder.fromNode(startPosition);
        Polygon endPolygon = PolygonBuilder.fromNode(endPosition);

        return new Level(startPolygon, endPolygon, Lists.newArrayList(terrainPoly));
    }

    public static class CorruptLevel extends Exception {
        public CorruptLevel(String message) {
            super(message);
        }
    }
}
