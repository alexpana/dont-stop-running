package org.vertexarmy.dsr.math;

import com.badlogic.gdx.math.Vector2;
import lombok.RequiredArgsConstructor;
import org.vertexarmy.dsr.collection.CircularDoublyLinkedList;

/**
 * created by Alex
 * on 3/8/2015.
 */
public class EarClippingTriangulation {

    @RequiredArgsConstructor
    private static class PolygonVertex {
        public final Vector2 position;
        public final short index;
    }

    @SuppressWarnings("UnusedAssignment")
    public static short[] triangulate(Polygon polygon) {
        short[] indices = new short[(polygon.getVertexCount() - 2) * 3];

        int indexPosition = 0;

        CircularDoublyLinkedList<PolygonVertex> vertexList = createVectorLinkedList(polygon);
        CircularDoublyLinkedList.Iterator<PolygonVertex> earIterator = vertexList.getIterator();

        int iterationCount = 0;
        while (vertexList.size() > 3 && iterationCount < vertexList.size()) {
            if (isEar(earIterator)) {
                indices[indexPosition++] = earIterator.next().index;
                indices[indexPosition++] = earIterator.current().index;
                indices[indexPosition++] = earIterator.previous().index;

                earIterator.forward();
                vertexList.remove(earIterator.previous());
                iterationCount = 0;
            } else {
                earIterator.forward();
            }
            iterationCount += 1;
        }

        indices[indexPosition++] = earIterator.next().index;
        indices[indexPosition++] = earIterator.current().index;
        indices[indexPosition++] = earIterator.previous().index;

        return indices;
    }

    private static boolean isEar(CircularDoublyLinkedList.Iterator<PolygonVertex> vertex) {
        PolygonVertex previous = vertex.previous();
        PolygonVertex current = vertex.current();
        PolygonVertex next = vertex.next();

        // is convex
        Vector2 v1 = new Vector2(previous.position).sub(current.position);
        Vector2 v2 = new Vector2(previous.position).sub(next.position);

        if (v1.crs(v2) >= 0) {
            return false;
        }

        Triangle triangle = new Triangle(previous.position, current.position, next.position);

        CircularDoublyLinkedList.Iterator<PolygonVertex> it = vertex.duplicate();
        it.next();

        while (!it.current().equals(vertex.previous())) {
            if (Algorithms.vertexInsideTriangle(it.current().position, triangle)) {
                return false;
            } else {
                it.forward();
            }
        }

        return true;
    }

    private static CircularDoublyLinkedList<PolygonVertex> createVectorLinkedList(Polygon polygon) {
        CircularDoublyLinkedList<PolygonVertex> vertexList = new CircularDoublyLinkedList<>();

        short polygonIndex = 0;
        for (Vector2 vertex : polygon.getVertices()) {
            vertexList.add(new PolygonVertex(vertex.cpy(), polygonIndex));
            polygonIndex += 1;
        }
        return vertexList;
    }
}
