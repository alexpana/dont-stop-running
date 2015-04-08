package org.vertexarmy.dsr.leveleditor.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;

/**
 * created by Alex
 * on 08-Apr-2015.
 */
public class Spinner extends TextField {
    private static final DecimalFormat FORMAT = new DecimalFormat("#.##");

    private float previousValue;

    @Getter
    @Setter
    private float increment = 0.1f;

    public Spinner(String text, Skin skin) {
        super(text, skin);

        this.addListener(new InputListener() {
            @Override
            public boolean scrolled(InputEvent event, float x, float y, int amount) {
                if (Math.signum(amount) < 0) {
                    incrementValue();
                } else {
                    decrementValue();
                }
                return false;
            }

            @Override
            public boolean keyTyped(InputEvent event, char character) {
                try {
                    previousValue = Float.valueOf(getText());
                } catch (Exception ignored) {
                }
                notifyTextChanged();
                return false;
            }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                getStage().setScrollFocus(Spinner.this);
                return false;
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.UP) {
                    incrementValue();
                }

                if (keycode == Input.Keys.DOWN) {
                    decrementValue();
                }

                return false;
            }
        });

        this.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    previousValue = Float.valueOf(getText());
                } catch (Exception ignored) {
                }
            }
        });
    }

    private void incrementValue() {
        setText(FORMAT.format(previousValue + increment));
    }

    private void decrementValue() {
        setText(FORMAT.format(previousValue - increment));
    }

    public void setText(String text) {
        super.setText(text);
        notifyTextChanged();
    }

    private void notifyTextChanged() {
        ChangeListener.ChangeEvent event = new ChangeListener.ChangeEvent();
        event.setTarget(this);
        notify(event, false);
    }
}
