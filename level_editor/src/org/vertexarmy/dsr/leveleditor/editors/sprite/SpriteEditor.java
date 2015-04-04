package org.vertexarmy.dsr.leveleditor.editors.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import org.vertexarmy.dsr.core.assets.TextureRepository;
import org.vertexarmy.dsr.core.component.ComponentType;
import org.vertexarmy.dsr.core.component.Node;
import org.vertexarmy.dsr.game.level.LevelSprite;

/**
 * created by Alex
 * on 04-Apr-2015.
 */
public class SpriteEditor {
    @Getter
    private final Node node = new Node();

    @Getter
    private LevelSprite levelSprite;

    @Getter
    private final EditHandler scaleHandler = new EditHandler();

    @Getter
    private final EditHandler rotateHandler = new EditHandler();

    public SpriteEditor() {
        node.addComponent(ComponentType.INPUT, new SpriteEditorInputComponent(this));
        node.addComponent(ComponentType.RENDER, new SpriteEditorRenderComponent(this));
    }

    public boolean bindToSprite(LevelSprite levelSprite) {
        if (this.levelSprite != levelSprite) {
            this.levelSprite = levelSprite;
            return true;
        } else {
            return false;
        }
    }

    public boolean unbindFromSprite() {
        if (levelSprite != null) {
            this.levelSprite = null;
            return true;
        } else {
            return false;
        }
    }

    public boolean isBoundToSprite() {
        return this.levelSprite != null;
    }

    public Vector2 getSpriteBottomLeftCorner() {
        return levelSprite.getPosition();
    }

    public Vector2 getSpriteTopRightCorner() {
        TextureRegion region = TextureRepository.instance().getTexture(levelSprite.getTextureName());
        float width = region.getRegionWidth() * levelSprite.getScale().x;
        float height = region.getRegionHeight() * levelSprite.getScale().y;
        return getSpriteBottomLeftCorner().cpy().add(width, height);
    }
}
