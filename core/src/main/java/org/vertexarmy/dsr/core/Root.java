package org.vertexarmy.dsr.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import org.vertexarmy.dsr.core.component.ComponentType;
import org.vertexarmy.dsr.core.component.InputComponent;
import org.vertexarmy.dsr.core.component.Node;
import org.vertexarmy.dsr.core.component.RenderComponent;
import org.vertexarmy.dsr.core.systems.InputSystem;
import org.vertexarmy.dsr.core.systems.RenderSystem;

/**
 * created by Alex
 * on 3/21/2015.
 */
public class Root {

    private final InputSystem inputSystem = InputSystem.instance();

    private final RenderSystem renderSystem = RenderSystem.instance();

    public Root() {
    }

    public void initialize() {
        inputSystem.initialize();
        renderSystem.initialize();

        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }

    public void addNode(Node node) {
        if (node.hasComponent(ComponentType.RENDER)) {
            renderSystem.addRenderComponent(getRenderComponent(node));
        }

        if (node.hasComponent(ComponentType.INPUT)) {
            inputSystem.addInputComponent(getInputComponent(node));
        }
    }

    public void removeNode(Node node) {
        if (node.hasComponent(ComponentType.RENDER)) {
            renderSystem.removeRenderComponent(getRenderComponent(node));
        }

        if (node.hasComponent(ComponentType.INPUT)) {
            inputSystem.removeInputComponent(getInputComponent(node));
        }
    }

    public void update() {
        renderSystem.update();
    }

    private RenderComponent getRenderComponent(Node node) {
        if (node.hasComponent(ComponentType.RENDER)) {
            return (RenderComponent) node.getComponent(ComponentType.RENDER);
        } else {
            return null;
        }
    }

    private InputComponent getInputComponent(Node node) {
        if (node.hasComponent(ComponentType.INPUT)) {
            return (InputComponent) node.getComponent(ComponentType.INPUT);
        } else {
            return null;
        }
    }
}
