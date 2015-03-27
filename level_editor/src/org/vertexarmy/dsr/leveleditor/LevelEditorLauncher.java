package org.vertexarmy.dsr.leveleditor;

import com.google.common.base.Function;

import javax.annotation.Nullable;
import java.io.File;

/**
 * created by Alex
 * on 3/6/2015.
 */
public class LevelEditorLauncher {
    private LevelEditorLauncher() {
    }

    public static void main(String[] arguments) {
        System.out.println("Running level editor");

        final String levelName = arguments.length > 0 ? arguments[0] : null;

        LevelEditor.launch(new Function<LevelEditor, Boolean>() {
            @Nullable
            @Override
            public Boolean apply(LevelEditor input) {
                input.loadLevel(new File(levelName));
                return true;
            }
        });
    }
}
