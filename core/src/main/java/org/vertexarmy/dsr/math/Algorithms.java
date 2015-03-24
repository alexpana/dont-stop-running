package org.vertexarmy.dsr.math;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * created by Alex
 * on 3/18/2015.
 */
public class Algorithms {
    public static boolean vertexInsideTriangle(Vector2 vertex, Triangle triangle) {
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

    public static float clamp(float value, float min, float max) {
        return Math.min(max, Math.max(value, min));
    }

    public static Rectangle createRectangle(float rx1, float ry1, float rx2, float ry2) {
        return new Rectangle(Math.min(rx1, rx2), Math.min(ry1, ry2), Math.abs(rx1 - rx2), Math.abs(ry1 - ry2));
    }

    public static Rectangle createRectangle(Vector2 v1, Vector2 v2) {
        return createRectangle(v1.x, v1.y, v2.x, v2.y);
    }

    @SuppressWarnings("UnusedDeclaration")
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
}
