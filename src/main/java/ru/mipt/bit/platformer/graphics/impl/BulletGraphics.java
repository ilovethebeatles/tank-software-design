package ru.mipt.bit.platformer.graphics.impl;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.graphics.GameUnitGraphics;
import ru.mipt.bit.platformer.model.level.entity.Bullet;
import ru.mipt.bit.platformer.util.TileMovement;

public class BulletGraphics implements GameUnitGraphics {
    private final TextureRegion textureRegion;
    private final Rectangle bounds = new Rectangle();
    private final TiledMapTileLayer layer;
    private final TileMovement movement;
    private final Bullet bullet;
    private float rotation;

    public BulletGraphics(TextureRegion textureRegion, TileMovement movement,
                          TiledMapTileLayer layer, Bullet bullet) {
        this.textureRegion = textureRegion;
        this.movement = movement;
        this.layer = layer;
        this.bullet = bullet;

        bounds.setSize(textureRegion.getRegionWidth()/2f, textureRegion.getRegionHeight()/2f);
        update(0f);
    }

    @Override
    public TextureRegion getTextureRegion() { return textureRegion; }

    @Override
    public Rectangle getBounds() { return new Rectangle(bounds); }

    @Override
    public float getRotation() { return rotation; }

    @Override
    public void update(float deltaTime) {
        rotation = bullet.getRotation();
        movement.moveRectangleBetweenTileCenters(bounds, bullet.getCoordinates(), bullet.getNext(), bullet.getProgress());
        bounds.setWidth(textureRegion.getRegionWidth()/2f);
        bounds.setHeight(textureRegion.getRegionHeight()/2f);
    }
}

