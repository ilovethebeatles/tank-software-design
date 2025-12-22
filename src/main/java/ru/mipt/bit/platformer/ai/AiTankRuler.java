package ru.mipt.bit.platformer.ai;

import ru.mipt.bit.platformer.command.impl.MoveCommand;
import ru.mipt.bit.platformer.graphics.Field;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.impl.Tank;
import ru.mipt.bit.platformer.tracker.OccupancyTracker;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

public class AiTankRuler {
    private final Tank tank;
    private final Field field;
    private final Random rnd = new Random();

    public AiTankRuler(Tank tank, Field field) {
        this.tank = tank;
        this.field = field;
    }

    public void tick(OccupancyTracker occupancy) {
        if (tank.isMoving()) return;
        List<Direction> dirs = Arrays.asList(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
        Collections.shuffle(dirs, rnd);
        for (Direction d : dirs) {
            MoveCommand cmd = new MoveCommand(tank, d, field, occupancy);
            cmd.execute();
            if (tank.isMoving()) return;
        }
    }
}

