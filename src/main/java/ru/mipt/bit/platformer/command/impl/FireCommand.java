package ru.mipt.bit.platformer.command.impl;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.command.Command;
import ru.mipt.bit.platformer.graphics.Field;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.impl.Tank;
import ru.mipt.bit.platformer.model.level.LevelModel;
import ru.mipt.bit.platformer.model.level.entity.Bullet;

public class FireCommand implements Command {
    private final Tank shooter;
    private final LevelModel level;
    private final Field field;

    public FireCommand(Tank shooter, LevelModel level, Field field) {
        this.shooter = shooter;
        this.level = level;
        this.field = field;
    }

    @Override
    public void execute() {
        Direction dir = directionFromRotation(shooter.getRotation());
        if (dir == null) return;

        GridPoint2 spawn = new GridPoint2(shooter.getCoordinates()).add(dir.getVector());
        if (field.isCellBlocked(spawn)) {
            Tank hit = level.findTankAt(spawn);
            if (hit != null) {
                level.damageTank(hit, Bullet.DEFAULT_DAMAGE);
            }
            return;
        }
        Tank target = level.findTankAt(spawn);
        if (target != null) {
            level.damageTank(target, Bullet.DEFAULT_DAMAGE);
            return;
        }
        Bullet bullet = new Bullet(spawn, dir);
        level.addBullet(bullet);
    }

    private Direction directionFromRotation(float rotation) {
        float r = ((rotation % 360f) + 360f) % 360f;
        if (approx(r, 0f))   return Direction.RIGHT;
        if (approx(r, 90f))  return Direction.UP;
        if (approx(r, 180f) || approx(r, -180f)) return Direction.LEFT;
        if (approx(r, 270f) || approx(r, -90f))  return Direction.DOWN;
        return null;
    }

    private boolean approx(float a, float b) {
        return Math.abs(a - b) < 1e-3;
    }
}

