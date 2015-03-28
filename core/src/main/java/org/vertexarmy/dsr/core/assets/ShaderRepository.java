package org.vertexarmy.dsr.core.assets;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.beust.jcommander.internal.Maps;
import java.util.Map;
import org.vertexarmy.dsr.core.Log;

/**
 * created by Alex
 * on 3/18/2015.
 */
@SuppressWarnings("UnusedDeclaration")
public class ShaderRepository {
    private static final ShaderRepository INSTANCE = new ShaderRepository();

    private final Map<ShaderInstance, ShaderProgram> shaderMap = Maps.newHashMap();

    private final Log log = Log.create();

    public enum ShaderInstance {
        DEFAULT_POS_COL_TEX_PROJ
    }

    private ShaderRepository() {
    }

    public static ShaderRepository instance() {
        return INSTANCE;
    }

    /**
     * Builds all the available shaders. This method must be called after initializing libgdx, when a valid
     * opengl context is available.
     */
    public void initialize() {
        shaderMap.put(ShaderInstance.DEFAULT_POS_COL_TEX_PROJ, createDefaultPosColTexProjShader());
        log.info("Initialized ok.");
    }

    public ShaderProgram getShader(ShaderInstance shaderShaderInstance) {
        return shaderMap.get(shaderShaderInstance);
    }

    private ShaderProgram createDefaultPosColTexProjShader() {
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
            log.error("Error compiling shader DefaultPosColTexProjShader: " + shader.getLog());
        return shader;
    }
}
