package org.vertexarmy.dsr.leveleditor;

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
        LevelPreview.launch(levelName);
    }
}
