package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.mipt.bit.platformer.ai.AiTankRuler;
import ru.mipt.bit.platformer.command.impl.FireCommand;
import ru.mipt.bit.platformer.command.impl.MoveCommand;
import ru.mipt.bit.platformer.command.impl.ToggleHealthBarsCommand;
import ru.mipt.bit.platformer.config.GameConfig;
import ru.mipt.bit.platformer.config.SpringConfig;
import ru.mipt.bit.platformer.factory.GameUnitFactory;
import ru.mipt.bit.platformer.factory.GraphicsFactory;
import ru.mipt.bit.platformer.graphics.Field;
import ru.mipt.bit.platformer.graphics.GameUnitGraphics;
import ru.mipt.bit.platformer.graphics.OverlayRenderable;
import ru.mipt.bit.platformer.graphics.impl.BulletGraphics;
import ru.mipt.bit.platformer.graphics.impl.HealthBarDecorator;
import ru.mipt.bit.platformer.handler.InputHandler;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameUnit;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.impl.Tank;
import ru.mipt.bit.platformer.model.level.LevelModel;
import ru.mipt.bit.platformer.model.level.LevelObserver;
import ru.mipt.bit.platformer.model.level.entity.Bullet;
import ru.mipt.bit.platformer.tracker.OccupancyTracker;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.*;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private final GameUnitFactory unitFactory;
    private final GraphicsFactory graphicsFactory;

    private GameConfig config;
    private Batch batch;
    private Field field;
    private MapRenderer mapRenderer;
    private TileMovement tileMovement;
    private final InputHandler inputHandler;
    private GameUnit player;
    private final List<Tank> tanks = new ArrayList<>();
    private final List<AiTankRuler> aiControllers = new ArrayList<>();
    private List<Obstacle> obstacles;
    private List<GameUnitGraphics> graphics;
    private final Map<GameUnit, GameUnitGraphics> graphicsByUnit = new HashMap<>();

    private int aiCount = 3;
    private ShapeRenderer shapeRenderer;
    private boolean showHealthBars = false;
    private LevelModel level;

    public GameDesktopLauncher(GameUnitFactory unitFactory, GraphicsFactory graphicsFactory, InputHandler inputHandler) {
        this.unitFactory = unitFactory;
        this.graphicsFactory = graphicsFactory;
        this.inputHandler = inputHandler;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        TiledMap tiledMap = new TmxMapLoader().load("level.tmx");
        TiledMapTileLayer groundLayer = getSingleLayer(tiledMap);
        mapRenderer = createSingleLayerMapRenderer(tiledMap, batch);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        config = new GameConfig(groundLayer.getHeight(), groundLayer.getWidth(), 3);
        field = new Field(tiledMap, groundLayer);
        graphics = new ArrayList<>();
        level = new LevelModel(field);
        level.addObserver(new LevelObserver() {
            @Override
            public void onObjectAdded(GameUnit obj) {
                if (obj instanceof Bullet) {
                    TextureRegion bulletTex = new TextureRegion(new Texture("images/brick.png"), 15, 15);
                    GameUnitGraphics bulletGfx =
                            new BulletGraphics(bulletTex, tileMovement, groundLayer, (Bullet) obj);
                    graphics.add(bulletGfx);
                    graphicsByUnit.put(obj, bulletGfx);
                }
            }

            @Override
            public void onObjectRemoved(GameUnit obj) {
                GameUnitGraphics g = graphicsByUnit.remove(obj);
                if (g != null) {
                    graphics.remove(g);
                }
            }
        });
        player = unitFactory.createTank(config.getPlayerStartPosition());
        level.addTank((Tank) player);
        tanks.add((Tank) player);
        obstacles = new ArrayList<>();
        for (GridPoint2 position : config.getObstaclePositions()) {
            Obstacle obstacle = unitFactory.createTree(position);
            obstacles.add(obstacle);
            field.addObstacle(obstacle);
        }
        generateRandomAITanks(aiCount, groundLayer.getWidth(), groundLayer.getHeight());
        Texture blueTankTexture = new Texture("images/tank_blue.png");
        Texture greenTreeTexture = new Texture("images/greenTree.png");
        GameUnitGraphics playerGfx = graphicsFactory.createTankGraphics(
                new TextureRegion(blueTankTexture), tileMovement, groundLayer, player);
        GameUnitGraphics playerDecorated = new HealthBarDecorator(playerGfx, (Tank) player);
        graphics.add(playerDecorated);
        graphicsByUnit.put((GameUnit) player, playerDecorated);
        for (int i = 1; i < tanks.size(); i++) {
            Tank aiTank = tanks.get(i);
            GameUnitGraphics aiGfx = graphicsFactory.createTankGraphics(
                    new TextureRegion(blueTankTexture), tileMovement, groundLayer, aiTank);
            GameUnitGraphics aiDecorated = new HealthBarDecorator(aiGfx, aiTank);
            graphics.add(aiDecorated);
            graphicsByUnit.put(aiTank, aiDecorated);
        }
        for (Obstacle obstacle : obstacles) {
            graphics.add(graphicsFactory.createTreeGraphics(
                    new TextureRegion(greenTreeTexture), groundLayer, obstacle));
        }
    }

    private void generateRandomAITanks(int count, int width, int height) {
        Random r = new Random();
        int attempts = 0;
        while (aiControllers.size() < count && attempts < 2000) {
            attempts++;
            GridPoint2 pos = new GridPoint2(r.nextInt(width), r.nextInt(height));
            if (field.isCellBlocked(pos)) continue;
            boolean taken = false;
            for (Tank t : tanks) {
                if (t.getCoordinates().equals(pos)) {
                    taken = true;
                    break;
                }
            }
            if (taken) continue;
            Tank ai = (Tank) unitFactory.createTank(pos);
            level.addTank(ai);
            tanks.add(ai);
            aiControllers.add(new AiTankRuler(ai, field, level));
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            new ToggleHealthBarsCommand(() -> showHealthBars, v -> showHealthBars = v).execute();
        }

        handleInput();
        runAI();
        updateGameState(deltaTime);
        renderGame();
    }

    private void runAI() {
        OccupancyTracker occ = new OccupancyTracker(tanks);
        for (AiTankRuler ai : aiControllers) {
            ai.tick(occ);
        }
    }

    private void handleInput() {
        if (!(player instanceof Tank)) return;
        Tank tank = (Tank) player;
        inputHandler.update();
        Direction direction = inputHandler.getLastDirection();
        if (direction != null) {
            OccupancyTracker occ = new OccupancyTracker(tanks);
            new MoveCommand(tank, direction, field, occ).execute();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            new FireCommand(tank, level, field).execute();
        }
        if (tank.isMoving()) return;
    }

    private void updateGameState(float deltaTime) {
        level.tick(deltaTime);
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

        if (showHealthBars) {
            for (GameUnitGraphics graphic : graphics) {
                if (graphic instanceof OverlayRenderable) {
                    ((OverlayRenderable) graphic).renderOverlay(shapeRenderer);
                }
            }
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
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
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        GameUnitFactory unitFactory = ctx.getBean(GameUnitFactory.class);
        GraphicsFactory graphicsFactory = ctx.getBean(GraphicsFactory.class);
        InputHandler inputHandler = ctx.getBean(InputHandler.class);
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(unitFactory, graphicsFactory, inputHandler), config);
    }
}

