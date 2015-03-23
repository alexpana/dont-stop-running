package org.vertexarmy.dsr.graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import java.util.HashMap;
import java.util.Map;
import org.vertexarmy.dsr.graphics.GraphicsUtils.GlError;

/**
 * created by Alex
 * on 2/28/2015.
 */
public class Renderer {
    private final PolygonSpriteBatch POLYGON_SPRITE_BATCH = new PolygonSpriteBatch();

    private final Map<Color, Texture> colorTextureMap = new HashMap<>();

    private State state;

    private ShaderProgram shaderProgram;

    private Camera camera;

    public Renderer(Camera camera) {
        this.camera = camera;
        shaderProgram = SpriteBatch.createDefaultShader();
    }

    public void draw(GraphicsPolygon polygon) {
        updateUniforms();

        Texture texture = getColorTexture(new Color(0.2f, 0.2f, 0.2f, 1.0f));
        texture.bind(0);

        Mesh mesh = polygon.createMesh();
        mesh.render(shaderProgram, GL20.GL_TRIANGLES);

        GlError error = GraphicsUtils.glGetError();
    }

    private void updateUniforms() {
        shaderProgram.setUniformMatrix("u_projTrans", camera.projection);
        shaderProgram.setUniformi("u_texture", 0);
    }

    private Texture getColorTexture(Color color) {
        if (!colorTextureMap.containsKey(color)) {
            Texture texture = createColorTexture(color);
            colorTextureMap.put(color, texture);
        }

        return colorTextureMap.get(color);
    }

    private Texture createColorTexture(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        return new Texture(pixmap);
    }

    static class State {
        Color color;
    }
}
