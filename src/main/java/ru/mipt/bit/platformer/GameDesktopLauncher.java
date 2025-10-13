package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.config.GameConfig;
import ru.mipt.bit.platformer.factory.GameUnitFactory;
import ru.mipt.bit.platformer.factory.GraphicsFactory;
import ru.mipt.bit.platformer.factory.impl.DefaultGameUnitFactory;
import ru.mipt.bit.platformer.factory.impl.DefaultGraphicsFactory;
import ru.mipt.bit.platformer.graphics.Field;
import ru.mipt.bit.platformer.graphics.GameUnitGraphics;
import ru.mipt.bit.platformer.handler.InputHandler;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameUnit;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.impl.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private final GameConfig config;
    private final GameUnitFactory unitFactory;
    private final GraphicsFactory graphicsFactory;

    private Batch batch;
    private Field field;
    private MapRenderer mapRenderer;
    private TileMovement tileMovement;
    private InputHandler inputHandler;

    private GameUnit player;
    private List<Obstacle> obstacles;
    private List<GameUnitGraphics> graphics;

    public GameDesktopLauncher() {
        this.config = new GameConfig();
        this.unitFactory = new DefaultGameUnitFactory();
        this.graphicsFactory = new DefaultGraphicsFactory();
    }

    public GameDesktopLauncher(GameConfig config, GameUnitFactory unitFactory,
                               GraphicsFactory graphicsFactory) {
        this.config = config;
        this.unitFactory = unitFactory;
        this.graphicsFactory = graphicsFactory;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        TiledMap tiledMap = new TmxMapLoader().load("level.tmx");
        TiledMapTileLayer groundLayer = getSingleLayer(tiledMap);
        mapRenderer = createSingleLayerMapRenderer(tiledMap, batch);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        field = new Field(tiledMap, groundLayer);
        inputHandler = new InputHandler();
        player = unitFactory.createTank(config.getPlayerStartPosition());
        obstacles = new ArrayList<>();
        for (GridPoint2 position : config.getObstaclePositions()) {
            Obstacle obstacle = unitFactory.createTree(position);
            obstacles.add(obstacle);
            field.addObstacle(obstacle);
        }
        graphics = new ArrayList<>();
        Texture blueTankTexture = new Texture("images/tank_blue.png");
        Texture greenTreeTexture = new Texture("images/greenTree.png");

        graphics.add(graphicsFactory.createTankGraphics(
                new TextureRegion(blueTankTexture), tileMovement, groundLayer, player));

        for (Obstacle obstacle : obstacles) {
            graphics.add(graphicsFactory.createTreeGraphics(
                    new TextureRegion(greenTreeTexture), groundLayer, obstacle));
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        float deltaTime = Gdx.graphics.getDeltaTime();

        handleInput();
        updateGameState(deltaTime);
        renderGame();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    private void handleInput() {
        if (player instanceof Tank) {
            Tank tank = (Tank) player;
            if (tank.isMoving()) return;
            inputHandler.update();
            Direction direction = inputHandler.getLastDirection();
            if (direction != null) {
                GridPoint2 newPosition = new GridPoint2(player.getCoordinates()).add(direction.getVector());
                if (!field.isCellBlocked(newPosition)) {
                    tank.moveTo(direction);
                }
            }
        }
    }

    private void updateGameState(float deltaTime) {
        if (player instanceof Tank) {
            Tank tank = (Tank) player;
            tank.updateMovementProgress(deltaTime);
        }
        for (GameUnitGraphics graphic : graphics) {
            graphic.update(deltaTime);
        }
    }

    private void renderGame() {
        mapRenderer.render();
        batch.begin();
        for (GameUnitGraphics graphic : graphics) {
            drawTextureRegionUnscaled(batch, graphic.getTextureRegion(),
                    graphic.getBounds(), graphic.getRotation());
        }
        batch.end();
    }

    @Override
    public void dispose() {
        for (GameUnitGraphics graphic : graphics) {
            if (graphic.getTextureRegion() != null) {
                graphic.getTextureRegion().getTexture().dispose();
            }
        }
        if (field != null && field.getTiledMap() != null) {
            field.getTiledMap().dispose();
        }
        if (batch != null) {
            batch.dispose();
        }
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}