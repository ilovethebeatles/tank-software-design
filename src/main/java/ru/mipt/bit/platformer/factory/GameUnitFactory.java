package ru.mipt.bit.platformer.factory;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.GameUnit;
import ru.mipt.bit.platformer.model.Obstacle;

public interface GameUnitFactory {
    GameUnit createTank(GridPoint2 position);
    Obstacle createTree(GridPoint2 position);
}
