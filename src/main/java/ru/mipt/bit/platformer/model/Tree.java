package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class Tree extends GameUnit {
    private final TiledMapTileLayer groundLayer;

    public Tree(GridPoint2 coordinates, TextureRegion textureRegion, TiledMapTileLayer groundLayer) {
        super(coordinates, textureRegion);
        this.groundLayer = groundLayer;
        moveRectangleAtTileCenter(groundLayer, bounds, coordinates);
    }

    @Override
    public void update(float deltaTime) {
        moveRectangleAtTileCenter(groundLayer, bounds, coordinates);
    }
}