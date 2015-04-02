package org.vertexarmy.dsr.leveleditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * created by Alex
 * on 3/31/2015.
 */
public class Shortcuts {
    public static boolean isEditBackgroundShortcut(int keycode) {
        return keycode == Input.Keys.B && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT);
    }

    public static boolean isSpritePickerShortcut(int keycode) {
        return keycode == Input.Keys.F3;
    }

    public static boolean isFullscreenShortcut(int keycode) {
        return keycode == Input.Keys.F11;
    }

    public static boolean isOpenShortcut(int keycode) {
        return Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && keycode == Input.Keys.O;
    }

    public static boolean isSaveShortcut(int keycode) {
        return Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && keycode == Input.Keys.S;
    }

    public static boolean isHideUIShortcut(int keycode) {
        return keycode == Input.Keys.F2;
    }

    public static boolean isHideDebugInfoShortcut(int keycode) {
        return keycode == Input.Keys.F1;
    }

    public static boolean isDeselectShortcut(int keycode) {
        return keycode == Input.Keys.ESCAPE;
    }
}
