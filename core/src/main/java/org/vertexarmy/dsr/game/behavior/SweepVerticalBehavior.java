package org.vertexarmy.dsr.game.behavior;

import org.vertexarmy.dsr.game.objects.GameObject;

/**
 * created by Alex
 * on 2/2/2015.
 */
public class SweepVerticalBehavior implements Behavior<GameObject> {
    private float height;
    private float speed;
    private float time;
    private float previousTime;

    public SweepVerticalBehavior(float height, float speed) {
        this.height = height;
        this.speed = speed;

        time = 0;
        previousTime = 0;
    }

    @Override
    public void update(GameObject object, float deltaTime) {
        object.position.add(0, height * ((float) Math.cos(time) - (float) Math.cos(previousTime)));

        previousTime = time;
        time += deltaTime / (1000.0 / speed);
    }
}
