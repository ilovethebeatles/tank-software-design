package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public abstract class GameUnit {
    protected GridPoint2 coordinates;
    protected float rotation;

    public GameUnit(GridPoint2 coordinates) {
        this.coordinates = new GridPoint2(coordinates);
        this.rotation = 0f;
    }

    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
