package org.vertexarmy.dsr.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import lombok.Getter;
import org.vertexarmy.dsr.core.assets.FontRepository;
import org.vertexarmy.dsr.core.assets.ShaderRepository;
import org.vertexarmy.dsr.core.assets.TextureRepository;
import org.vertexarmy.dsr.core.component.*;
import org.vertexarmy.dsr.core.systems.InputSystem;
import org.vertexarmy.dsr.core.systems.RenderSystem;
import org.vertexarmy.dsr.core.systems.UpdateSystem;

/**
 * created by Alex
 * on 3/21/2015.
 */
public class Root {
    private final InputSystem inputSystem = InputSystem.instance();

    private final RenderSystem renderSystem = RenderSystem.instance();

    private final UpdateSystem updateSystem = UpdateSystem.instance();

    @Getter
    private UiNode uiNode;

    public Root() {
    }

    public void initialize() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        updateSystem.initialize();
        inputSystem.initialize();
        renderSystem.initialize();

        ShaderRepository.instance().initialize();
        FontRepository.instance().initialize();
        TextureRepository.instance().initialize();

        uiNode = new UiNode();
        addNode(uiNode);
    }

    public void addNode(Node node) {
        if (node.hasComponent(ComponentType.RENDER)) {
            renderSystem.addRenderComponent(getRenderComponentFor(node));
        }

        if (node.hasComponent(ComponentType.INPUT)) {
            inputSystem.addInputComponent(getInputComponentFor(node));
        }

        if (node.hasComponent(ComponentType.UPDATE)) {
            updateSystem.addUpdateComponent(getUpdateComponentFor(node));
        }
    }

    public void removeNode(Node node) {
        if (node.hasComponent(ComponentType.RENDER)) {
            renderSystem.removeRenderComponent(getRenderComponentFor(node));
        }

        if (node.hasComponent(ComponentType.INPUT)) {
            inputSystem.removeInputComponent(getInputComponentFor(node));
        }

        if (node.hasComponent(ComponentType.UPDATE)) {
            updateSystem.removeUpdateComponent(getUpdateComponentFor(node));
        }
    }

    public void update() {
        updateSystem.update();
        renderSystem.update();
    }

    private RenderComponent getRenderComponentFor(Node node) {
        if (node.hasComponent(ComponentType.RENDER)) {
            return (RenderComponent) node.getComponent(ComponentType.RENDER);
        } else {
            return null;
        }
    }

    private InputComponent getInputComponentFor(Node node) {
        if (node.hasComponent(ComponentType.INPUT)) {
            return (InputComponent) node.getComponent(ComponentType.INPUT);
        } else {
            return null;
        }
    }

    private UpdateComponent getUpdateComponentFor(Node node) {
        if (node.hasComponent(ComponentType.UPDATE)) {
            return (UpdateComponent) node.getComponent(ComponentType.UPDATE);
        } else {
            return null;
        }
    }

    public void handleResize(int w, int h) {
        renderSystem.setViewportSize(w, h);

        uiNode.getStage().getViewport().setWorldSize(w, h);
        uiNode.getStage().getViewport().update(w, h, true);
    }
}
