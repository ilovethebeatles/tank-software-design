package ru.mipt.bit.platformer.graphics.impl;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.graphics.GameUnitGraphics;
import ru.mipt.bit.platformer.model.GameUnit;
import ru.mipt.bit.platformer.model.impl.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class TankGraphics implements GameUnitGraphics {
    private final TextureRegion textureRegion;
    private final Rectangle bounds;
    private float rotation;
    private final TileMovement tileMovement;
    private final TiledMapTileLayer groundLayer;
    private final Tank tank;

    public TankGraphics(TextureRegion textureRegion, TileMovement tileMovement,
                        TiledMapTileLayer groundLayer, GameUnit tank) {
        this.textureRegion = textureRegion;
        this.tileMovement = tileMovement;
        this.groundLayer = groundLayer;
        if (!(tank instanceof Tank)) {
            throw new IllegalArgumentException("TankGraphics requires Tank instance");
        }
        this.tank = (Tank) tank;
        this.bounds = new Rectangle();
        this.bounds.setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        this.rotation = 0f;
        update(0f);
    }

    @Override
    public void update(float deltaTime) {
        if (tank.isMoving()) {
            tileMovement.moveRectangleBetweenTileCenters(bounds,
                    tank.getCoordinates(), tank.getDestinationCoordinates(),
                    tank.getMovementProgress());
        } else {
            moveRectangleAtTileCenter(groundLayer, bounds, tank.getCoordinates());
        }
        rotation = tank.getRotation();
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
