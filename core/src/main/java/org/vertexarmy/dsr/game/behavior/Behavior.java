package org.vertexarmy.dsr.game.behavior;

/**
 * created by Alex
 * on 2/2/2015.
 */
public interface Behavior<T> {
    void update(T object, float deltaTime);
}
