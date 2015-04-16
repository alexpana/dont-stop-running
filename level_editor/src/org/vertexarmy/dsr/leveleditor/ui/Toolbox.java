package org.vertexarmy.dsr.leveleditor.ui;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.beust.jcommander.internal.Maps;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.vertexarmy.dsr.leveleditor.AssetName;

import java.util.Map;

/**
 * Created by alex
 * on 26.03.2015.
 */
public class Toolbox extends Table {

    @RequiredArgsConstructor
    public enum Item {
        OPEN_LEVEL(AssetName.ICON_FILE_OPEN),
        SAVE_LEVEL(AssetName.ICON_FILE_SAVE),
        AUTO_SCROLL_BACKWARD(AssetName.ICON_PLAY_REVERSE),
        AUTO_SCROLL_PAUSE(AssetName.ICON_PAUSE),
        AUTO_SCROLL_FORWARD(AssetName.ICON_PLAY_FORWARD),
        SETTINGS(AssetName.ICON_SETTINGS);

        @Getter
        private final String iconName;
    }

    private final Listener listener;

    private Map<ImageButton, Item> imageButtonItemsMap = Maps.newLinkedHashMap();

    public Toolbox(Skin uiSkin, Listener listener) {
        this.listener = listener;
        this.setBackground(new NinePatchDrawable(new NinePatch(uiSkin.getRegion("panel"), 1, 1, 2, 1)));

        createComponents();

        layoutComponents();

        initListeners();
    }

    private void createComponents() {
        for (Item item : Item.values()) {
            imageButtonItemsMap.put(UIToolkit.createImageButton(item.getIconName()), item);
        }
    }

    private void layoutComponents() {
        this.align(Align.left);
        this.pad(2, 2, 2, 2);

        for (ImageButton imageButton : imageButtonItemsMap.keySet()) {
            add(imageButton);
        }
    }

    private void initListeners() {
        for (final ImageButton imageButton : imageButtonItemsMap.keySet()) {
            UIToolkit.addActionListener(imageButton, new UIToolkit.ActionListener() {
                @Override
                public void action() {
                    notifyActionRequested(imageButtonItemsMap.get(imageButton));
                }
            });
        }
    }

    private void notifyActionRequested(Item item) {
        if (listener != null) {
            listener.actionRequested(item);
        }
    }

    public interface Listener {
        void actionRequested(Item actionItem);
    }
}
