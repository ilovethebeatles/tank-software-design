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
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Field;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.Tree;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;
    private Field field;
    private MapRenderer mapRenderer;
    private TileMovement tileMovement;
    private TiledMapTileLayer groundLayer;

    private Tank player;
    private Tree treeObstacle;

    @Override
    public void create() {
        batch = new SpriteBatch();
        TiledMap tiledMap = new TmxMapLoader().load("level.tmx");
        groundLayer = getSingleLayer(tiledMap);
        mapRenderer = createSingleLayerMapRenderer(tiledMap, batch);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        field = new Field(tiledMap, groundLayer);

        Texture blueTankTexture = new Texture("images/tank_blue.png");
        Texture greenTreeTexture = new Texture("images/greenTree.png");
        player = new Tank(new GridPoint2(1, 1), new TextureRegion(blueTankTexture), tileMovement, groundLayer);
        treeObstacle = new Tree(new GridPoint2(1, 3), new TextureRegion(greenTreeTexture), groundLayer);
        field.addObstacle(treeObstacle);
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

    private void handleInput() {
        if (player.isMoving()) return;

        boolean up = Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W);
        boolean down = Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S);
        boolean left = Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A);
        boolean right = Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D);
        Direction direction = Direction.fromKeys(up, down, left, right);
        if (direction == null) return;
        GridPoint2 newPosition = new GridPoint2(player.getCoordinates()).add(direction.getVector());
        if (!field.isCellBlocked(newPosition)) {
            player.moveTo(direction);
        }
    }

    private void updateGameState(float deltaTime) {
        player.update(deltaTime);
        treeObstacle.update(deltaTime);
    }

    private void renderGame() {
        mapRenderer.render();
        batch.begin();
        drawTextureRegionUnscaled(batch, player.getTextureRegion(), player.getBounds(), player.getRotation());
        drawTextureRegionUnscaled(batch, treeObstacle.getTextureRegion(), treeObstacle.getBounds(), treeObstacle.getRotation());
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        if (player != null && player.getTextureRegion() != null) {
            player.getTextureRegion().getTexture().dispose();
        }
        if (treeObstacle != null && treeObstacle.getTextureRegion() != null) {
            treeObstacle.getTextureRegion().getTexture().dispose();
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