package ru.mipt.bit.platformer.graphics.impl;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.graphics.GameUnitGraphics;
import ru.mipt.bit.platformer.model.impl.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class TankGraphics extends GameUnitGraphics {
    private final TileMovement tileMovement;
    private final TiledMapTileLayer groundLayer;
    private final Tank tank;

    public TankGraphics(TextureRegion textureRegion, TileMovement tileMovement,
                        TiledMapTileLayer groundLayer, Tank tank) {
        super(textureRegion);
        this.tileMovement = tileMovement;
        this.groundLayer = groundLayer;
        this.tank = tank;
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
}
