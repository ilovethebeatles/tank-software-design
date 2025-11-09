package ru.mipt.bit.platformer.command.impl;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.graphics.Field;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.impl.Tank;
import ru.mipt.bit.platformer.tracker.OccupancyTracker;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class MoveCommandTest {
    private Field field;

    @BeforeEach
    void setup() {
        TiledMap map = new TiledMap();
        TiledMapTileLayer layer = new TiledMapTileLayer(4, 4, 128, 128);
        map.getLayers().add(layer);
        field = new Field(map, layer);
    }

    @Test
    void executesWhenFree() {
        Tank t = new Tank(new GridPoint2(1, 1));
        OccupancyTracker occ = new OccupancyTracker(Collections.singletonList(t));
        MoveCommand cmd = new MoveCommand(t, Direction.RIGHT, field, occ);
        cmd.execute();
        assertTrue(t.isMoving());
        assertEquals(new GridPoint2(2,1), t.getDestinationCoordinates());
    }

    @Test
    void deniedWhenTargetOccupiedByOther() {
        Tank t1 = new Tank(new GridPoint2(1, 1));
        Tank t2 = new Tank(new GridPoint2(2, 1));
        OccupancyTracker occ = new OccupancyTracker(Arrays.asList(t1, t2));
        MoveCommand cmd = new MoveCommand(t1, Direction.RIGHT, field, occ);
        cmd.execute();
        assertFalse(t1.isMoving());
    }

    @Test
    void deniedWhenOutOfBounds() {
        Tank t = new Tank(new GridPoint2(3, 3));
        OccupancyTracker occ = new OccupancyTracker(Collections.singletonList(t));
        MoveCommand cmd = new MoveCommand(t, Direction.RIGHT, field, occ);
        cmd.execute();
        assertFalse(t.isMoving());
    }
}

