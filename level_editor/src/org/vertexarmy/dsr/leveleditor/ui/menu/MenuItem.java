package org.vertexarmy.dsr.leveleditor.ui.menu;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.beust.jcommander.internal.Lists;
import lombok.Getter;

import java.util.List;

/**
 * created by Alex
 * on 10-Apr-2015.
 */
public class MenuItem {
    @Getter
    private final String title;

    @Getter
    private final List<MenuItem> children = Lists.newArrayList();

    @Getter
    private Drawable icon;

    public MenuItem(String title) {
        this.title = title;
    }

    public void addChild(MenuItem menuItem) {
        children.add(menuItem);
    }

    public void setIcon(TextureRegion icon) {
        this.icon = new TextureRegionDrawable(icon);
    }
}
