package org.vertexarmy.dsr.leveleditor.ui.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.beust.jcommander.internal.Lists;
import lombok.Getter;
import lombok.Setter;
import org.vertexarmy.dsr.core.UiContext;
import org.vertexarmy.dsr.core.assets.FontRepository;
import org.vertexarmy.dsr.core.assets.TextureRepository;
import org.vertexarmy.dsr.graphics.GraphicsUtils;
import org.vertexarmy.dsr.leveleditor.AssetName;
import org.vertexarmy.dsr.math.Algorithms;

import java.util.List;

/**
 * created by Alex
 * on 10-Apr-2015.
 */
public class Menu extends Table {

    private static final List<Menu> INSTANCES = Lists.newArrayList();

    private static final Listener EMPTY_LISTENER = new EmptyListener();

    private final List<MenuItem> menuItems = Lists.newArrayList();

    private final List<MenuItemEntry> menuItemEntries = Lists.newArrayList();

    @Setter
    private Listener listener = EMPTY_LISTENER;

    private Label titleLabel;

    @Getter
    private Vector2 displayLocation = Vector2.Zero.cpy();

    private UiContext context;

    public Menu(UiContext context) {
        this.context = context;

        titleLabel = new Label("", context.getSkin());

        Label.LabelStyle titleLabelStyle = new Label.LabelStyle(titleLabel.getStyle());
        titleLabelStyle.font = FontRepository.instance().getFont(AssetName.FONT_MARKE_8);
        titleLabelStyle.fontColor = new Color(0x808080FF);

        titleLabel.setStyle(titleLabelStyle);

        this.setBackground(new NinePatchDrawable(new NinePatch(context.getSkin().getRegion("panel-w-border"), 1, 1, 2, 1)));

        INSTANCES.add(this);
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void addItem(MenuItem menuItem) {
        menuItems.add(menuItem);
        updateItems();
    }

    @SuppressWarnings("unused")
    public void removeItem(MenuItem menuItem) {
        menuItems.remove(menuItem);
        updateItems();
    }

    public void showAt(Vector2 position) {
        hideAllMenuInstances();

        refreshItems();

        context.getStage().addActor(this);
        setVisible(true);
        setBounds(position.x, Gdx.graphics.getHeight() - (position.y + getPrefHeight()), getPrefWidth(), getPrefHeight());
        displayLocation = position;
    }

    public void hide() {
        for (MenuItemEntry entry : menuItemEntries) {
            entry.setHovered(false);
        }

        setVisible(false);
        remove();
    }

    public void setMenuListener(Listener listener) {
        if (listener == null) {
            this.listener = EMPTY_LISTENER;
        } else {
            this.listener = listener;
        }
    }


    private void updateItems() {
        clear();
        menuItemEntries.clear();
        add(titleLabel).left().padLeft(4).row();
        for (MenuItem item : menuItems) {
            MenuItemEntry menuItemEntry = new MenuItemEntry(this, item, context.getSkin());
            menuItemEntries.add(menuItemEntry);
            add(menuItemEntry).fillX().expandX().left().row();
        }
    }

    private void refreshItems() {
        clear();
        add(titleLabel).left().padLeft(4).row();
        for (MenuItemEntry entry : menuItemEntries) {
            entry.setHovered(false);
            add(entry).fillX().expandX().left().row();
        }
    }

    private void itemActivated(MenuItemEntry item) {
        listener.itemActivated(item.getMenuItem());
        hide();
    }

    public void dispose() {
        INSTANCES.remove(this);
    }

    private static void hideAllMenuInstances() {
        for (Menu menu : INSTANCES) {
            menu.hide();
        }
    }

    public interface Listener {
        void itemActivated(MenuItem item);
    }

    private static class EmptyListener implements Listener {
        @Override
        public void itemActivated(MenuItem item) {
        }
    }

    private static class MenuItemEntry extends Table {
        private final Menu menu;

        @Getter
        private final MenuItem menuItem;

        private boolean isHovered;

        private static final Color HOVER_COLOR = new Color(0xFFFFFF40);

        private static final Drawable hoveredBackground = new TextureRegionDrawable(new TextureRegion(GraphicsUtils.getColorTexture(HOVER_COLOR), 1, 1));

        // Apparently we receive an exit event after a click.
        boolean ignoreNextExitEvent = false;

        public MenuItemEntry(Menu menu, MenuItem menuItem, Skin skin) {
            this.menu = menu;
            this.menuItem = menuItem;
            addItemIcon(menuItem);

            Cell<Label> cell = addItemText(menuItem, skin);

            addExpandIcon(menuItem, cell);

            initListeners();

            pad(2, 3, 2, 3);
            padRight(8);
        }

        private void addExpandIcon(MenuItem menuItem, Cell<Label> cell) {
            if (menuItem.getChildren().isEmpty()) {
                cell.colspan(2);
            } else {
                add(new Image(TextureRepository.instance().getTexture(AssetName.ICON_PLAY_FORWARD)));
            }
        }

        private Cell<Label> addItemText(MenuItem menuItem, Skin skin) {
            return add(new Label(menuItem.getTitle(), skin)).fillX().expandX();
        }

        private void addItemIcon(MenuItem menuItem) {
            add(new Image(menuItem.getIcon()));
        }

        private void initListeners() {
            this.addListener(new InputListener() {

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    setHovered(true);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    if (!ignoreNextExitEvent) {
                        setHovered(false);
                    } else {
                        ignoreNextExitEvent = false;
                    }
                }

                @Override
                public boolean mouseMoved(InputEvent event, float x, float y) {
                    setHovered(true);
                    return true;
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    menu.itemActivated(MenuItemEntry.this);
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    ignoreNextExitEvent = true;
                    if (Algorithms.createRectangle(getX(), getY(), getWidth(), getHeight()).contains(x, y)) {
                        setHovered(true);
                    }
                }

                @Override
                public void touchDragged(InputEvent event, float x, float y, int pointer) {
                    setHovered(false);
                }
            });
        }

        private void setHovered(boolean hovered) {
            if (isHovered != hovered) {
                isHovered = hovered;
                setBackground(isHovered ? hoveredBackground : null);
            }

            if (!isHovered) {
                ignoreNextExitEvent = false;
            }
        }
    }
}
