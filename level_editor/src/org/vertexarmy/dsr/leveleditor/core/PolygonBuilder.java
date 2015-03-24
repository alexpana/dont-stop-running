package org.vertexarmy.dsr.leveleditor.core;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.batik.dom.GenericAttr;
import org.apache.batik.dom.svg.SVGOMRectElement;
import org.vertexarmy.dsr.math.Polygon;
import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGAnimatedLength;

/**
 * created by Alex
 * on 3/7/2015.
 */
public class PolygonBuilder {
    public static Polygon fromNode(Node node) {
        if (node instanceof GenericAttr) {
            String value = ((GenericAttr) node).getValue();
            List<Float> vertices = parsePathData(value);

            // flip Y coordinates
            for (int i = 1; i < vertices.size(); i += 2) {
                vertices.set(i, 1000 - vertices.get(i));
            }

            // reverse vertex order
            for (int i = 0; i < vertices.size() / 2; i += 2) {
                float x1 = vertices.get(i);
                float x2 = vertices.get(vertices.size() - 1 - (i + 1));
                float y1 = vertices.get(i + 1);
                float y2 = vertices.get(vertices.size() - 1 - i);
                vertices.set(i, x2);
                vertices.set(vertices.size() - 1 - (i + 1), x1);
                vertices.set(i + 1, y2);
                vertices.set(vertices.size() - 1 - i, y1);
            }
            return new Polygon(vertices);
        }

        if (node instanceof SVGOMRectElement) {
            SVGOMRectElement rect = (SVGOMRectElement) node;
            float[] vertices = new float[8];
            vertices[0] = getFloat(rect.getX());
            vertices[1] = getFloat(rect.getY());
            vertices[2] = getFloat(rect.getX()) + getFloat(rect.getWidth());
            vertices[3] = getFloat(rect.getY());
            vertices[4] = getFloat(rect.getX()) + getFloat(rect.getWidth());
            vertices[5] = getFloat(rect.getY()) + getFloat(rect.getHeight());
            vertices[6] = getFloat(rect.getX());
            vertices[7] = getFloat(rect.getY()) + getFloat(rect.getHeight());
            return new Polygon(vertices);
        }
        return null;
    }

    private static List<Float> parsePathData(String value) {
        // m - relative positions
        // M - absolute position

        String trimmedValue = value.substring(2, value.length() - 2);

        Iterable<Float> vertexIterable = Iterables.transform(
                Arrays.asList(trimmedValue.split(" +|,")),
                new Function<String, Float>() {
                    @Nullable
                    @Override
                    public Float apply(String input) {
                        return Float.parseFloat(input);
                    }
                });
        return Lists.newArrayList(vertexIterable);
    }

    private static float getFloat(SVGAnimatedLength svgAnimatedLength) {
        return svgAnimatedLength.getBaseVal().getValue();
    }
}
