package org.vertexarmy.dsr.leveleditor.tools.builders;

import com.badlogic.gdx.math.Vector2;
import lombok.Setter;
import org.vertexarmy.dsr.core.UiContext;
import org.vertexarmy.dsr.game.level.Level;
import org.vertexarmy.dsr.game.level.LevelSprite;
import org.vertexarmy.dsr.leveleditor.ui.Dialog;
import org.vertexarmy.dsr.leveleditor.ui.SpritePickerDialog;

/**
 * created by Alex
 * on 12-Apr-2015.
 */
public class NewLevelSpriteTool {
    private SpritePickerDialog spritePickerDialog;

    private Level boundLevel;

    private Vector2 newSpritePosition;

    @Setter
    private Listener listener;

    public NewLevelSpriteTool(UiContext uiContext) {
        spritePickerDialog = new SpritePickerDialog(uiContext, "Choose texture");

        spritePickerDialog.setListener(new Dialog.Listener<SpritePickerDialog.Event>() {
            @Override
            public void dialogAccepted(SpritePickerDialog.Event event) {
                LevelSprite levelSprite = new LevelSprite(event.getSelectedTextureName(), newSpritePosition, 0, new Vector2(1, 1), 0, true);
                boundLevel.getLevelSprites().add(levelSprite);

                if (listener != null) {
                    listener.spriteAdded(levelSprite);
                }
            }
        });
    }

    public LevelSprite createLevelSprite(Level level, Vector2 position) {
        boundLevel = level;
        newSpritePosition = position;
        spritePickerDialog.show();
        return null;
    }

    public interface Listener {
        void spriteAdded(LevelSprite newLevelSprite);
    }
}
