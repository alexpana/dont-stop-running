package org.vertexarmy.dsr.leveleditor.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * created by Alex
 * on 04-Apr-2015.
 */
public class LevelSpriteDialog extends Dialog<LevelSpriteDialog.Event> {

    public LevelSpriteDialog(Stage stage, String title, Skin skin) {
        super(stage, title, skin);
    }

    @Override
    protected Actor getContent() {
        return null;
    }

    @Override
    protected void doAction() {
        notifyListener(new Event());
    }

    @Override
    protected void show() {
        getStage().addActor(this);

        setVisible(true);
        UIToolkit.centerWindow(this);
    }

    @Override
    protected void hide() {
        setVisible(false);
        remove();
    }

    public static class Event {

    }
}
