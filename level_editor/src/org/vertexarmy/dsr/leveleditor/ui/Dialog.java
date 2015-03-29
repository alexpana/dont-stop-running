package org.vertexarmy.dsr.leveleditor.ui;

/**
 * created by Alex
 * on 3/28/2015.
 */
public interface Dialog {

    void show();

    void hide();

    interface Listener<E> {
        void dialogAccepted(E event);
    }
}
