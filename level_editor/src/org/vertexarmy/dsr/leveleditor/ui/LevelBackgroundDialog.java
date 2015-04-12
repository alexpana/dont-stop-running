package org.vertexarmy.dsr.leveleditor.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.beust.jcommander.internal.Maps;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.vertexarmy.dsr.core.UiContext;
import org.vertexarmy.dsr.game.level.BackgroundLayer;
import org.vertexarmy.dsr.leveleditor.AssetName;

import java.util.Collection;
import java.util.Map;

/**
 * Created by alex
 * on 31.03.2015.
 */
public class LevelBackgroundDialog extends Dialog<LevelBackgroundDialog.Event> {

    // Model
    private final Map<String, BackgroundLayer> backgroundLayerMap = Maps.newHashMap();

    // View
    private final List<String> layerList;

    private final SpritePickerDialog texturePickerDialog;

    private final Label layerTextureNameLabel;

    private final Label textureName;

    private final ImageButton selectTextureButton;

    private final ImageButton clearTextureButton;

    private final Label parallaxSpeedLabel;

    private final TextField parallaxSpeedTextField;

    public LevelBackgroundDialog(UiContext uiContext, String title) {
        super(uiContext, title);

        texturePickerDialog = new SpritePickerDialog(uiContext, "Select Texture");

        layerList = new List<>(uiContext.getSkin());

        layerTextureNameLabel = new Label("Texture", uiContext.getSkin());

        textureName = new Label("none", uiContext.getSkin());

        selectTextureButton = UIToolkit.createImageButton(AssetName.ICON_SELECT_TEXTURE);

        clearTextureButton = UIToolkit.createImageButton(AssetName.ICON_CLEAR_TEXTURE);

        parallaxSpeedLabel = new Label("Parallax", uiContext.getSkin());

        parallaxSpeedTextField = new TextField("1.0", uiContext.getSkin());

        setMovable(true);

        initComponents();

        initListeners();

        packLayout();

        updateViewToMatchModel();
    }

    private void initComponents() {

        String[] backgroundLayerNames = new String[BackgroundLayer.Type.values().length];

        for (int i = 0; i < backgroundLayerNames.length; ++i) {
            backgroundLayerNames[i] = BackgroundLayer.Type.values()[i].name().toLowerCase();
        }

        layerList.setItems(Array.with(backgroundLayerNames));

        int i = 0;
        for (String item : layerList.getItems()) {
            backgroundLayerMap.put(item, new BackgroundLayer(null, 1.0f, BackgroundLayer.Type.values()[i]));
            i += 1;
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
        Table contentTable = new Table(getUiContext().getSkin());

        Table rightTable = new Table(getUiContext().getSkin());

        rightTable.add(layerTextureNameLabel).left().padRight(10);
        rightTable.add(textureName).fill().expand();
        rightTable.add(selectTextureButton).right();
        rightTable.add(clearTextureButton).right().row();

        rightTable.add(parallaxSpeedLabel).left().padRight(10);
        rightTable.add(parallaxSpeedTextField).fillX().expandX().top().colspan(3);

        contentTable.add(new ScrollPane(layerList, getUiContext().getSkin())).width(70).padRight(5);
        contentTable.add(rightTable).fillX().expandX().top();

        return contentTable;
    }

    @Override
    protected void doAction() {
        notifyListener(new Event(backgroundLayerMap.values()));
        hide();
    }

    @Override
    public void show() {
        getUiContext().getStage().addActor(this);
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
        BackgroundLayer selectedLayer = getSelectedLayer();

        if (selectedLayer.getTextureName() != null) {
            textureName.getStyle().fontColor = Color.WHITE;
            textureName.setText(selectedLayer.getTextureName());
        } else {
            textureName.getStyle().fontColor = new Color(0xAAAAAAff);
            textureName.setText("none");
        }

        parallaxSpeedTextField.setText(String.valueOf(selectedLayer.getParallaxSpeedScale()));
    }

    private BackgroundLayer getSelectedLayer() {
        return backgroundLayerMap.get(layerList.getSelected());
    }

    @RequiredArgsConstructor
    public static class Event {
        @Getter
        private final Collection<BackgroundLayer> backgroundLayers;
    }
}
