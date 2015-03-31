package org.vertexarmy.dsr.leveleditor.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * created by Alex
 * on 3/28/2015.
 */
public class LevelLoadDialog extends Dialog<LevelLoadDialog.Event> {
    private final List<String> availableLevelsList;

    private final ScrollPane scrollPane;

    public LevelLoadDialog(Stage stage, String title, Skin skin) {
        super(stage, title, skin);

        availableLevelsList = new List<>(skin);
        availableLevelsList.setSize(100, 100);

        getActionButton().setText("Load");

        scrollPane = new ScrollPane(availableLevelsList, skin);
        scrollPane.setHeight(300);
        scrollPane.setFadeScrollBars(false);

        initListeners();

        packLayout();
    }

    private void initListeners() {
        UIToolkit.addActionListener(availableLevelsList, new UIToolkit.ActionListener() {
            @Override
            public void action() {
                notifyListener(new Event(availableLevelsList.getSelected()));
                hide();
            }
        });
    }

    @Override
    protected Actor getContent() {
        return scrollPane;
    }

    @Override
    protected void doAction() {
        notifyListener(new Event(availableLevelsList.getSelected()));
        hide();
    }

    @Override
    public void show() {
        getStage().addActor(this);
        setVisible(true);

        setSize(300, 200);
        UIToolkit.centerWindowOnTop(this);
    }

    @Override
    public void hide() {
        setVisible(false);
        remove();
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
