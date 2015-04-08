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
import org.vertexarmy.dsr.leveleditor.editors.Bindable;
import org.vertexarmy.dsr.leveleditor.ui.genericeditor.GenericEditor;

/**
 * created by Alex
 * on 04-Apr-2015.
 */
public class SpriteEditor extends Bindable<LevelSprite> {
    @Getter
    private final Node node = new Node();

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

    @Override
    public void doBind(LevelSprite levelSprite) {
        editDialog.bindToObject(levelSprite);
    }

    void deleteSprite() {
        if (isBound()) {
            notifyDeleteSpriteRequested();
            unbind();
        }
    }

    private void notifyDeleteSpriteRequested() {
        if (listener != null) {
            listener.deleteSpriteRequested();
        }
    }

    Vector2 getSpriteBottomLeftCorner() {
        return getBoundObject().getPosition();
    }

    Vector2 getSpriteTopRightCorner() {
        TextureRegion region = TextureRepository.instance().getTexture(getBoundObject().getTextureName());
        float width = region.getRegionWidth() * getBoundObject().getScale().x;
        float height = region.getRegionHeight() * getBoundObject().getScale().y;
        return getSpriteBottomLeftCorner().cpy().add(width, height);
    }
}
