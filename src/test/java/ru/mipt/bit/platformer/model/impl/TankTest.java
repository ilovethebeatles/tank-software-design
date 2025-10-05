package ru.mipt.bit.platformer.model.impl;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.model.Direction;

import static org.junit.jupiter.api.Assertions.*;

class TankTest {
    private Tank tank;

    @BeforeEach
    void setUp() {
        tank = new Tank(new GridPoint2(1, 1));
    }

    @Test
    void moveTo_shouldResetProgressAndStartMoving() {
        assertFalse(tank.isMoving());
        assertEquals(1f, tank.getMovementProgress());
        tank.moveTo(Direction.UP);
        assertEquals(0f, tank.getMovementProgress());
        assertTrue(tank.isMoving());
    }

    @Test
    void updateMovementProgress_shouldIncreaseProgress() {
        tank.moveTo(Direction.UP);
        float initialProgress = tank.getMovementProgress();
        tank.updateMovementProgress(0.1f);
        assertTrue(tank.getMovementProgress() > initialProgress);
        assertTrue(tank.isMoving());
    }

    @Test
    void updateMovementProgress_shouldCompleteWhenEnoughTimePassed() {
        tank.moveTo(Direction.UP);
        tank.updateMovementProgress(Tank.MOVEMENT_SPEED);
        assertEquals(1f, tank.getMovementProgress());
        assertFalse(tank.isMoving());
        assertEquals(new GridPoint2(1, 2), tank.getCoordinates());
    }
}
