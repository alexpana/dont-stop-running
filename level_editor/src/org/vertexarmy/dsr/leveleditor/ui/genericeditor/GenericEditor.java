package org.vertexarmy.dsr.leveleditor.ui.genericeditor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.beust.jcommander.internal.Maps;
import org.vertexarmy.dsr.core.ReflectionHelper;
import org.vertexarmy.dsr.core.UiContext;
import org.vertexarmy.dsr.leveleditor.ui.Dialog;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * created by Alex
 * on 05-Apr-2015.
 */
public class GenericEditor extends Dialog {

    private final Table contentTable;

    private final Table objectContentTable;

    private final Table emptyContentTable = createEmptyContentTable();

    private final Map<Field, FieldEditor> fieldEditorMap = Maps.newHashMap();

    private float fullSizeWidth = 200;

    private float fullSizeHeight = 100;

    public GenericEditor(UiContext uiContext, String title, Class objectClass) {
        super(uiContext, title);

        objectContentTable = createContentTable(objectClass);

        contentTable = new Table(uiContext.getSkin());

        contentTable.add(emptyContentTable);

        this.setMovable(true);

        packLayout();
    }

    private Table createEmptyContentTable() {
        Table result = new Table(getUiContext().getSkin());

        Label emptyLabel = new Label("Nothing selected", getUiContext().getSkin());
        emptyLabel.setAlignment(Align.center);
        result.add(emptyLabel).fillX().expandX().center();
        return result;
    }

    private Table createContentTable(Class objectClass) {
        Table result = new Table(getUiContext().getSkin());

        for (Field field : objectClass.getDeclaredFields()) {
            if (ReflectionHelper.classHasProperty(objectClass, field.getName())) {
                FieldEditor editor = createEditorForField(field);
                if (editor != null) {
                    fieldEditorMap.put(field, editor);
                    result.add(new Label(field.getName(), getUiContext().getSkin())).right().fillX().padRight(6);
                    result.add(editor.getUiComponent()).fillX().expandX().left().width(150).row();
                }
            }
        }

        return result;
    }

    private FieldEditor createEditorForField(final Field field) {
        if (field.getType() == Boolean.class || field.getType() == boolean.class) {
            return new BooleanFieldEditor(field, getUiContext().getSkin());
        }

        if (field.getType() == Vector2.class) {
            return new Vector2FieldEditor(field, getUiContext().getSkin());
        }

        if (field.getType() == String.class && field.getName().contains("texture")) {
            return new TextureFieldEditor(field, getUiContext());
        }

        if (field.getType() == Float.class || field.getType() == float.class) {
            return new FloatFieldEditor(field, getUiContext().getSkin());
        }

        if (field.getType() == Integer.class || field.getType() == int.class) {
            return new IntegerFieldEditor(field, getUiContext().getSkin());
        }

        return null;
    }

    public void bindToObject(Object object) {
        if (object == null) {
            contentTable.clear();
            contentTable.add(emptyContentTable).fill().expand().center();
        } else {
            contentTable.clear();
            contentTable.add(objectContentTable).fillX().expandX().top();
            fullSizeWidth = getPrefWidth();
            fullSizeHeight = getPrefHeight();
        }

        for (FieldEditor fieldEditor : fieldEditorMap.values()) {
            fieldEditor.bindToObject(object);
        }

        this.setSize(fullSizeWidth, fullSizeHeight);
    }

    @Override
    protected Actor getContent() {
        return contentTable;
    }

    @Override
    protected void doAction() {
        hide();
    }

    @Override
    public void show() {
        getUiContext().getStage().addActor(this);
        this.setVisible(true);

        this.setSize(fullSizeWidth, fullSizeHeight);
    }

    @Override
    public void hide() {
        this.setVisible(false);
        remove();
    }
}
