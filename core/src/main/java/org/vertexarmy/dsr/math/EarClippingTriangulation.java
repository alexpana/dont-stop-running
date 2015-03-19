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

    public static short[] triangulate(Polygon polygon) {
        short[] indices = new short[(polygon.getVertexCount() - 2) * 3];

        int indexPosition = 0;

        CircularDoublyLinkedList<PolygonVertex> vertexList = createVectorLinkedList(polygon);
        CircularDoublyLinkedList.Iterator<PolygonVertex> earIterator = vertexList.getIterator();

        while (vertexList.size() > 3) {
            if (isEar(earIterator)) {
                indices[indexPosition++] = earIterator.next().index;
                indices[indexPosition++] = earIterator.current().index;
                indices[indexPosition++] = earIterator.previous().index;

                earIterator.forward();
                vertexList.remove(earIterator.previous());
            } else {
                earIterator.forward();
            }
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

        int i = 0;
        short polygonIndex = 0;
        float[] vertex = new float[2];
        for (float element : polygon.getVertices()) {
            vertex[i++] = element;
            if (i == 2) {
                vertexList.add(new PolygonVertex(new Vector2(vertex[0], vertex[1]), polygonIndex));
                polygonIndex += 1;
                i = 0;
            }
        }
        return vertexList;
    }
}
