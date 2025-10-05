package ru.mipt.bit.platformer.graphics.impl;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.graphics.GameUnitGraphics;
import ru.mipt.bit.platformer.model.impl.Tree;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class TreeGraphics extends GameUnitGraphics {
    private final TiledMapTileLayer groundLayer;
    private final Tree tree;

    public TreeGraphics(TextureRegion textureRegion, TiledMapTileLayer groundLayer, Tree tree) {
        super(textureRegion);
        this.groundLayer = groundLayer;
        this.tree = tree;
        update(0f);
    }

    @Override
    public void update(float deltaTime) {
        moveRectangleAtTileCenter(groundLayer, bounds, tree.getCoordinates());
    }
}
