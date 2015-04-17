package org.vertexarmy.dsr.math;

import com.badlogic.gdx.math.Vector2;
import com.beust.jcommander.internal.Lists;
import java.io.Serializable;
import java.util.List;
import lombok.EqualsAndHashCode;
import org.vertexarmy.dsr.core.DeepCopyable;

/**
 * created by Alex
 * on 3/6/2015.
 */
@EqualsAndHashCode
public class Polygon implements Serializable, DeepCopyable {

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

    public Polygon(List<Vector2> vertexList) {
        this.vertexList.addAll(vertexList);
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

    public boolean containsVertex(Vector2 vertex) {
        short[] indices = EarClippingTriangulation.triangulate(this);
        Triangle triangle = new Triangle();

        for (int i = 0; i < indices.length / 3; ++i) {
            short i1 = indices[i * 3];
            short i2 = indices[i * 3 + 1];
            short i3 = indices[i * 3 + 2];

            triangle.set(vertexList.get(i1), vertexList.get(i2), vertexList.get(i3));
            if (Algorithms.triangleContainsVertex(vertex, triangle)) {
                return true;
            }
        }

        return false;
    }

    public List<Edge> getEdgeList() {
        List<Edge> edges = Lists.newArrayList();
        for (int i = 1; i < vertexList.size(); ++i) {
            edges.add(new Edge(vertexList.get(i - 1), vertexList.get(i)));
        }
        edges.add(new Edge(vertexList.get(vertexList.size() - 1), vertexList.get(0)));
        return edges;
    }

    public void scale(Vector2 scale) {
        for (Vector2 vertex : vertexList) {
            vertex.scl(scale);
        }
    }

    public void rotate(float degrees) {
        for (Vector2 vertex : vertexList) {
            vertex.rotate(degrees);
        }
    }

    public void translate(Vector2 translation) {
        for (Vector2 vertex : vertexList) {
            vertex.add(translation);
        }
    }

    @Override
    public Object copy() {
        List<Vector2> newVertexList = Lists.newArrayList();
        for (Vector2 vertex : vertexList) {
            newVertexList.add(vertex.cpy());
        }
        return new Polygon(newVertexList);
    }

    public float[] toFloatArray() {
        float[] array = new float[vertexList.size() * 2];
        int i = 0;
        for (Vector2 vertex : vertexList) {
            array[i * 2] = vertex.x;
            array[i * 2 + 1] = vertex.y;
            i += 1;
        }
        return array;
    }
}
