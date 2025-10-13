package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameConfig {
    private final GridPoint2 playerStartPosition;
    private final List<GridPoint2> obstaclePositions;

    public GameConfig() {
        this.playerStartPosition = new GridPoint2(1, 1);
        this.obstaclePositions = Arrays.asList(
                new GridPoint2(1, 3)
        );
    }

    public GameConfig(GridPoint2 playerStartPosition, List<GridPoint2> obstaclePositions) {
        this.playerStartPosition = new GridPoint2(playerStartPosition);
        this.obstaclePositions = Collections.unmodifiableList(obstaclePositions);
    }

    public GridPoint2 getPlayerStartPosition() {
        return new GridPoint2(playerStartPosition);
    }

    public List<GridPoint2> getObstaclePositions() {
        return Collections.unmodifiableList(obstaclePositions);
    }
}
