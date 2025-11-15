package ru.mipt.bit.platformer.ai;

import ru.mipt.bit.platformer.command.impl.FireCommand;
import ru.mipt.bit.platformer.command.impl.MoveCommand;
import ru.mipt.bit.platformer.graphics.Field;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.impl.Tank;
import ru.mipt.bit.platformer.model.level.LevelModel;
import ru.mipt.bit.platformer.tracker.OccupancyTracker;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

public class AiTankRuler {
    private final Tank tank;
    private final Field field;
    private final Random rnd = new Random();
    private final LevelModel level;

    public AiTankRuler(Tank tank, Field field, LevelModel level) {
        this.tank = tank;
        this.field = field;
        this.level = level;
    }

    public void tick(OccupancyTracker occupancy) {
        if (tank.isMoving()) return;
        if (rnd.nextFloat() < 0.2f) {
            new FireCommand(tank, level, field).execute();
            if (tank.isMoving()) return;
            return;
        }
        List<Direction> dirs = Arrays.asList(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
        Collections.shuffle(dirs, rnd);
        for (Direction d : dirs) {
            MoveCommand cmd = new MoveCommand(tank, d, field, occupancy);
            cmd.execute();
            if (tank.isMoving()) return;
        }
    }
}

