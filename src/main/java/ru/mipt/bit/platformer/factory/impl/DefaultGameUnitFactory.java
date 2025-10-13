package ru.mipt.bit.platformer.factory.impl;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.factory.GameUnitFactory;
import ru.mipt.bit.platformer.model.GameUnit;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.impl.Tank;
import ru.mipt.bit.platformer.model.impl.Tree;

public class DefaultGameUnitFactory implements GameUnitFactory {
    @Override
    public GameUnit createTank(GridPoint2 position) {
        return new Tank(position);
    }

    @Override
    public Obstacle createTree(GridPoint2 position) {
        return new Tree(position);
    }
}
