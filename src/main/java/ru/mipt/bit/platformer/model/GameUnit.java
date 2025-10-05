package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameUnit {
    protected GridPoint2 coordinates;
    protected Rectangle bounds;
    protected TextureRegion textureRegion;
    protected float rotation;

    public GameUnit(GridPoint2 coordinates, TextureRegion textureRegion) {
        this.coordinates = new GridPoint2(coordinates);
        this.textureRegion = textureRegion;
        this.bounds = new Rectangle();
        this.bounds.setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        this.rotation = 0f;
    }

    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }

    public Rectangle getBounds() {
        return new Rectangle(bounds);
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public float getRotation() {
        return rotation;
    }

    public abstract void update(float deltaTime);
}
