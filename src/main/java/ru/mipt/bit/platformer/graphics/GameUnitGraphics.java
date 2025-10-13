package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public interface GameUnitGraphics {
    TextureRegion getTextureRegion();
    Rectangle getBounds();
    float getRotation();
    void update(float deltaTime);
}
