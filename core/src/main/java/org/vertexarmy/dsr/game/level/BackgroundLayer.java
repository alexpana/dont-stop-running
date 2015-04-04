package org.vertexarmy.dsr.game.level;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * created by Alex
 * on 4/4/2015.
 */
public class BackgroundLayer implements Serializable {

    public enum Type {
        BACKGROUND(0),
        FAR(1),
        MIDDLE(2),
        NEAR(3),
        FOREGROUND(4);

        @Getter
        private final int zOrder;

        Type(int zOrder) {
            this.zOrder = zOrder;
        }
    }

	@Getter
	@Setter
	private String textureName;

	@Getter
	@Setter
	private float parallaxSpeedScale;

	@Getter
	@Setter
	private Type type;

	public BackgroundLayer(String textureName, float parallaxSpeedScale, Type type) {
		this.textureName = textureName;
		this.parallaxSpeedScale = parallaxSpeedScale;
		this.type = type;
	}
}
