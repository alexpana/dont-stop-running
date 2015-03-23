package org.vertexarmy.dsr.level_editor;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * created by Alex
 * on 3/20/2015.
 */
public class DebugValuesPanel extends Table {

    public DebugValuesPanel(final Skin skin) {
        DebugValues.instance().addListener(new DebugValues.Listener() {
            @Override
            public void valuesChanged() {
                clear();
                for (String key : DebugValues.instance().keySet()) {
                    add(new Label(key + ":", skin)).left();
                    add(new Label(DebugValues.instance().getValue(key), skin)).left().row();
                }
            }
        });
    }

//    public void setCameraPosition(int x, int y) {
//        cameraPositionLabel.setText(x + ", " + y);
//    }
//
//    public void setLoadedLevel(String loadedLevel) {
//        levelNameLabel.setText(loadedLevel);
//    }
//
//    public void setZoomLevel(float zoomLevel) {
//        zoomLevelLabel.setText(String.valueOf(zoomLevel * 100) + " %");
//    }
//
//    public void setMousePosition(int x, int y) {
//        mouseCoordinatesViewport.setText(x + ", " + y);
//        Vector2 mouseWorld = RenderSystem.instance().screenToWorld(new Vector2(x, y));
//        mouseCoordinatesWorld.setText((int) mouseWorld.x + ", " + (int) mouseWorld.y);
//    }
}
