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
import ru.mipt.bit.platformer.graphics.impl.TankGraphics;
import ru.mipt.bit.platformer.graphics.impl.TreeGraphics;
import ru.mipt.bit.platformer.handler.InputHandler;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.graphics.Field;
import ru.mipt.bit.platformer.model.impl.Tree;
import ru.mipt.bit.platformer.model.impl.Tank;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;
    private Field field;
    private MapRenderer mapRenderer;
    private TileMovement tileMovement;
    private TiledMapTileLayer groundLayer;
    private InputHandler inputHandler;
    private Tank player;
    private Tree treeObstacle;
    private TankGraphics playerGraphics;
    private TreeGraphics treeGraphics;

    @Override
    public void create() {
        batch = new SpriteBatch();
        TiledMap tiledMap = new TmxMapLoader().load("level.tmx");
        groundLayer = getSingleLayer(tiledMap);
        mapRenderer = createSingleLayerMapRenderer(tiledMap, batch);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        field = new Field(tiledMap, groundLayer);
        inputHandler = new InputHandler();
        player = new Tank(new GridPoint2(1, 1));
        treeObstacle = new Tree(new GridPoint2(1, 3));
        field.addObstacle(treeObstacle);
        Texture blueTankTexture = new Texture("images/tank_blue.png");
        Texture greenTreeTexture = new Texture("images/greenTree.png");
        playerGraphics = new TankGraphics(new TextureRegion(blueTankTexture),
                tileMovement, groundLayer, player);
        treeGraphics = new TreeGraphics(new TextureRegion(greenTreeTexture),
                groundLayer, treeObstacle);
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
        if (player.isMoving()) return;

        inputHandler.update();
        Direction direction = inputHandler.getLastDirection();

        if (direction != null) {
            GridPoint2 newPosition = new GridPoint2(player.getCoordinates()).add(direction.getVector());
            if (!field.isCellBlocked(newPosition)) {
                player.moveTo(direction);
            }
        }
    }

    private void updateGameState(float deltaTime) {
        player.updateMovementProgress(deltaTime);
        playerGraphics.update(deltaTime);
        treeGraphics.update(deltaTime);
    }

    private void renderGame() {
        mapRenderer.render();
        batch.begin();
        drawTextureRegionUnscaled(batch, playerGraphics.getTextureRegion(),
                playerGraphics.getBounds(), playerGraphics.getRotation());
        drawTextureRegionUnscaled(batch, treeGraphics.getTextureRegion(),
                treeGraphics.getBounds(), treeGraphics.getRotation());
        batch.end();
    }

    @Override
    public void dispose() {
        if (playerGraphics != null && playerGraphics.getTextureRegion() != null) {
            playerGraphics.getTextureRegion().getTexture().dispose();
        }
        if (treeGraphics != null && treeGraphics.getTextureRegion() != null) {
            treeGraphics.getTextureRegion().getTexture().dispose();
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