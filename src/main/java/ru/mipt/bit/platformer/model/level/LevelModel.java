package ru.mipt.bit.platformer.model.level;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.graphics.Field;
import ru.mipt.bit.platformer.model.GameUnit;
import ru.mipt.bit.platformer.model.impl.Tank;
import ru.mipt.bit.platformer.model.level.entity.Bullet;

import java.util.*;

public class LevelModel {
    private final Field field;
    private final List<Tank> tanks = new ArrayList<>();
    private final List<Bullet> bullets = new ArrayList<>();
    private final List<LevelObserver> observers = new ArrayList<>();

    public LevelModel(Field field) {
        this.field = field;
    }

    public void addObserver(LevelObserver o) { observers.add(o); }
    public void removeObserver(LevelObserver o) { observers.remove(o); }

    public void addTank(Tank t) {
        tanks.add(t);
        notifyAdded(t);
    }

    public void removeTank(Tank t) {
        tanks.remove(t);
        notifyRemoved(t);
    }

    public void addBullet(Bullet b) {
        bullets.add(b);
        notifyAdded(b);
    }

    public void removeBullet(Bullet b) {
        bullets.remove(b);
        notifyRemoved(b);
    }

    public List<Tank> getTanks() { return Collections.unmodifiableList(tanks); }
    public List<Bullet> getBullets() { return Collections.unmodifiableList(bullets); }

    public Tank findTankAt(GridPoint2 cell) {
        for (Tank t : tanks) {
            if (t.getCoordinates().equals(cell)) return t;
        }
        return null;
    }

    public void damageTank(Tank t, int dmg) {
        t.setHp(t.getHp() - dmg);
        if (t.getHp() <= 0) {
            removeTank(t);
        }
    }

    public void tick(float deltaTime) {
        for (Tank t : new ArrayList<>(tanks)) {
            t.updateMovementProgress(deltaTime);
        }
        for (Bullet b : new ArrayList<>(bullets)) {
            b.update(deltaTime, this, field);
        }
    }

    private void notifyAdded(GameUnit obj) {
        for (LevelObserver o : observers) o.onObjectAdded(obj);
    }

    private void notifyRemoved(GameUnit obj) {
        for (LevelObserver o : observers) o.onObjectRemoved(obj);
    }
}

