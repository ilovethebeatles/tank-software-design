package ru.mipt.bit.platformer.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.model.Direction;

import java.util.HashMap;
import java.util.Map;

public class InputHandler {
    private final Map<Integer, Runnable> keyBindings = new HashMap<>();
    private Direction lastDirection;

    public InputHandler() {
        setupDefaultBindings();
    }

    private void setupDefaultBindings() {
        bindKey(Input.Keys.UP, () -> lastDirection = Direction.UP);
        bindKey(Input.Keys.W, () -> lastDirection = Direction.UP);
        bindKey(Input.Keys.DOWN, () -> lastDirection = Direction.DOWN);
        bindKey(Input.Keys.S, () -> lastDirection = Direction.DOWN);
        bindKey(Input.Keys.LEFT, () -> lastDirection = Direction.LEFT);
        bindKey(Input.Keys.A, () -> lastDirection = Direction.LEFT);
        bindKey(Input.Keys.RIGHT, () -> lastDirection = Direction.RIGHT);
        bindKey(Input.Keys.D, () -> lastDirection = Direction.RIGHT);
        bindKey(Input.Keys.SPACE, () -> {
            System.out.println("FIRE! - можно добавить стрельбу");
        });
    }

    public void bindKey(int keycode, Runnable action) {
        keyBindings.put(keycode, action);
    }

    public void update() {
        lastDirection = null;

        for (Map.Entry<Integer, Runnable> entry : keyBindings.entrySet()) {
            if (Gdx.input.isKeyPressed(entry.getKey())) {
                entry.getValue().run();
            }
        }
    }

    public Direction getLastDirection() {
        return lastDirection;
    }

    public boolean isKeyPressed(int keycode) {
        return Gdx.input.isKeyPressed(keycode);
    }
}
