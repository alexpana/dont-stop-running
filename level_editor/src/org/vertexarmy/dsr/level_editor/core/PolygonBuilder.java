package org.vertexarmy.dsr.level_editor.core;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import java.util.Arrays;
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
            Iterable<Float> vertexIterable = Iterables.transform(
                    Arrays.asList(((GenericAttr) node).getValue().split(" +|,")),
                    new Function<String, Float>() {
                        @Nullable
                        @Override
                        public Float apply(String input) {
                            return Float.parseFloat(input);
                        }
                    });
            return new Polygon(vertexIterable);
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

    private static float getFloat(SVGAnimatedLength svgAnimatedLength) {
        return svgAnimatedLength.getBaseVal().getValue();
    }
}
