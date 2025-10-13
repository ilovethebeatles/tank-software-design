package ru.mipt.bit.platformer.graphics.impl;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.graphics.GameUnitGraphics;
import ru.mipt.bit.platformer.model.Obstacle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class TreeGraphics implements GameUnitGraphics {
    private final TextureRegion textureRegion;
    private final Rectangle bounds;
    private float rotation;
    private final TiledMapTileLayer groundLayer;
    private final Obstacle obstacle;

    public TreeGraphics(TextureRegion textureRegion, TiledMapTileLayer groundLayer, Obstacle obstacle) {
        this.textureRegion = textureRegion;
        this.groundLayer = groundLayer;
        this.obstacle = obstacle;

        this.bounds = new Rectangle();
        this.bounds.setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        this.rotation = 0f;
        update(0f);
    }

    @Override
    public void update(float deltaTime) {
        moveRectangleAtTileCenter(groundLayer, bounds, obstacle.getCoordinates());
    }

    @Override
    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(bounds);
    }

    @Override
    public float getRotation() {
        return rotation;
    }
}