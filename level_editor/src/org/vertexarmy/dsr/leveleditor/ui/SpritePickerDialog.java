package org.vertexarmy.dsr.leveleditor.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import org.vertexarmy.dsr.collection.ArrayUtils;
import org.vertexarmy.dsr.core.assets.TextureRepository;

/**
 * Created by alex
 * on 30.03.2015.
 */
public class SpritePickerDialog extends Dialog {
    private final Stage stage;

    private final Skin skin;

    private final List<Object> availableLevelsList;

    private final TextButton selectButton;

    private final TextButton cancelButton;

//    private final ImageButton previewArea;

    public SpritePickerDialog(Stage stage, String title, Skin skin) {
        super(title, skin);
        this.stage = stage;
        this.skin = skin;

        availableLevelsList = new List<>(skin);

        selectButton = new TextButton("Select", skin);

        cancelButton = new TextButton("Cancel", skin);

//        previewArea = new ImageButton(skin);


        initComponents();

        initLayout();

        initListeners();
    }

    private void initComponents() {
        availableLevelsList.setItems(ArrayUtils.toArray(TextureRepository.instance().textureNames()));
        availableLevelsList.setSize(100, 100);

        selectButton.pad(0, 5, 3, 5);

        cancelButton.pad(0, 5, 3, 5);
    }

    private void initLayout() {
        ScrollPane scrollPane = new ScrollPane(availableLevelsList, skin);
        scrollPane.setHeight(300);
        scrollPane.setFadeScrollBars(false);
        scrollPane.getStyle().vScrollKnob.setMinWidth(7);
        scrollPane.getStyle().vScroll.setMinWidth(7);

        add(scrollPane).padBottom(4).expand().fill().colspan(2).row();
        add(selectButton).right().padRight(2);
        add(cancelButton).left().row();
//        add(previewArea);

        setResizable(true);
        setMovable(true);
        setModal(false);
        pack();

    }

    private void initListeners() {

    }

    @Override
    public void show() {
        stage.addActor(this);
        setVisible(true);
        setSize(300, 100);
        UIToolkit.centerWindow(this);
    }

    @Override
    public void hide() {
        setVisible(false);
        remove();
    }
}
