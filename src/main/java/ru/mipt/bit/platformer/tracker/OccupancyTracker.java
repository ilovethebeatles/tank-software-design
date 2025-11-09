package ru.mipt.bit.platformer.tracker;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.impl.Tank;

import java.util.*;

public class OccupancyTracker {
    private final Set<GridPoint2> occupied = new HashSet<>();

    public OccupancyTracker(Collection<Tank> tanks) {
        for (Tank t : tanks) {
            occupied.add(t.getCoordinates());
            if (t.isMoving()) {
                occupied.add(t.getDestinationCoordinates());
            }
        }
    }

    public boolean isOccupied(GridPoint2 cell) {
        for (GridPoint2 p : occupied) {
            if (p.equals(cell)) return true;
        }
        return false;
    }

    public void reserve(GridPoint2 cell) {
        occupied.add(new GridPoint2(cell));
    }
}

