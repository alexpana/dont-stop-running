package org.vertexarmy.dsr.leveleditor.levelrenderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.beust.jcommander.internal.Lists;
import org.vertexarmy.dsr.core.assets.TextureRepository;
import org.vertexarmy.dsr.core.systems.RenderSystem;
import org.vertexarmy.dsr.game.level.Level;
import org.vertexarmy.dsr.game.level.TerrainPatch;
import org.vertexarmy.dsr.graphics.GraphicsUtils;
import org.vertexarmy.dsr.graphics.TextureOverlay;
import org.vertexarmy.dsr.math.EarClippingTriangulation;

import java.util.List;

/**
 * Created by alex
 * on 01.04.2015.
 */
public class TerrainRenderer {
    private Level level;

    private final List<PolygonSprite> polygonSprites = Lists.newArrayList();

    public void setLevel(Level level) {
        this.level = level;
        reloadLevel();
    }

    public void render() {
        PolygonSpriteBatch polygonSpriteBatch = RenderSystem.instance().getPolygonSpriteBatch();
        polygonSpriteBatch.begin();
        for (PolygonSprite polygonSprite : polygonSprites) {
            polygonSprite.draw(polygonSpriteBatch);
        }
        polygonSpriteBatch.end();

    }

    public void reloadLevel() {
        polygonSprites.clear();
        for (TerrainPatch terrainPatch : level.getTerrainPatches()) {
            PolygonSprite terrainPolygonSprite = spriteFromTerrainPatch(terrainPatch);
            polygonSprites.add(terrainPolygonSprite);
        }
    }

    public PolygonSprite spriteFromTerrainPatch(TerrainPatch terrainPatch) {
        short[] indices = EarClippingTriangulation.triangulate(terrainPatch.getShape());

        PolygonRegion polygonRegion = new PolygonRegion(
                textureRegionFromTextureOverlay(terrainPatch.getTextureOverlay()),
                terrainPatch.getShape().getVertexArray(),
                indices);

        PolygonSprite sprite = new PolygonSprite(polygonRegion);
        float[] vertices = sprite.getVertices();

        for (int i = 0; i < sprite.getVertices().length / 5; ++i) {
            vertices[i * 5 + 3] *= terrainPatch.getTextureOverlay().getTextureScale().x;
            vertices[i * 5 + 4] *= terrainPatch.getTextureOverlay().getTextureScale().y;
        }
        return sprite;
    }

    private TextureRegion textureRegionFromTextureOverlay(TextureOverlay textureOverlay) {
        String textureName = textureOverlay.getTextureName();
        if (textureName == null) {
            return new TextureRegion(GraphicsUtils.getColorTexture(Color.BLACK), 1, 1);
        } else {
            Texture overlayTexture = TextureRepository.instance().getTexture(textureName).getTexture();
            overlayTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

            float uOffset = textureOverlay.getTextureOffset().x;
            float vOffset = textureOverlay.getTextureOffset().y;

            return new TextureRegion(overlayTexture, uOffset, vOffset, uOffset + 1, vOffset + 1);
        }
    }
}
