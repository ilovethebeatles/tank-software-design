package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameUnitGraphics {
    protected TextureRegion textureRegion;
    protected Rectangle bounds;
    protected float rotation;

    public GameUnitGraphics(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
        this.bounds = new Rectangle();
        this.bounds.setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        this.rotation = 0f;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public Rectangle getBounds() {
        return new Rectangle(bounds);
    }

    public float getRotation() {
        return rotation;
    }

    public abstract void update(float deltaTime);
}
