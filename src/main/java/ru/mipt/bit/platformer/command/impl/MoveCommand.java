package ru.mipt.bit.platformer.command.impl;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.command.Command;
import ru.mipt.bit.platformer.graphics.Field;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.impl.Tank;
import ru.mipt.bit.platformer.tracker.OccupancyTracker;

public class MoveCommand implements Command {
    private final Tank tank;
    private final Direction direction;
    private final Field field;
    private final OccupancyTracker occupancy;

    public MoveCommand(Tank tank, Direction direction, Field field, OccupancyTracker occupancy) {
        this.tank = tank;
        this.direction = direction;
        this.field = field;
        this.occupancy = occupancy;
    }

    @Override
    public void execute() {
        if (tank.isMoving()) return;
        GridPoint2 target = new GridPoint2(tank.getCoordinates()).add(direction.getVector());
        if (field.isCellBlocked(target)) return;
        if (occupancy.isOccupied(target)) return;
        tank.moveTo(direction);
        occupancy.reserve(target);
    }
}

