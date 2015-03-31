package org.vertexarmy.dsr.leveleditor.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.vertexarmy.dsr.collection.ArrayUtils;
import org.vertexarmy.dsr.core.assets.TextureRepository;
import org.vertexarmy.dsr.graphics.GraphicsUtils;

import java.util.Collections;

/**
 * Created by alex
 * on 30.03.2015.
 */
public class SpritePickerDialog extends Dialog<SpritePickerDialog.Event> {
    private final Stage stage;

    private final Skin skin;

    private final List<Object> availableLevelsList;

    private final ImageButton previewArea;

    private TextureRegion selectedTexture;

    public SpritePickerDialog(Stage stage, String title, Skin skin) {
        super(stage, title, skin);
        this.stage = stage;
        this.skin = skin;

        availableLevelsList = new List<>(skin);

        previewArea = new ImageButton(new ImageButton.ImageButtonStyle());

        initComponents();

        initListeners();

        packLayout();

        updatePreviewToMatchSelection();
    }

    private void initComponents() {
        final java.util.List<String> availableTextures = TextureRepository.instance().textureNames();
        Collections.sort(availableTextures);
        availableLevelsList.setItems(ArrayUtils.toArray(availableTextures));
        availableLevelsList.setSize(200, 100);

        previewArea.setSize(300, 300);
        previewArea.getStyle().imageUp = new PreviewDrawable();

        getActionButton().setText("Select");

        setMovable(true);
    }

    private void initListeners() {
        availableLevelsList.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                updatePreviewToMatchSelection();
            }
        });

        UIToolkit.addListSelectionListener(availableLevelsList, new UIToolkit.ListSelectionListener() {
            @Override
            public void itemSelected() {
                notifyListener(new Event(selectedTexture));
                hide();
            }
        });
    }

    @Override
    protected Actor getContent() {
        Table contentTable = new Table();

        ScrollPane scrollPane = new ScrollPane(availableLevelsList, skin);
        scrollPane.setHeight(300);
        scrollPane.setFadeScrollBars(false);

        contentTable.add(scrollPane).expand().fill();
        contentTable.add(previewArea).row();
        return contentTable;
    }

    @Override
    protected void doAction() {
        notifyListener(new Event(selectedTexture));
        hide();
    }

    private void updatePreviewToMatchSelection() {
        final String selectedTextureName = availableLevelsList.getSelection().first().toString();
        selectedTexture = TextureRepository.instance().getTexture(selectedTextureName);
    }

    @Override
    public void show() {
        stage.addActor(this);
        setVisible(true);
        setSize(500, 355);
        UIToolkit.centerWindow(this);
    }

    @Override
    public void hide() {
        setVisible(false);
        remove();
    }

    @RequiredArgsConstructor
    public static class Event {
        @Getter
        private final TextureRegion selectedTexture;
    }

    private class PreviewDrawable extends TextureRegionDrawable {

        public static final int SIZE = 300;

        public PreviewDrawable() {
            setLeftWidth(0);
            setRightWidth(0);
            setWidth(SIZE);
            setHeight(SIZE);
            setMinWidth(SIZE);
            setMinHeight(SIZE);
        }

        @Override
        public void draw(Batch batch, float x, float y, float width, float height) {
            if (selectedTexture != null) {
                float w = selectedTexture.getRegionWidth();
                float h = selectedTexture.getRegionHeight();
                float maxSize = Math.max(w, h);
                if (maxSize <= SIZE) {
                    Vector2 center = new Vector2(x + width / 2, y + height / 2);
                    batch.draw(selectedTexture, (int) (center.x - w / 2), (int) (center.y - h / 2), w, h);
                } else {
                    batch.draw(selectedTexture, x, y, width, height);
                }
            } else {
                batch.draw(GraphicsUtils.getColorTexture(new Color(0x202020ff)), x, y, width, height);
            }
        }
    }
}
