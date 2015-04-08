package org.vertexarmy.dsr.leveleditor.editors.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.Setter;
import org.vertexarmy.dsr.core.Root;
import org.vertexarmy.dsr.core.assets.TextureRepository;
import org.vertexarmy.dsr.core.component.ComponentType;
import org.vertexarmy.dsr.core.component.Node;
import org.vertexarmy.dsr.game.level.LevelSprite;
import org.vertexarmy.dsr.leveleditor.ui.genericeditor.GenericEditor;

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

    @Getter
    private GenericEditor editDialog;

    @Getter
    @Setter
    private SpriteEditorListener listener;

    public SpriteEditor(Root root) {
        node.addComponent(ComponentType.INPUT, new SpriteEditorInputComponent(this));
        node.addComponent(ComponentType.RENDER, new SpriteEditorRenderComponent(this));

        editDialog = new GenericEditor(root.getUiNode().getStage(), "Edit sprite", root.getUiNode().getUiSkin(), LevelSprite.class);
        editDialog.setSize(235, 200);
    }

    public boolean bindToSprite(LevelSprite levelSprite) {
        if (this.levelSprite != levelSprite) {
            this.levelSprite = levelSprite;
            editDialog.bindToObject(levelSprite);
            return true;
        } else {
            return false;
        }
    }

    public boolean unbindFromSprite() {
        if (levelSprite != null) {
            this.levelSprite = null;
            editDialog.bindToObject(null);
            return true;
        } else {
            return false;
        }
    }

    public boolean isBoundToSprite() {
        return this.levelSprite != null;
    }

    void deleteSprite() {
        if (isBoundToSprite()) {
            notifyDeleteSpriteRequested();
            unbindFromSprite();
        }
    }

    private void notifyDeleteSpriteRequested() {
        if (listener != null) {
            listener.deleteSpriteRequested();
        }
    }

    Vector2 getSpriteBottomLeftCorner() {
        return levelSprite.getPosition();
    }

    Vector2 getSpriteTopRightCorner() {
        TextureRegion region = TextureRepository.instance().getTexture(levelSprite.getTextureName());
        float width = region.getRegionWidth() * levelSprite.getScale().x;
        float height = region.getRegionHeight() * levelSprite.getScale().y;
        return getSpriteBottomLeftCorner().cpy().add(width, height);
    }
}
