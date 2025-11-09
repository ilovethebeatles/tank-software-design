package ru.mipt.bit.platformer.ai;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.graphics.Field;
import ru.mipt.bit.platformer.model.impl.Tank;
import ru.mipt.bit.platformer.tracker.OccupancyTracker;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

class AITankControllerTest {
    private Field field;

    @BeforeEach
    void setup() {
        TiledMap map = new TiledMap();
        TiledMapTileLayer layer = new TiledMapTileLayer(3, 3, 128, 128);
        map.getLayers().add(layer);
        field = new Field(map, layer);
    }

    @Test
    void aiChoosesSomeValidDirectionWhenAvailable() {
        Tank t = new Tank(new GridPoint2(1, 1));
        AiTankRuler ai = new AiTankRuler(t, field);
        ai.tick(new OccupancyTracker(Collections.singletonList(t)));
        assertTrue(t.isMoving());
    }

    @Test
    void aiStaysWhenNoMoves() {
        Tank tAI = new Tank(new GridPoint2(0, 0));
        Tank blocker1 = new Tank(new GridPoint2(1, 0));
        Tank blocker2 = new Tank(new GridPoint2(0, 1));
        AiTankRuler ai = new AiTankRuler(tAI, field);
        OccupancyTracker occ = new OccupancyTracker(java.util.Arrays.asList(tAI, blocker1, blocker2));
        ai.tick(occ);
        assertFalse(tAI.isMoving());
    }
}

