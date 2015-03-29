package org.vertexarmy.dsr.leveleditor.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * created by Alex
 * on 3/28/2015.
 */
public class LevelLoadDialog extends Window implements Dialog {
    private final TextButton cancelButton;

    private final TextButton load;

    private final List<String> availableLevelsList;

    private final Stage stage;

    @Setter
    private Listener<Event> listener;

    public LevelLoadDialog(Stage stage, String title, Skin skin) {
        super(title, skin);
        this.stage = stage;

        availableLevelsList = new List<>(skin);
        availableLevelsList.setSize(100, 100);
//        availableLevelsList.getStyle().
        load = new TextButton("Open", skin);

        cancelButton = new TextButton("Cancel", skin);

        load.pad(0, 5, 3, 5);
        cancelButton.pad(0, 5, 3, 5);

        ScrollPane scrollPane = new ScrollPane(availableLevelsList, skin);
        scrollPane.setHeight(300);
        scrollPane.setFadeScrollBars(false);
        scrollPane.getStyle().vScrollKnob.setMinWidth(7);
        scrollPane.getStyle().vScroll.setMinWidth(7);

        add(scrollPane).padBottom(4).expand().fill().colspan(2).row();
        add(load).right().padRight(2);
        add(cancelButton).left();
        setResizable(false);
        setMovable(false);
        setModal(false);
        pack();

        initListeners();
    }

    private void initListeners() {
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hide();
            }
        });

        load.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (listener != null) {
                    listener.dialogAccepted(new Event(availableLevelsList.getSelected()));
                }
                hide();
            }
        });

        availableLevelsList.addListener(new InputListener() {
            long previousClickTime = 0;
            long DOUBLE_CLICK_THRESHOLD = 500;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - previousClickTime < DOUBLE_CLICK_THRESHOLD) {
                    if (listener != null) {
                        listener.dialogAccepted(new Event(availableLevelsList.getSelected()));
                    }
                    hide();
                } else {
                    previousClickTime = currentTime;
                }
                return true;
            }
        });

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

    @Override
    public void show() {
        stage.addActor(this);
        setVisible(true);

        setWidth(300);
        setHeight(200);
        setPosition((int) (Gdx.graphics.getWidth() - getWidth()) / 2, Gdx.graphics.getHeight() - getHeight() - 50);
    }

    @Override
    public void hide() {
        setVisible(false);
        this.remove();
    }

    public void setAvailableLevelsList(java.util.List<String> availableLevels) {
        this.availableLevelsList.clearItems();
        String[] availableLevelsArray = new String[availableLevels.size()];
        for (int i = 0; i < availableLevels.size(); ++i) {
            availableLevelsArray[i] = availableLevels.get(i);
        }
        this.availableLevelsList.setItems(availableLevelsArray);
    }

    @RequiredArgsConstructor
    public static class Event {
        @Getter
        private final String levelName;
    }
}
