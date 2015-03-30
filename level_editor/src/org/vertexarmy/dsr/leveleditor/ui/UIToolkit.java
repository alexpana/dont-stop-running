package org.vertexarmy.dsr.leveleditor.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.vertexarmy.dsr.core.assets.IconRepository;
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
        style.imageUp = new TextureRegionDrawable(IconRepository.instance().getIcon(iconName));
        style.imageOver = new CompositeDrawable(
                IconRepository.instance().getIcon(AssetName.ICON_BACKGROUND_HOVER),
                IconRepository.instance().getIcon(iconName));
        style.imageDown = new CompositeDrawable(
                IconRepository.instance().getIcon(AssetName.ICON_BACKGROUND_PRESSED),
                IconRepository.instance().getIcon(iconName));

        return new ImageButton(style);
    }

    public static <T> void addListSelectionListener(final List<T> list, final ListSelectionListener listener) {
        list.addListener(new InputListener() {
            long previousClickTime = 0;
            int firstSelectedIndex = -1;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                long currentTime = System.currentTimeMillis();

                if (currentTime - previousClickTime < DOUBLE_CLICK_THRESHOLD && list.getSelectedIndex() == firstSelectedIndex) {
                    if (listener != null) {
                        listener.itemSelected();
                    }
                } else {
                    firstSelectedIndex = list.getSelectedIndex();
                    previousClickTime = currentTime;
                }
                return true;
            }
        });
    }

    public interface ListSelectionListener {
        void itemSelected();
    }
}
