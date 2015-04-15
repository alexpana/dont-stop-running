package org.vertexarmy.dsr.leveleditor.tools.editors.levelsprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.Setter;
import org.vertexarmy.dsr.core.UiContext;
import org.vertexarmy.dsr.core.assets.TextureRepository;
import org.vertexarmy.dsr.core.component.ComponentType;
import org.vertexarmy.dsr.core.component.Node;
import org.vertexarmy.dsr.game.level.LevelSprite;
import org.vertexarmy.dsr.leveleditor.tools.BindableTool;
import org.vertexarmy.dsr.leveleditor.ui.genericeditor.GenericEditor;
import org.vertexarmy.dsr.leveleditor.ui.menu.Menu;
import org.vertexarmy.dsr.leveleditor.ui.menu.MenuItem;

/**
 * created by Alex
 * on 04-Apr-2015.
 */
public class LevelSpriteEditTool extends BindableTool<LevelSprite> {
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
    private LevelSpriteEditorListener listener;

    @Getter
    private Menu contextMenu;

    private UiContext uiContext;

    public LevelSpriteEditTool(UiContext uiContext) {
        this.uiContext = uiContext;

        node.addComponent(ComponentType.INPUT, new LevelSpriteEditorInputComponent(this));
        node.addComponent(ComponentType.RENDER, new LevelSpriteEditorRenderComponent(this));

        editDialog = new GenericEditor(uiContext, "Edit sprite", LevelSprite.class);
        editDialog.setSize(235, 200);

        initMenu();
    }

    private void initMenu() {
        contextMenu = new Menu(uiContext);
        contextMenu.setTitle("Sprite");

        final MenuItem editItem = new MenuItem("Edit sprite");
        final MenuItem deleteItem = new MenuItem("Delete sprite");

        contextMenu.addItem(editItem);
        contextMenu.addItem(deleteItem);

        contextMenu.setMenuListener(new Menu.Listener() {
            @Override
            public void itemActivated(MenuItem item) {
                if (item == editItem) {
                    editDialog.show();
                }

                if (item == deleteItem) {
                    deleteSprite();
                }
            }
        });
    }

    @Override
    protected void doBind(LevelSprite levelSprite) {
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
