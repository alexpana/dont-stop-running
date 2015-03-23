package org.vertexarmy.dsr.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import lombok.Getter;
import org.vertexarmy.dsr.core.component.ComponentType;
import org.vertexarmy.dsr.core.component.InputComponent;
import org.vertexarmy.dsr.core.component.Node;
import org.vertexarmy.dsr.core.component.RenderComponent;

/**
 * created by Alex
 * on 3/21/2015.
 */
public class UiNode extends Node {
    @Getter
    private final Stage stage;

    @Getter
    private final Table contentTable;

    @Getter
    private final Skin uiSkin;

    public UiNode() {
        FileHandle skinFileHandle = Gdx.files.internal("ui/skin.json");
        uiSkin = new Skin(skinFileHandle);

        stage = new Stage();

        contentTable = new Table();
        contentTable.setFillParent(true);
        contentTable.pad(4, 4, 4, 4);

        stage.addActor(contentTable);

        contentTable.left().top();

        addComponent(ComponentType.INPUT, new InputComponent() {
            @Override
            public InputProcessor getInputAdapter() {
                return stage;
            }
        });

        addComponent(ComponentType.RENDER, new RenderComponent() {
            @Override
            public void render() {
                stage.act(Gdx.graphics.getDeltaTime());
                stage.draw();
            }

            @Override
            public RenderList getRenderList() {
                return RenderList.UI;
            }
        });
    }
}
