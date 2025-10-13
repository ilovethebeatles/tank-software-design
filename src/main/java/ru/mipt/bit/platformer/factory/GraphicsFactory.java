package ru.mipt.bit.platformer.factory;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.graphics.GameUnitGraphics;
import ru.mipt.bit.platformer.model.GameUnit;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.util.TileMovement;

public interface GraphicsFactory {
    GameUnitGraphics createTankGraphics(TextureRegion texture, TileMovement movement,
                                        TiledMapTileLayer layer, GameUnit tank);
    GameUnitGraphics createTreeGraphics(TextureRegion texture, TiledMapTileLayer layer,
                                        Obstacle tree);
}