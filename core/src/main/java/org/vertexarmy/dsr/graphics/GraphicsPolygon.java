package org.vertexarmy.dsr.graphics;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import org.vertexarmy.dsr.math.Polygon;

/**
 * created by Alex
 * on 2/28/2015.
 */
public class GraphicsPolygon {
    private static final int MAX_VERTICES = 2048;
    private static final int FLOATS_PER_VERTEX = 8;

    private float vertices[] = new float[MAX_VERTICES];
    private int vertexCount = 0;

    private short indices[] = new short[MAX_VERTICES * 3];
    private int indexCount = 0;

    public GraphicsPolygon(Polygon polygon) {
        for (int i = 0; i < polygon.getVertexCount(); ++i) {
            addVertexP(polygon.getVertices()[2 * i], polygon.getVertices()[2 * i + 1]);
        }
    }

    public void addVertexP(float positionX, float positionY) {
        addVertexPCT(positionX, positionY, 1, 1, 1, 1, 0, 0);
    }

    public void addVertexPC(float positionX, float positionY, float colorRed, float colorBlue, float colorAlpha, float colorGreen) {
        addVertexPCT(positionX, positionY, colorRed, colorBlue, colorGreen, colorAlpha, 0, 0);
    }

    public void addVertexPT(float positionX, float positionY, float textureU, float textureV) {
        addVertexPCT(positionX, positionY, 1, 1, 1, 1, textureU, textureV);
    }

    public void addVertexPCT(float positionX, float positionY, float colorRed, float colorBlue, float colorGreen, float colorAlpha, float textureU, float textureV) {
        vertices[vertexCount * 8] = positionX;
        vertices[vertexCount * 8 + 1] = positionY;
        vertices[vertexCount * 8 + 2] = colorRed;
        vertices[vertexCount * 8 + 3] = colorGreen;
        vertices[vertexCount * 8 + 4] = colorBlue;
        vertices[vertexCount * 8 + 5] = colorAlpha;
        vertices[vertexCount * 8 + 6] = textureU;
        vertices[vertexCount * 8 + 7] = textureV;
        vertexCount += 1;

        if (vertexCount > 2) {
            indices[indexCount] = (short) (vertexCount - 1);
            indices[indexCount + 1] = (short) (vertexCount - 2);
            indices[indexCount + 2] = 0;
            indexCount += 3;
        }
    }

    public void clear() {
        vertexCount = 0;
    }

    public Mesh createMesh() {
        Mesh mesh = new Mesh(Mesh.VertexDataType.VertexArray, false, 1024, 1024 * 3,
                new VertexAttribute(VertexAttributes.Usage.Position, 2, ShaderProgram.POSITION_ATTRIBUTE),
                new VertexAttribute(VertexAttributes.Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE),
                new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE + "0"));

        mesh.setVertices(vertices, 0, vertexCount * FLOATS_PER_VERTEX);
        mesh.setIndices(indices, 0, indexCount);
        return mesh;
    }
}
