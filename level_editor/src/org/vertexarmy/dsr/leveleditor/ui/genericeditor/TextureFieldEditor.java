package org.vertexarmy.dsr.leveleditor.ui.genericeditor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import org.vertexarmy.dsr.core.ReflectionHelper;
import org.vertexarmy.dsr.leveleditor.AssetName;
import org.vertexarmy.dsr.leveleditor.ui.Dialog;
import org.vertexarmy.dsr.leveleditor.ui.SpritePickerDialog;
import org.vertexarmy.dsr.leveleditor.ui.UIToolkit;

import java.lang.reflect.Field;

/**
 * created by Alex
 * on 05-Apr-2015.
 */
public class TextureFieldEditor extends FieldEditor {

    private final Label textureNameLabel;

    private final ImageButton selectTextureButton;

    private final ImageButton clearTextureButton;

    private final Table contentTable;

    private SpritePickerDialog texturePickerDialog;

    private Object boundObject;

    public TextureFieldEditor(final Field field, Stage stage, Skin skin) {
        super(field);

        texturePickerDialog = new SpritePickerDialog(stage, "Select texture", skin);

        textureNameLabel = new Label("none", skin);
        textureNameLabel.setStyle(new Label.LabelStyle(textureNameLabel.getStyle()));

        selectTextureButton = UIToolkit.createImageButton(AssetName.ICON_SELECT_TEXTURE);

        clearTextureButton = UIToolkit.createImageButton(AssetName.ICON_CLEAR_TEXTURE);

        contentTable = new Table(skin);
        contentTable.add(textureNameLabel).fill().expand();
        contentTable.add(selectTextureButton).right();
        contentTable.add(clearTextureButton).right().row();

        UIToolkit.addActionListener(selectTextureButton, new UIToolkit.ActionListener() {
            @Override
            public void action() {
                texturePickerDialog.show();
            }
        });

        UIToolkit.addActionListener(clearTextureButton, new UIToolkit.ActionListener() {
            @Override
            public void action() {
                setSelectedTexture(null);
            }
        });

        texturePickerDialog.setListener(new Dialog.Listener<SpritePickerDialog.Event>() {
            @Override
            public void dialogAccepted(SpritePickerDialog.Event event) {
                setSelectedTexture(event.getSelectedTextureName());
            }
        });
    }

    @Override
    public Actor getUiComponent() {
        return contentTable;
    }

    @Override
    public void bindToObject(Object object) {
        this.boundObject = object;
        if (object != null) {
            setSelectedTexture(ReflectionHelper.<String>getField(object, getBoundField(), null));
        }
    }

    private void setSelectedTexture(String textureName) {
        if (textureName != null) {
            textureNameLabel.getStyle().fontColor = Color.WHITE;
            textureNameLabel.setText(textureName.length() > 15 ? textureName.substring(0, 15) + ".." : textureName);
        } else {
            textureNameLabel.getStyle().fontColor = new Color(0xAAAAAAff);
            textureNameLabel.setText("none");
        }
        ReflectionHelper.setField(boundObject, getBoundField(), textureName);
    }
}
