package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.GridPoint2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LevelFileParser {

    public static LevelData parseLevel(String filePath) throws IOException {
        InputStream inputStream = LevelFileParser.class.getClassLoader()
                .getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IllegalArgumentException("Level file not found in resources: " + filePath);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            List<String> lines = new ArrayList<>();
            String ln = "";
            while ((ln = reader.readLine()) != null) {
                lines.add(ln);
            }
            List<GridPoint2> obstacles = new ArrayList<>();
            GridPoint2 playerStart = null;
            for (int y = 0; y < lines.size(); y++) {
                String line = lines.get(lines.size() - 1 - y).trim();
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
        } catch (Exception e) {
            throw new IllegalArgumentException("Level file not found: " + filePath +
                    ". Make sure file exists in src/main/resources/", e);
        }
    }
}
