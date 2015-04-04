package org.vertexarmy.dsr.leveleditor.levelrenderer;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.beust.jcommander.internal.Lists;
import org.vertexarmy.dsr.core.assets.TextureRepository;
import org.vertexarmy.dsr.core.systems.RenderSystem;
import org.vertexarmy.dsr.game.level.Level;
import org.vertexarmy.dsr.game.level.LevelSprite;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * created by Alex
 * on 4/4/2015.
 */
public class LevelSpritesRenderer {
	private Level level;

	private final List<Sprite> foregroundSpriteList = Lists.newArrayList();

	private final List<Sprite> backgroundSpriteList = Lists.newArrayList();

	private static final SpriteZOrderComparator zOrderComparator = new SpriteZOrderComparator();

	public void setLevel(Level level) {
		this.level = level;
		reloadLevel();
	}

	public void renderForegroundSprites() {
		renderSpriteList(foregroundSpriteList);
	}

	public void renderBackgroundSprites() {
		renderSpriteList(backgroundSpriteList);
	}

	private void renderSpriteList(List<Sprite> spriteList) {
		SpriteBatch spriteBatch = RenderSystem.instance().getSpriteBatch();

		spriteBatch.begin();
		for (Sprite sprite : spriteList) {
			sprite.draw(spriteBatch);
		}
		spriteBatch.end();
	}

	public void reloadLevel() {
		if (level == null) {
			return;
		}

		foregroundSpriteList.clear();
		backgroundSpriteList.clear();

		List<LevelSprite> foregroundLevelSpriteList = Lists.newArrayList();

		List<LevelSprite> backgroundLevelSpriteList = Lists.newArrayList();

		for (LevelSprite sprite : level.getLevelSprites()) {
			if (sprite.isForeground()) {
				foregroundLevelSpriteList.add(sprite);
			} else {
				backgroundLevelSpriteList.add(sprite);
			}
		}

		Collections.sort(foregroundLevelSpriteList, zOrderComparator);

		Collections.sort(backgroundLevelSpriteList, zOrderComparator);

		for (LevelSprite levelSprite : foregroundLevelSpriteList) {
			foregroundSpriteList.add(spriteFromLevelSprite(levelSprite));
		}

		for (LevelSprite levelSprite : backgroundLevelSpriteList) {
			backgroundSpriteList.add(spriteFromLevelSprite(levelSprite));
		}
	}

	private Sprite spriteFromLevelSprite(LevelSprite levelSprite) {
		Sprite sprite = new Sprite(TextureRepository.instance().getTexture(levelSprite.getTextureName()));
		sprite.setPosition(levelSprite.getPosition().x, levelSprite.getPosition().y);
		sprite.setScale(levelSprite.getScale().x, levelSprite.getScale().y);
		sprite.setRotation(levelSprite.getRotation());
		return sprite;
	}

	private static final class SpriteZOrderComparator implements Comparator<LevelSprite> {
		@Override
		public int compare(LevelSprite lhs, LevelSprite rhs) {
			return lhs.getZOrder() - rhs.getZOrder();
		}
	}
}
