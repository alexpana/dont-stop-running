package org.vertexarmy.dsr.level_editor;

/**
 * created by Alex
 * on 3/6/2015.
 */
public class LevelEditorLauncher {

    public static void main(String[] arguments) {
        System.out.println("Running level editor");

        final String levelName = arguments.length > 0 ? arguments[0] : null;
        LevelPreview.launch(levelName);
    }
}
