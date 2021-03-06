package org.vertexarmy.dsr.atlas_viewer;

import org.vertexarmy.dsr.atlas_viewer.ui.AtlasViewerFrame;

import javax.swing.*;
import java.io.File;

/**
 * created by Alex
 * on 3/14/2015.
 */
public class AtlasViewerLauncher {
    static {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
    }

    public static void main(String[] args) {
        AtlasViewerFrame frame = new AtlasViewerFrame();
        frame.setVisible(true);

        if (args.length > 0) {
            frame.getModel().setTextureAtlas(AtlasLoader.load(new File(args[0])));
        }
    }
}
