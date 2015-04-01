package org.vertexarmy.dsr.math;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * created by Alex
 * on 3/18/2015.
 */
@SuppressWarnings("unused")
public class Algorithms {
    public static boolean triangleContainsVertex(Vector2 vertex, Triangle triangle) {
        Vector2 v1v2 = new Vector2(triangle.v2).sub(triangle.v1);
        Vector2 v2v3 = new Vector2(triangle.v3).sub(triangle.v2);
        Vector2 v3v1 = new Vector2(triangle.v1).sub(triangle.v3);

        Vector2 v1x = new Vector2(vertex).sub(triangle.v1);
        Vector2 v2x = new Vector2(vertex).sub(triangle.v2);
        Vector2 v3x = new Vector2(vertex).sub(triangle.v3);

        int s1 = signum(v1v2.crs(v1x));
        int s2 = signum(v2v3.crs(v2x));
        int s3 = signum(v3v1.crs(v3x));

        return allEqual(s1, s2, s3);
    }

    public static boolean vertexOnTrianglePerimeter(Vector2 vertex, Triangle triangle) {
        Vector2 v1v2 = new Vector2(triangle.v2).sub(triangle.v1);
        Vector2 v2v3 = new Vector2(triangle.v3).sub(triangle.v2);
        Vector2 v3v1 = new Vector2(triangle.v1).sub(triangle.v3);

        Vector2 v1x = new Vector2(vertex).sub(triangle.v1);
        Vector2 v2x = new Vector2(vertex).sub(triangle.v2);
        Vector2 v3x = new Vector2(vertex).sub(triangle.v3);

        int s1 = signum(v1v2.crs(v1x));
        int s2 = signum(v2v3.crs(v2x));
        int s3 = signum(v3v1.crs(v3x));

        return s1 == 0 || s2 == 0 || s3 == 0;
    }

    public static int signum(float f) {
        if (f < 0) {
            return -1;
        }
        if (f > 0) {
            return 1;
        }
        return 0;
    }

    @SafeVarargs
    public static <E> boolean allEqual(E... elements) {
        if (elements.length < 2) {
            return true;
        }

        for (int i = 1; i < elements.length; ++i) {
            if (!elements[i].equals(elements[i - 1])) {
                return false;
            }
        }

        return true;
    }

    public static float min(float element, float... elements) {
        float min = element;

        for (float e : elements) {
            if (e < min) {
                min = e;
            }
        }

        return min;
    }

    public static float max(float element, float... elements) {
        float max = element;

        for (float e : elements) {
            if (e > max) {
                max = e;
            }
        }

        return max;
    }

    public static float clamp(float value, float min, float max) {
        return Math.min(max, Math.max(value, min));
    }

    public static Rectangle createRectangle(float rx1, float ry1, float rx2, float ry2) {
        return new Rectangle(Math.min(rx1, rx2), Math.min(ry1, ry2), Math.abs(rx1 - rx2), Math.abs(ry1 - ry2));
    }

    public static Rectangle createRectangle(Vector2 v1, Vector2 v2) {
        return createRectangle(v1.x, v1.y, v2.x, v2.y);
    }

    public static float distance(Vector2 a, Vector2 b) {
        final float deltaX = a.x - b.x;
        final float deltaY = a.y - b.y;
        return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public static float distanceSq(Vector2 a, Vector2 b) {
        final float deltaX = a.x - b.x;
        final float deltaY = a.y - b.y;
        return deltaX * deltaX + deltaY * deltaY;
    }

    public static float vertexToEdgeDistance(Vector2 edgeA, Vector2 edgeB, Vector2 vertex) {
        Vector2 projection = vertexToEdgeProjection(edgeA, edgeB, vertex);

        return (float) Math.sqrt(min(distanceSq(edgeA, vertex), distanceSq(edgeB, vertex), distanceSq(projection, vertex)));
    }

    public static float vertexToEdgeDistance(Edge edge, Vector2 vertex) {
        return vertexToEdgeDistance(edge.start, edge.end, vertex);
    }

    public static Vector2 vertexToEdgeProjection(Vector2 edgeA, Vector2 edgeB, Vector2 vertex) {
        Vector2 edgeVector = edgeA.cpy().sub(edgeB);

        Vector2 edgeVectorNormalized = edgeVector.cpy().nor();

        Vector2 vertexVector = vertex.cpy().sub(edgeB);

        return edgeVectorNormalized.scl(vertexVector.dot(edgeVectorNormalized)).add(edgeB);
    }

    public static Vector2 vertexToEdgeProjection(Edge edge, Vector2 vertex) {
        return vertexToEdgeProjection(edge.start, edge.end, vertex);
    }

    public static boolean segmentContainsVertex(Vector2 segmentA, Vector2 segmentB, Vector2 vertex) {
        float segmentLengthSquared = Algorithms.distanceSq(segmentA, segmentB);

        return Algorithms.distanceSq(segmentA, vertex) <= segmentLengthSquared && Algorithms.distanceSq(segmentB, vertex) <= segmentLengthSquared;
    }

    public static boolean polygonContainsVertex(Vector2 vertex, Polygon polygon) {
        short[] indices = EarClippingTriangulation.triangulate(polygon);
        Triangle triangle = new Triangle();

        for (int i = 0; i < indices.length / 3; ++i) {
            short i1 = indices[i * 3];
            short i2 = indices[i * 3 + 1];
            short i3 = indices[i * 3 + 2];

            triangle.set(polygon.getVertex(i1), polygon.getVertex(i2), polygon.getVertex(i3));
            if (Algorithms.triangleContainsVertex(vertex, triangle)) {
                return true;
            }
        }

        return false;
    }
}
