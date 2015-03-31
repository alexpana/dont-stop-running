package org.vertexarmy.dsr.leveleditor.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.vertexarmy.dsr.core.assets.TextureRepository;
import org.vertexarmy.dsr.graphics.CompositeDrawable;
import org.vertexarmy.dsr.leveleditor.AssetName;

/**
 * Created by alex
 * on 30.03.2015.
 */
public class UIToolkit {
    private static long DOUBLE_CLICK_THRESHOLD = 500;

    public static void centerWindow(Window window) {
        window.setPosition((int) ((Gdx.graphics.getWidth() - window.getWidth()) / 2), (int) ((Gdx.graphics.getHeight() - window.getHeight()) / 2));
    }

    public static void centerWindowOnTop(Window window) {
        window.setPosition((int) ((Gdx.graphics.getWidth() - window.getWidth()) / 2), (int) (Gdx.graphics.getHeight() - window.getHeight() - 50));
    }

    public static ImageButton createImageButton(String iconName) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(TextureRepository.instance().getTexture(iconName));
        style.imageOver = new CompositeDrawable(
                TextureRepository.instance().getTexture(AssetName.ICON_BACKGROUND_HOVER),
                TextureRepository.instance().getTexture(iconName));
        style.imageDown = new CompositeDrawable(
                TextureRepository.instance().getTexture(AssetName.ICON_BACKGROUND_PRESSED),
                TextureRepository.instance().getTexture(iconName));

        return new ImageButton(style);
    }

    public static <T> void addActionListener(final List<T> list, final ActionListener listener) {
        list.addListener(new InputListener() {
            long previousClickTime = 0;
            int firstSelectedIndex = -1;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                long currentTime = System.currentTimeMillis();

                if (currentTime - previousClickTime < DOUBLE_CLICK_THRESHOLD && list.getSelectedIndex() == firstSelectedIndex) {
                    if (listener != null) {
                        listener.action();
                    }
                } else {
                    firstSelectedIndex = list.getSelectedIndex();
                    previousClickTime = currentTime;
                }
                return true;
            }
        });
    }

    public static void addActionListener(Actor actor, final ActionListener actionListener) {
        actor.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                actionListener.action();
            }
        });
    }

    public static <T> void addSelectionListener(final List<T> list, final SingleSelectionListener listener) {
        list.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                listener.selectionChanged(list.getSelectedIndex());
            }
        });
    }

    public interface SingleSelectionListener {
        void selectionChanged(int selectionIndex);
    }

    public interface ActionListener {
        void action();
    }
}
