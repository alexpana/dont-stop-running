package org.vertexarmy.dsr.leveleditor.ui;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * created by Alex
 * on 3/28/2015.
 */
public class LevelSaveDialog extends Dialog<LevelSaveDialog.Event> {
    public static final char ESCAPE_CHARACTER = '\u001B';

    public static final char RETURN_CHARACTER = '\r';

    private final TextButton cancelButton;

    private final TextButton saveButton;

    private final Stage stage;

    private final TextField nameTextField;

    @Setter
    private Listener<Event> listener;

    public LevelSaveDialog(Stage stage, String title, Skin skin) {
        super(title, skin);
        this.stage = stage;
        nameTextField = new TextField("", skin);
        saveButton = new TextButton("Save", skin);
        cancelButton = new TextButton("Cancel", skin);

        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });

        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (listener != null) {
                    listener.dialogAccepted(new Event(nameTextField.getText()));
                }
                hide();
            }
        });

        nameTextField.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                if (character == ESCAPE_CHARACTER) {
                    hide();
                    return true;
                }

                if (character == RETURN_CHARACTER) {
                    if (listener != null) {
                        listener.dialogAccepted(new Event(nameTextField.getText()));
                    }
                    hide();
                    return true;
                }

                return false;
            }
        });

        saveButton.pad(0, 5, 3, 5);
        cancelButton.pad(0, 5, 3, 5);

        add(nameTextField).left().fillX().expandX().padRight(1);
        add(saveButton).padRight(2);
        add(cancelButton);
        setResizable(false);
        setMovable(false);
        setModal(false);

        pack();
    }

    @Override
    public void show() {
        stage.addActor(this);
        setVisible(true);

        setWidth(300);

        UIToolkit.centerWindowOnTop(this);

        stage.setKeyboardFocus(nameTextField);
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
