package org.vertexarmy.dsr.leveleditor.ui;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import org.vertexarmy.dsr.graphics.CompositeDrawable;

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
        openLevelButton = createImageButton(IconRepository.FILE_OPEN);
        saveLevelButton = createImageButton(IconRepository.FILE_SAVE);
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

    private ImageButton createImageButton(String iconName) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = createImageUp(iconName);
        style.imageOver = createImageOver(iconName);
        style.imageDown = createImageDown(iconName);

        return new ImageButton(style);
    }

    private Drawable createImageUp(String iconName) {
        return new TextureRegionDrawable(IconRepository.instance().getIcon(iconName));
    }

    private Drawable createImageOver(String iconName) {
        return new CompositeDrawable(
                IconRepository.instance().getIcon(IconRepository.COMPONENT_HOVER),
                IconRepository.instance().getIcon(iconName));
    }

    private Drawable createImageDown(String iconName) {
        return new CompositeDrawable(
                IconRepository.instance().getIcon(IconRepository.COMPONENT_PRESSED),
                IconRepository.instance().getIcon(iconName));
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
