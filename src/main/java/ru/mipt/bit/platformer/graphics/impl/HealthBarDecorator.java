package ru.mipt.bit.platformer.graphics.impl;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.graphics.GameUnitGraphics;
import ru.mipt.bit.platformer.graphics.OverlayRenderable;
import ru.mipt.bit.platformer.model.impl.Tank;

public class HealthBarDecorator implements GameUnitGraphics, OverlayRenderable {
    private final GameUnitGraphics inner;
    private final Tank tank;

    public HealthBarDecorator(GameUnitGraphics inner, Tank tank) {
        this.inner = inner;
        this.tank = tank;
    }

    @Override
    public void update(float deltaTime) { inner.update(deltaTime); }

    @Override
    public TextureRegion getTextureRegion() { return inner.getTextureRegion(); }

    @Override
    public Rectangle getBounds() { return inner.getBounds(); }

    @Override
    public float getRotation() { return inner.getRotation(); }

    @Override
    public void renderOverlay(ShapeRenderer shapeRenderer) {
        Rectangle b = getBounds();
        float barWidth = b.getWidth() * 0.8f;
        float barHeight = b.getHeight() * 0.10f;
        float x = b.x + (b.getWidth() - barWidth) / 2f;
        float y = b.y + b.getHeight() + b.getHeight() * 0.05f;
        float hpRatio = Math.max(0f, Math.min(1f, tank.getHpRatio()));
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f, 0f, 0f, 1f);
        shapeRenderer.rect(x, y, barWidth, barHeight);
        shapeRenderer.setColor(0f, 1f, 0f, 1f);
        shapeRenderer.rect(x, y, barWidth * hpRatio, barHeight);
        shapeRenderer.end();
    }
}

