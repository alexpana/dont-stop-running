package org.vertexarmy.dsr.math;

import lombok.Getter;

/**
 * created by Alex
 * on 3/6/2015.
 */
public class Polygon {
    private static final int MAX_VERTEX_COUNT = 64;

    @Getter
    private float[] vertices;

    private int size = 0;

    public Polygon() {
        this(MAX_VERTEX_COUNT * 2);
    }

    public Polygon(int vertexCount) {
        vertices = new float[vertexCount];
        size = 0;
    }

    public Polygon(float[] vertices) {
        this.vertices = vertices;
        size = vertices.length;
    }

    public Polygon(Iterable<Float> vertexIterable) {
        int i = 0;
        float[] vertex = new float[2];

        this.size = 0;
        for (float element : vertexIterable) {
            size += 1;
        }

        this.vertices = new float[this.size];
        this.size = 0;

        for (float element : vertexIterable) {
            vertex[i++] = element;
            if (i == 2) {
                addVertex(vertex[0], vertex[1]);
                i = 0;
            }
        }
    }

    public void setVertices(float[] vertices) {
        this.vertices = vertices;
        size = vertices.length;
    }

    public int getVertexCount() {
        return size / 2;
    }

    public void addVertex(float x, float y) {
        if (size == vertices.length) {
            return;
        }
        vertices[size] = x;
        vertices[size + 1] = y;
        size += 2;
    }
}
