package org.vertexarmy.dsr.core.component;

/**
 * created by Alex
 * on 3/21/2015.
 */
public abstract class RenderComponent {
    public static enum RenderList {
        UI,
        DEFAULT
    }

    public abstract void render();

    public RenderList getRenderList() {
        return RenderList.DEFAULT;
    }
}
