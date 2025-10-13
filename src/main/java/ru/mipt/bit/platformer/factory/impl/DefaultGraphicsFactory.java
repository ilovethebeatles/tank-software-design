package ru.mipt.bit.platformer.factory.impl;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.factory.GraphicsFactory;
import ru.mipt.bit.platformer.graphics.GameUnitGraphics;
import ru.mipt.bit.platformer.graphics.impl.TankGraphics;
import ru.mipt.bit.platformer.graphics.impl.TreeGraphics;
import ru.mipt.bit.platformer.model.GameUnit;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.util.TileMovement;

public class DefaultGraphicsFactory implements GraphicsFactory {
    @Override
    public GameUnitGraphics createTankGraphics(TextureRegion texture, TileMovement movement,
                                               TiledMapTileLayer layer, GameUnit tank) {
        return new TankGraphics(texture, movement, layer, tank);
    }

    @Override
    public GameUnitGraphics createTreeGraphics(TextureRegion texture, TiledMapTileLayer layer,
                                               Obstacle tree) {
        return new TreeGraphics(texture, layer, tree);
    }
}
