package org.vertexarmy.dsr.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * created by Alex
 * on 3/1/2015.
 */
@SuppressWarnings("UnusedDeclaration")
public class GraphicsUtils {
    @RequiredArgsConstructor
    public static class GlError {
        @Getter
        private final String symbolicName;
        @Getter
        private final String description;
    }

    private final static Map<Integer, GlError> errorMap;

    private final static Map<Color, Texture> colorTextureMap = new HashMap<>();

    static {
        errorMap = new HashMap<>();
        errorMap.put(GL20.GL_NO_ERROR, new GlError("GL_NO_ERROR", "No error has been recorded. The value of this symbolic constant is guaranteed to be 0."));
        errorMap.put(GL20.GL_INVALID_ENUM, new GlError("GL_INVALID_ENUM", "An unacceptable value is specified for an enumerated argument.The offending command is ignored and has no other side effect than to set the error flag."));
        errorMap.put(GL20.GL_INVALID_VALUE, new GlError("GL_INVALID_VALUE", "A numeric argument is out of range.The offending command is ignored and has no other side effect than to set the error flag."));
        errorMap.put(GL20.GL_INVALID_OPERATION, new GlError("GL_INVALID_OPERATION", "The specified operation is not allowed in the current state.The offending command is ignored and has no other side effect than to set the error flag."));
        errorMap.put(GL20.GL_INVALID_FRAMEBUFFER_OPERATION, new GlError("GL_INVALID_FRAMEBUFFER_OPERATION", "The framebuffer object is not complete.The offending command is ignored and has no other side effect than to set the error flag."));
        errorMap.put(GL20.GL_OUT_OF_MEMORY, new GlError("GL_INVALID_ENUM", "There is not enough memory left to execute the command.The state of the GL is undefined, except for the state of the error flags, after this error is recorded."));
        errorMap.put(0x503, new GlError("GL_STACK_UNDERFLOW", "An attempt has been made to perform an operation that would cause an internal stack to underflow."));
        errorMap.put(0x504, new GlError("GL_STACK_OVERFLOW", "An attempt has been made to perform an operation that would cause an internal stack to overflow."));
    }

    public static GlError convertError(int glErrorCode) {
        return errorMap.get(glErrorCode);
    }

    public static GlError glGetError() {
        return errorMap.get(Gdx.gl.glGetError());
    }

    public static Texture getColorTexture(Color color) {
        if (!colorTextureMap.containsKey(color)) {
            Texture texture = createColorTexture(color);
            colorTextureMap.put(color, texture);
        }

        return colorTextureMap.get(color);
    }

    private  ShaderProgram createDefaultShader() {
        String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
                + "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
                + "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
                + "uniform mat4 u_projTrans;\n" //
                + "varying vec4 v_color;\n" //
                + "varying vec2 v_texCoords;\n" //
                + "\n" //
                + "void main()\n" //
                + "{\n" //
                + "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
                + "   v_color.a = v_color.a * (255.0/254.0);\n" //
                + "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
                + "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
                + "}\n";

        String fragmentShader = "#ifdef GL_ES\n" //
                + "#define LOWP lowp\n" //
                + "precision mediump float;\n" //
                + "#else\n" //
                + "#define LOWP \n" //
                + "#endif\n" //
                + "varying LOWP vec4 v_color;\n" //
                + "varying vec2 v_texCoords;\n" //
                + "uniform sampler2D u_texture;\n" //
                + "void main()\n"//
                + "{\n" //
                + "  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n" //
                + "}";

        ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
        if (!shader.isCompiled())
            throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
        return shader;
    }

    private static Texture createColorTexture(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        return new Texture(pixmap);
    }
}
