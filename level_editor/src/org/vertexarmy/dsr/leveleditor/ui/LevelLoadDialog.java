package org.vertexarmy.dsr.leveleditor.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * created by Alex
 * on 3/28/2015.
 */
public class LevelLoadDialog extends Dialog<LevelLoadDialog.Event> {
    private final TextButton cancelButton;

    private final TextButton load;

    private final List<String> availableLevelsList;

    private final Stage stage;

    public LevelLoadDialog(Stage stage, String title, Skin skin) {
        super(title, skin);
        this.stage = stage;

        availableLevelsList = new List<>(skin);
        availableLevelsList.setSize(100, 100);

        load = new TextButton("Open", skin);

        cancelButton = new TextButton("Cancel", skin);

        load.pad(0, 5, 3, 5);
        cancelButton.pad(0, 5, 3, 5);

        ScrollPane scrollPane = new ScrollPane(availableLevelsList, skin);
        scrollPane.setHeight(300);
        scrollPane.setFadeScrollBars(false);

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
                notifyListener(new Event(availableLevelsList.getSelected()));
                hide();
            }
        });


        UIToolkit.addListSelectionListener(availableLevelsList, new UIToolkit.ListSelectionListener() {
            @Override
            public void itemSelected() {
                notifyListener(new Event(availableLevelsList.getSelected()));
                hide();
            }
        });
    }

    @Override
    public void show() {
        stage.addActor(this);
        setVisible(true);

        setSize(300, 200);
        UIToolkit.centerWindowOnTop(this);
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
