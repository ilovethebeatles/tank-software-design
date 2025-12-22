package ru.mipt.bit.platformer.tracker;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.impl.Tank;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class OccupancyTrackerTest {

    @Test
    void movingTankOccupiesBothCells() {
        Tank t = new Tank(new GridPoint2(2, 2));
        t.moveTo(Direction.RIGHT);
        OccupancyTracker occ = new OccupancyTracker(Arrays.asList(t));
        assertTrue(occ.isOccupied(new GridPoint2(2, 2)));
        assertTrue(occ.isOccupied(new GridPoint2(3, 2)));
    }

    @Test
    void standingTankOccupiesOnlyCurrentCell() {
        Tank t = new Tank(new GridPoint2(1, 1));
        OccupancyTracker occ = new OccupancyTracker(Arrays.asList(t));
        assertTrue(occ.isOccupied(new GridPoint2(1, 1)));
        assertFalse(occ.isOccupied(new GridPoint2(2, 1)));
    }
}

