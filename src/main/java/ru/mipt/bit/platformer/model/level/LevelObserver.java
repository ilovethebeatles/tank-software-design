package ru.mipt.bit.platformer.model.level;

import ru.mipt.bit.platformer.model.GameUnit;

public interface LevelObserver {
    void onObjectAdded(GameUnit obj);
    void onObjectRemoved(GameUnit obj);
}
