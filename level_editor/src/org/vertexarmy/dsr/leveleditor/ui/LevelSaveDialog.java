package org.vertexarmy.dsr.leveleditor.ui;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.vertexarmy.dsr.core.UiContext;

/**
 * created by Alex
 * on 3/28/2015.
 */
public class LevelSaveDialog extends Dialog<LevelSaveDialog.Event> {

    public static final char ESCAPE_CHARACTER = '\u001B';

    public static final char RETURN_CHARACTER = '\r';

    private final TextField nameTextField;

    public LevelSaveDialog(UiContext uiContext, String title) {
        super(uiContext, title);

        nameTextField = new TextField("", uiContext.getSkin());

        getActionButton().setText("Save");

        initListeners();

        packLayout();
    }

    private void initListeners() {
        nameTextField.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                if (character == ESCAPE_CHARACTER) {
                    hide();
                    return true;
                }

                if (character == RETURN_CHARACTER) {
                    doAction();
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    protected Actor getContent() {
        return nameTextField;
    }

    @Override
    protected void doAction() {
        notifyListener(new Event(nameTextField.getText()));
        hide();
    }

    @Override
    public void show() {
        getUiContext().getStage().addActor(this);
        setVisible(true);

        setWidth(300);

        UIToolkit.centerWindowOnTop(this);

        getStage().setKeyboardFocus(nameTextField);
    }

    @Override
    public void hide() {
        nameTextField.setText("");

        setVisible(false);
        getStage().setKeyboardFocus(null);
        remove();
    }

    @RequiredArgsConstructor
    public static class Event {
        @Getter
        private final String filename;
    }
}
