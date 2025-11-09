package ru.mipt.bit.platformer.model.impl;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameUnit;

public class Tank implements GameUnit {
    public static final float MOVEMENT_SPEED = 0.4f;

    private final GridPoint2 coordinates;
    private float rotation;
    private GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private int maxHp = 100;
    private int hp = 100;

    public Tank(GridPoint2 coordinates) {
        this.coordinates = new GridPoint2(coordinates);
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.rotation = 0f;
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

    @Override
    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    @Override
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public GridPoint2 getDestinationCoordinates() {
        return new GridPoint2(destinationCoordinates);
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, Math.min(hp, maxHp));
    }

    public float getHpRatio() {
        return (float) hp / (float) maxHp;
    }
}