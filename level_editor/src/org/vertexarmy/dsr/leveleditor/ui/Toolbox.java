package org.vertexarmy.dsr.leveleditor.ui;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import org.vertexarmy.dsr.leveleditor.AssetName;

/**
 * Created by alex
 * on 26.03.2015.
 */
public class Toolbox extends Table {
    private final Listener listener;

    private ImageButton openLevelButton;

    private ImageButton saveLevelButton;

    private ImageButton autoScrollForwardButton;

    private ImageButton pauseAutoScrollButton;

    private ImageButton autoScrollBackwardButton;

    private ImageButton editTerrainPatchButton;

    public Toolbox(Skin uiSkin, Listener listener) {
        this.listener = listener;
        this.setBackground(new NinePatchDrawable(new NinePatch(uiSkin.getRegion("panel"), 1, 1, 2, 1)));

        createComponents();

        layoutComponents();

        initListeners();
    }

    private void createComponents() {
        openLevelButton = UIToolkit.createImageButton(AssetName.ICON_FILE_OPEN);
        saveLevelButton = UIToolkit.createImageButton(AssetName.ICON_FILE_SAVE);

        autoScrollForwardButton = UIToolkit.createImageButton(AssetName.ICON_PLAY_FORWARD);
        pauseAutoScrollButton = UIToolkit.createImageButton(AssetName.ICON_PAUSE);
        autoScrollBackwardButton = UIToolkit.createImageButton(AssetName.ICON_PLAY_REVERSE);

        editTerrainPatchButton = UIToolkit.createImageButton(AssetName.ICON_PAUSE);
    }

    private void layoutComponents() {
        this.align(Align.left);
        this.pad(2, 2, 2, 2);

        this.add(openLevelButton);
        this.add(saveLevelButton);
        this.add(autoScrollBackwardButton);
        this.add(pauseAutoScrollButton);
        this.add(autoScrollForwardButton);
        this.add(editTerrainPatchButton);
    }

    private void initListeners() {
        UIToolkit.addActionListener(openLevelButton, new UIToolkit.ActionListener() {
            @Override
            public void action() {
                notifyLoadFileRequested();
            }
        });

        UIToolkit.addActionListener(saveLevelButton, new UIToolkit.ActionListener() {
            @Override
            public void action() {
                notifySaveFileRequested();
            }
        });

        UIToolkit.addActionListener(autoScrollBackwardButton, new UIToolkit.ActionListener() {
            @Override
            public void action() {
                notifyAutoScrollBackward();
            }
        });

        UIToolkit.addActionListener(pauseAutoScrollButton, new UIToolkit.ActionListener() {
            @Override
            public void action() {
                notifyPauseAutoScroll();
            }
        });

        UIToolkit.addActionListener(autoScrollForwardButton, new UIToolkit.ActionListener() {
            @Override
            public void action() {
                notifyAutoScrollForward();
            }
        });

        UIToolkit.addActionListener(autoScrollForwardButton, new UIToolkit.ActionListener() {
            @Override
            public void action() {
                notifyAutoScrollForward();
            }
        });

        UIToolkit.addActionListener(editTerrainPatchButton, new UIToolkit.ActionListener() {
            @Override
            public void action() {
                notifyEditTerrainPatch();
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

    private void notifyAutoScrollBackward() {
        if (listener != null) {
            listener.autoScrollBackwardRequested();
        }
    }

    private void notifyPauseAutoScroll() {
        if (listener != null) {
            listener.pauseAutoScrollRequested();
        }
    }

    private void notifyAutoScrollForward() {
        if (listener != null) {
            listener.autoScrollForwardRequested();
        }
    }

    private void notifyEditTerrainPatch() {
        if (listener != null) {
            listener.editTerrainPatch();
        }
    }

    public interface Listener {
        void loadFileRequested();

        void saveFileRequested();

        void autoScrollForwardRequested();

        void pauseAutoScrollRequested();

        void autoScrollBackwardRequested();

        void editTerrainPatch();
    }
}
