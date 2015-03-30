package org.vertexarmy.dsr.leveleditor.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import lombok.Setter;

/**
 * created by Alex
 * on 3/28/2015.
 */
public abstract class Dialog<E> extends Window {

    @Setter
    private Listener<E> listener;

    public Dialog(String title, Skin skin) {
        super(title, skin);

        this.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    hide();
                    return true;
                }
                return false;
            }
        });
    }

    abstract void show();

    abstract void hide();

    protected void notifyListener(E event) {
        if (listener != null) {
            listener.dialogAccepted(event);
        }
    }

    public interface Listener<E> {
        void dialogAccepted(E event);
    }
}
