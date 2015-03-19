package org.vertexarmy.dsr.math;

import com.badlogic.gdx.math.Vector2;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * created by Alex
 * on 3/18/2015.
 */
public class TestAlgorithms {
    @Test
    public void testVertexInsideTriangle() {
        Vector2 v1 = new Vector2(0, 0);
        Vector2 v2 = new Vector2(4, 0);
        Vector2 v3 = new Vector2(0, 3);
        Vector2 v4 = new Vector2(2, 3);
        Vector2 v5 = new Vector2(0, 6);
        Vector2 v6 = new Vector2(9, 6);

        Vector2 x1 = new Vector2(2, -2);
        Vector2 x2 = new Vector2(2, 0);
        Vector2 x3 = new Vector2(-1, 1);
        Vector2 x4 = new Vector2(2, 1);
        Vector2 x5 = new Vector2(3, 1.5f);
        Vector2 x6 = new Vector2(1, 2);
        Vector2 x7 = new Vector2(5, 2);
        Vector2 x8 = new Vector2(2, 5);

        Triangle t1 = new Triangle(v1, v2, v4);
        Triangle t2 = new Triangle(v1, v3, v4);
        Triangle t3 = new Triangle(v3, v4, v6);
        Triangle t4 = new Triangle(v5, v4, v2);

        Assert.assertTrue(Algorithms.vertexInsideTriangle(x4, t1));

        Assert.assertTrue(Algorithms.vertexOnTrianglePerimeter(x2, t1));
        Assert.assertTrue(Algorithms.vertexOnTrianglePerimeter(x5, t1));

        Assert.assertFalse(Algorithms.vertexInsideTriangle(x1, t1));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x3, t1));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x7, t1));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x8, t1));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x6, t1));

        Assert.assertTrue(Algorithms.vertexInsideTriangle(x6, t2));

        Assert.assertFalse(Algorithms.vertexInsideTriangle(x1, t2));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x2, t2));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x3, t2));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x4, t2));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x5, t2));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x7, t2));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x8, t2));

        Assert.assertFalse(Algorithms.vertexInsideTriangle(x1, t3));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x2, t3));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x3, t3));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x4, t3));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x5, t3));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x6, t3));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x7, t3));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x8, t3));

        Assert.assertTrue(Algorithms.vertexOnTrianglePerimeter(x5, t4));

        Assert.assertFalse(Algorithms.vertexInsideTriangle(x1, t4));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x2, t4));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x3, t4));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x4, t4));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x6, t4));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x7, t4));
        Assert.assertFalse(Algorithms.vertexInsideTriangle(x8, t4));
    }
}
