package org.vertexarmy.dsr.leveleditor.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;
import com.beust.jcommander.internal.Maps;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.vertexarmy.dsr.game.Level;
import org.vertexarmy.dsr.leveleditor.AssetName;

/**
 * Created by alex
 * on 31.03.2015.
 */
public class LevelBackgroundDialog extends Dialog<LevelBackgroundDialog.Event> {

    // Model
    private final Map<String, Level.BackgroundLayer> backgroundLayerMap = Maps.newHashMap();

    // View
    private final List<String> layerList;

    private final SpritePickerDialog texturePickerDialog;

    private final Label layerTextureNameLabel;

    private final Label textureName;

    private final ImageButton selectTextureButton;

    private final ImageButton clearTextureButton;

    private final Label parallaxSpeedLabel;

    private final TextField parallaxSpeedTextField;

    public LevelBackgroundDialog(Stage stage, String title, Skin skin) {
        super(stage, title, skin);

        texturePickerDialog = new SpritePickerDialog(stage, "Select Texture", skin);

        layerList = new List<>(skin);

        layerTextureNameLabel = new Label("Texture", skin);

        textureName = new Label("none", skin);

        selectTextureButton = UIToolkit.createImageButton(AssetName.ICON_SELECT_TEXTURE);

        clearTextureButton = UIToolkit.createImageButton(AssetName.ICON_CLEAR_TEXTURE);

        parallaxSpeedLabel = new Label("Parallax", skin);

        parallaxSpeedTextField = new TextField("1.0", skin);

        initComponents();

        initListeners();

        packLayout();

        updateViewToMatchModel();
    }

    private void initComponents() {
        layerList.setItems(Array.with("Background", "Far", "Middle", "Near", "Foreground"));

        for (String item : layerList.getItems()) {
            backgroundLayerMap.put(item, new Level.BackgroundLayer(null, 1.0f));
        }

        textureName.setStyle(new Label.LabelStyle(textureName.getStyle()));

        getActionButton().setText("Save");
    }

    private void initListeners() {
        UIToolkit.addActionListener(selectTextureButton, new UIToolkit.ActionListener() {
            @Override
            public void action() {
                texturePickerDialog.show();
            }
        });

        UIToolkit.addActionListener(clearTextureButton, new UIToolkit.ActionListener() {
            @Override
            public void action() {
                getSelectedLayer().setTextureName(null);
                updateViewToMatchModel();
            }
        });

        UIToolkit.addActionListener(layerList, new UIToolkit.ActionListener() {
            @Override
            public void action() {
                updateViewToMatchModel();
            }
        });

        UIToolkit.addSelectionListener(layerList, new UIToolkit.SingleSelectionListener() {
            @Override
            public void selectionChanged(int selectionIndex) {
                updateViewToMatchModel();
            }
        });

        parallaxSpeedTextField.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                try {
                    float parallaxSpeedScale = Float.parseFloat(parallaxSpeedTextField.getText());
                    getSelectedLayer().setParallaxSpeedScale(parallaxSpeedScale);
                    return true;
                } catch (NumberFormatException ignored) {
                    return false;
                }
            }
        });

        texturePickerDialog.setListener(new Dialog.Listener<SpritePickerDialog.Event>() {
            @Override
            public void dialogAccepted(SpritePickerDialog.Event event) {
                getSelectedLayer().setTextureName(event.getSelectedTextureName());
                updateViewToMatchModel();
            }
        });
    }

    @Override
    protected Actor getContent() {
        Table contentTable = new Table(getSkin());

        Table rightTable = new Table(getSkin());

        rightTable.add(layerTextureNameLabel).left().padRight(10);
        rightTable.add(textureName).fill().expand();
        rightTable.add(selectTextureButton).right();
        rightTable.add(clearTextureButton).right().row();

        rightTable.add(parallaxSpeedLabel).left().padRight(10);
        rightTable.add(parallaxSpeedTextField).fillX().expandX().top().colspan(3);

        contentTable.add(new ScrollPane(layerList, getSkin())).width(70).padRight(5);
        contentTable.add(rightTable).fillX().expandX().top();

        return contentTable;
    }

    @Override
    protected void doAction() {
        notifyListener(new Event(backgroundLayerMap));
        hide();
    }

    @Override
    public void show() {
        getStage().addActor(this);
        setVisible(true);

        setWidth(400);

        UIToolkit.centerWindow(this);

    }

    @Override
    public void hide() {
        setVisible(false);
        remove();
    }

    private void updateViewToMatchModel() {
        Level.BackgroundLayer selectedLayer = getSelectedLayer();

        if (selectedLayer.getTextureName() != null) {
            textureName.getStyle().fontColor = Color.WHITE;
            textureName.setText(selectedLayer.getTextureName());
        } else {
            textureName.getStyle().fontColor = new Color(0xAAAAAAff);
            textureName.setText("none");
        }

        parallaxSpeedTextField.setText(String.valueOf(selectedLayer.getParallaxSpeedScale()));
    }

    private Level.BackgroundLayer getSelectedLayer() {
        return backgroundLayerMap.get(layerList.getSelected());
    }

    @RequiredArgsConstructor
    public static class Event {
        @Getter
        private final Map<String, Level.BackgroundLayer> backgroundLayers;
    }
}
