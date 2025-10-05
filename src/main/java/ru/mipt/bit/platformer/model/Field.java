package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private final TiledMap tiledMap;
    private final TiledMapTileLayer groundLayer;
    private final List<GameUnit> obstacles;
    private final int width;
    private final int height;

    public Field(TiledMap tiledMap, TiledMapTileLayer groundLayer) {
        this.tiledMap = tiledMap;
        this.groundLayer = groundLayer;
        this.obstacles = new ArrayList<>();
        this.width = groundLayer.getWidth();
        this.height = groundLayer.getHeight();
    }

    public void addObstacle(GameUnit obstacle) {
        obstacles.add(obstacle);
    }

    public boolean isCellBlocked(GridPoint2 coordinates) {
        if (coordinates.x < 0 || coordinates.x >= width || coordinates.y < 0 || coordinates.y >= height) {
            return true;
        }

        for (GameUnit obstacle : obstacles) {
            if (obstacle.getCoordinates().equals(coordinates)) {
                return true;
            }
        }

        return false;
    }

    public List<GameUnit> getObstacles() {
        return new ArrayList<>(obstacles);
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public TiledMapTileLayer getGroundLayer() {
        return groundLayer;
    }
}
