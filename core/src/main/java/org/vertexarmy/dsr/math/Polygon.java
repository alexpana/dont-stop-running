package org.vertexarmy.dsr.math;

import com.badlogic.gdx.math.Vector2;
import com.beust.jcommander.internal.Lists;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * created by Alex
 * on 3/6/2015.
 */
@EqualsAndHashCode
public class Polygon implements Serializable {
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
        if (index == vertexList.size()) {
            vertexList.add(position);
        } else {
            vertexList.add(index, position);
        }
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

    public List<Edge> getEdgeList() {
        List<Edge> edges = Lists.newArrayList();
        for (int i = 1; i < vertexList.size(); ++i) {
            edges.add(new Edge(vertexList.get(i - 1), vertexList.get(i)));
        }
        edges.add(new Edge(vertexList.get(vertexList.size() - 1), vertexList.get(0)));
        return edges;
    }
}
