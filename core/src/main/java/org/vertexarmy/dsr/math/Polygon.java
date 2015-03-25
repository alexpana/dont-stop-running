package org.vertexarmy.dsr.math;

import com.badlogic.gdx.math.Vector2;
import com.beust.jcommander.internal.Lists;
import java.util.List;

/**
 * created by Alex
 * on 3/6/2015.
 */
public class Polygon {
    private final List<Vector2> vertexList = Lists.newArrayList();

    public Polygon(float[] vertices) {
        setVertices(vertices);
    }

    public Polygon(Iterable<Float> vertexIterable) {
        int i = 0;
        float[] vertex = new float[2];

        for (float element : vertexIterable) {
            vertex[i++] = element;
            if (i == 2) {
                vertexList.add(new Vector2(vertex[0], vertex[1]));
                i = 0;
            }
        }
    }

    public void setVertices(float[] vertices) {
        vertexList.clear();
        for (int i = 0; i < vertices.length / 2; ++i) {
            vertexList.add(new Vector2(vertices[i * 2], vertices[i * 2 + 1]));
        }
    }

    public void setVertices(List<Vector2> vertices) {
        vertexList.clear();
        vertexList.addAll(vertices);
    }

    public int getVertexCount() {
        return vertexList.size();
    }

    public void addVertex(float x, float y) {
        vertexList.add(new Vector2(x, y));
    }

    public void addVertex(int index, Vector2 position) {
        vertexList.add(index, position);
    }

    public void setVertex(int index, Vector2 position) {
        vertexList.set(index, position);
    }

    public void removeVertex(int index) {
        vertexList.remove(index);
    }

    public Vector2 getVertex(int index) {
        return vertexList.get(index);
    }

    public List<Vector2> getVertices() {
        return vertexList;
    }

    public float[] getVertexArray() {
        float[] vertexArray = new float[getVertexCount() * 2];
        int i = 0;
        for (Vector2 vertex : vertexList) {
            vertexArray[i * 2] = vertex.x;
            vertexArray[i * 2 + 1] = vertex.y;
            i += 1;
        }

        return vertexArray;
    }
}
