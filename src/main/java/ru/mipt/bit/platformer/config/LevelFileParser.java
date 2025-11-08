package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;

public class LevelFileParser {

    public static LevelData parseLevel(String filePath) {
        FileHandle file = Gdx.files.internal(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("Level file not found: " + filePath);
        }
        String[] lines = file.readString().trim().split("\n");
        List<GridPoint2> obstacles = new ArrayList<>();
        GridPoint2 playerStart = null;
        int width = 0;
        int height = lines.length;
        for (int y = 0; y < lines.length; y++) {
            String line = lines[lines.length - 1 - y].trim();
            width = Math.max(width, line.length());
            for (int x = 0; x < line.length(); x++) {
                char cell = line.charAt(x);
                switch (cell) {
                    case 'T':
                        obstacles.add(new GridPoint2(x, y));
                        break;
                    case 'X':
                        if (playerStart != null) {
                            throw new IllegalArgumentException("Multiple player start positions found");
                        }
                        playerStart = new GridPoint2(x, y);
                        break;
                    case '_':
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown cell character: " + cell);
                }
            }
        }
        if (playerStart == null) {
            throw new IllegalArgumentException("No player start position (X) found in level file");
        }

        return new LevelData(playerStart, obstacles);
    }
}
