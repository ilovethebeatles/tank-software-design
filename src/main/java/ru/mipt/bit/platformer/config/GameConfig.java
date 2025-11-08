package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.math.GridPoint2;

import java.io.IOException;
import java.util.*;

public class GameConfig {
    private final GridPoint2 playerStartPosition;
    private final List<GridPoint2> obstaclePositions;

    public GameConfig() {
        this.playerStartPosition = new GridPoint2(1, 1);
        this.obstaclePositions = Arrays.asList(
                new GridPoint2(1, 3)
        );
    }

    public GameConfig(float height, float width, int obstaclesNum) {
        Random random = new Random();
        this.playerStartPosition = new GridPoint2(random.nextInt((int) width), random.nextInt((int) height));
        this.obstaclePositions = new ArrayList<>(obstaclesNum);
        for (int i = 0; i < obstaclesNum; ++i) {
            GridPoint2 obstacle = new GridPoint2(0, 0);
            for(int j = 0; j < i; ++j) {
                while(obstacle.x == obstaclePositions.get(j).x && obstacle.y == obstaclePositions.get(j).y || obstacle.x == playerStartPosition.x && obstacle.y == playerStartPosition.y) {
                    obstacle.set(random.nextInt((int) width), random.nextInt((int) height));
                }
            }
            obstaclePositions.add(obstacle);
        }
    }

    public GameConfig(String pathToLevelFile) throws IOException {
        LevelData levelData = LevelFileParser.parseLevel("level_schema.txt");
        playerStartPosition = levelData.getPlayerStartPosition();
        obstaclePositions = levelData.getObstaclePositions();
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
