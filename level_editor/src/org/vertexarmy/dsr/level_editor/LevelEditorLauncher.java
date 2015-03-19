package org.vertexarmy.dsr.level_editor;

import com.badlogic.gdx.Gdx;
import java.io.IOException;
import org.vertexarmy.dsr.game.Level;

/**
 * created by Alex
 * on 3/6/2015.
 */
public class LevelEditorLauncher {

    private static LevelPreview levelPreview;

    public static void main(String[] arguments) {
        System.out.println("Running level editor");
        levelPreview = LevelPreview.launch();

        final String levelName = arguments[0];

        LevelLoader loader = new LevelLoader(levelName);
        Level level = null;

        try {
            level = loader.load();
        } catch (LevelLoader.CorruptLevel | IOException e) {
            Gdx.app.error("LevelLauncher", "Could not load SVG file. Reason: " + e.getMessage());
        }

        if (level != null) {
            levelPreview.loadLevel(level, levelName);
        }
    }
}
