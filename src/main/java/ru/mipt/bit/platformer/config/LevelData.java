package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.math.GridPoint2;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class LevelData {
    private GridPoint2 playerStartPosition;
    private List<GridPoint2> obstaclePositions;

    public LevelData(GridPoint2 playerStart, List<GridPoint2> obstacles) {
        playerStartPosition = playerStart;
        obstaclePositions = obstacles;
    }
}
