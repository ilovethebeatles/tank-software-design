package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class Tank extends GameUnit {
    private static final float MOVEMENT_SPEED = 0.4f;

    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private final TileMovement tileMovement;
    private final TiledMapTileLayer groundLayer;

    public Tank(GridPoint2 coordinates, TextureRegion textureRegion, TileMovement tileMovement, TiledMapTileLayer groundLayer) {
        super(coordinates, textureRegion);
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.tileMovement = tileMovement;
        this.groundLayer = groundLayer;
        moveRectangleAtTileCenter(groundLayer, bounds, coordinates);
    }

    public void moveTo(Direction direction) {
        if (movementProgress < 1f) return;

        GridPoint2 newDestination = new GridPoint2(coordinates).add(direction.getVector());
        destinationCoordinates = newDestination;
        movementProgress = 0f;
        rotation = direction.getRotation();
    }

    public boolean isMoving() {
        return movementProgress < 1f;
    }

    public GridPoint2 getDestinationCoordinates() {
        return new GridPoint2(destinationCoordinates);
    }

    @Override
    public void update(float deltaTime) {
        if (isMoving()) {
            movementProgress = Math.min(movementProgress + deltaTime / MOVEMENT_SPEED, 1f);
            tileMovement.moveRectangleBetweenTileCenters(bounds, coordinates, destinationCoordinates, movementProgress);

            if (movementProgress >= 1f) {
                coordinates.set(destinationCoordinates);
                moveRectangleAtTileCenter(groundLayer, bounds, coordinates);
            }
        }
    }
}