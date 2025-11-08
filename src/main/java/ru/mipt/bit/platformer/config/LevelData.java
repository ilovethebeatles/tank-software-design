package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.math.GridPoint2;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
public class LevelData {
    private GridPoint2 playerStartPosition;
    private List<GridPoint2> obstaclePositions;
}
