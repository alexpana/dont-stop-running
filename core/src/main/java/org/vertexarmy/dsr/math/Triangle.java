package org.vertexarmy.dsr.math;

import com.badlogic.gdx.math.Vector2;

/**
 * created by Alex
 * on 3/18/2015.
 */
public class Triangle {
    public Vector2 v1;
    public Vector2 v2;
    public Vector2 v3;

    public Triangle() {
    }

    public Triangle(Vector2 v1, Vector2 v2, Vector2 v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public void set(Vector2 v1, Vector2 v2, Vector2 v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }
}
