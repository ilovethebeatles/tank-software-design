package ru.mipt.bit.platformer.model.impl;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameUnit;

public class Tank extends GameUnit {
    private static final float MOVEMENT_SPEED = 0.4f;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;

    public Tank(GridPoint2 coordinates) {
        super(coordinates);
        this.destinationCoordinates = new GridPoint2(coordinates);
    }

    public void moveTo(Direction direction) {
        if (isMoving()) return;
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

    public float getMovementProgress() {
        return movementProgress;
    }

    public void updateMovementProgress(float deltaTime) {
        if (isMoving()) {
            movementProgress = Math.min(movementProgress + deltaTime / MOVEMENT_SPEED, 1f);
            if (movementProgress >= 1f) {
                coordinates.set(destinationCoordinates);
            }
        }
    }
}