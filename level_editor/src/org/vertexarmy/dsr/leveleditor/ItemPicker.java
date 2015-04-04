package org.vertexarmy.dsr.leveleditor;

import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.vertexarmy.dsr.core.systems.RenderSystem;
import org.vertexarmy.dsr.game.level.Level;
import org.vertexarmy.dsr.math.Algorithms;
import org.vertexarmy.dsr.math.Polygon;

/**
 * created by Alex
 * on 04-Apr-2015.
 */
public class ItemPicker {
	enum ItemType {
		TERRAIN_POLYGON,
		LEVEL_SPRITE,
		NONE
	}

	@RequiredArgsConstructor
	public static class PickResult {
		@Getter
		private final ItemType type;

		@Getter
		private final Object object;

		public static final PickResult NOTHING = new PickResult(ItemType.NONE, null);
	}

	public static PickResult pickObject(Level level, int screenX, int screenY) {
		Polygon pickedPolygon = pickTerrainPolygon(level, screenX, screenY);

		if (pickedPolygon != null) {
			return new PickResult(ItemType.TERRAIN_POLYGON, pickedPolygon);
		}

		return PickResult.NOTHING;
	}

	private static Polygon pickTerrainPolygon(Level level, int screenX, int screenY) {
		Polygon clickedPolygon = null;

		Vector2 mouseWorldPosition = RenderSystem.instance().screenToWorld(screenX, screenY);

		for (Polygon terrainPolygon : level.getTerrainPatches()) {
			if (Algorithms.polygonContainsVertex(mouseWorldPosition, terrainPolygon)) {
				clickedPolygon = terrainPolygon;
			}
		}
		return clickedPolygon;
	}
}
