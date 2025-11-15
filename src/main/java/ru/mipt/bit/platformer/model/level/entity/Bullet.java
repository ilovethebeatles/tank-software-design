package ru.mipt.bit.platformer.model.level.entity;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.graphics.Field;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameUnit;
import ru.mipt.bit.platformer.model.impl.Tank;
import ru.mipt.bit.platformer.model.level.LevelModel;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Bullet implements GameUnit {
    public static final float CELL_TIME = 0.1f;
    public static final int DEFAULT_DAMAGE = 25;

    private GridPoint2 pos;
    private GridPoint2 next;
    private float progress = 1f;
    private float rotation;
    private final Direction dir;

    public Bullet(GridPoint2 startCell, Direction dir) {
        this.pos = new GridPoint2(startCell);
        this.dir = dir;
        this.next = new GridPoint2(startCell).add(dir.getVector());
        this.rotation = dir.getRotation();
    }

    @Override
    public GridPoint2 getCoordinates() { return new GridPoint2(pos); }

    @Override
    public float getRotation() { return rotation; }

    @Override
    public void setRotation(float rotation) { this.rotation = rotation; }

    public GridPoint2 getNext() { return new GridPoint2(next); }

    public float getProgress() { return progress; }

    public void update(float dt, LevelModel level, Field field) {
        progress = continueProgress(progress, dt, CELL_TIME);
        if (progress < 1f) return;
        pos.set(next);
        if (field.isCellBlocked(pos)) {
            level.removeBullet(this);
            return;
        }
        Tank hit = level.findTankAt(pos);
        if (hit != null) {
            level.damageTank(hit, DEFAULT_DAMAGE);
            level.removeBullet(this);
            return;
        }
        next = new GridPoint2(pos).add(dir.getVector());
        progress = 0f;
    }
}
