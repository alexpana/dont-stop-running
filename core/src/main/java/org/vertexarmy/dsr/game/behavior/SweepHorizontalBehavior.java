package org.vertexarmy.dsr.game.behavior;

import org.vertexarmy.dsr.game.objects.GameObject;

/**
 * created by Alex
 * on 2/2/2015.
 */
public class SweepHorizontalBehavior implements Behavior<GameObject> {
    private float width;
    private float speed;
    private float time;
    private float previousTime;

    public SweepHorizontalBehavior(float width, float speed) {
        this.width = width;
        this.speed = speed;

        time = 0;
        previousTime = 0;
    }

    @Override
    public void update(GameObject object, float deltaTime) {
        object.position.add(width * ((float) Math.cos(time) - (float) Math.cos(previousTime)), 0);

        previousTime = time;
        time += deltaTime / (1000.0 / speed);
    }
}
