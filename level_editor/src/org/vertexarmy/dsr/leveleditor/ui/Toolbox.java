package org.vertexarmy.dsr.leveleditor.ui;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import org.vertexarmy.dsr.leveleditor.AssetName;

/**
 * Created by alex
 * on 26.03.2015.
 */
public class Toolbox extends Table {
    private final Skin uiSkin;

    private final Listener listener;

    private ImageButton openLevelButton;

    private ImageButton saveLevelButton;

    public Toolbox(Skin uiSkin, Listener listener) {
        this.uiSkin = uiSkin;
        this.listener = listener;
        this.setBackground(new NinePatchDrawable(new NinePatch(uiSkin.getRegion("panel"), 1, 1, 2, 1)));

        createComponents();

        layoutComponents();

        initListeners();
    }

    private void createComponents() {
        openLevelButton = UIToolkit.createImageButton(AssetName.ICON_FILE_OPEN);
        saveLevelButton = UIToolkit.createImageButton(AssetName.ICON_FILE_SAVE);
    }

    private void layoutComponents() {
        this.align(Align.left);
        this.pad(2, 2, 2, 2);

        this.add(openLevelButton);
        this.add(saveLevelButton);
    }

    private void initListeners() {
        openLevelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                notifyLoadFileRequested();
            }
        });

        saveLevelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                notifySaveFileRequested();
            }
        });
    }

    private void notifyLoadFileRequested() {
        if (listener != null) {
            listener.loadFileRequested();
        }
    }

    private void notifySaveFileRequested() {
        if (listener != null) {
            listener.saveFileRequested();
        }
    }

    public interface Listener {
        void loadFileRequested();

        void saveFileRequested();
    }
}
