package org.vertexarmy.dsr.leveleditor.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.vertexarmy.dsr.leveleditor.FileUtils;

import java.io.File;

/**
 * created by Alex
 * on 3/28/2015.
 */
public class LevelLoadDialog extends Dialog<LevelLoadDialog.Event> {
    private static final String LEVEL_EXTENSION = "json";

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
        discoverAvailableLevelsList();
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

    public void discoverAvailableLevelsList() {
        java.util.List<File> discoveredLevelFiles = FileUtils.discoverFiles(new File("levels/"), ImmutableList.of(LEVEL_EXTENSION));

        this.availableLevelsList.clearItems();
        String[] availableLevelsArray = new String[discoveredLevelFiles.size()];
        for (int i = 0; i < discoveredLevelFiles.size(); ++i) {
            String filename = discoveredLevelFiles.get(i).getName();
            availableLevelsArray[i] = filename.substring(0, filename.length() - LEVEL_EXTENSION.length() - 1);
        }
        this.availableLevelsList.setItems(availableLevelsArray);
    }

    @RequiredArgsConstructor
    public static class Event {
        @Getter
        private final String levelName;
    }
}
