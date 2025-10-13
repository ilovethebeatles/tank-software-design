package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DirectionTest {
    @Test
    void getVector_shouldReturnCorrectVector() {
        assertEquals(new GridPoint2(0, 1), Direction.UP.getVector());
        assertEquals(new GridPoint2(0, -1), Direction.DOWN.getVector());
        assertEquals(new GridPoint2(1, 0), Direction.RIGHT.getVector());
        assertEquals(new GridPoint2(-1, 0), Direction.LEFT.getVector());
    }

    @Test
    void fromKeys_shouldReturnCorrectDirection() {
        assertEquals(Direction.UP, Direction.fromKeys(true, false, false, false));
        assertEquals(Direction.DOWN, Direction.fromKeys(false, true, false, false));
        assertEquals(Direction.LEFT, Direction.fromKeys(false, false, true, false));
        assertEquals(Direction.RIGHT, Direction.fromKeys(false, false, false, true));
        assertNull(Direction.fromKeys(false, false, false, false));
    }
}