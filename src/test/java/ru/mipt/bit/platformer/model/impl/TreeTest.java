package ru.mipt.bit.platformer.model.impl;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TreeTest {
    private Tree tree;

    @BeforeEach
    void setUp() {
        tree = new Tree(new GridPoint2(3, 4));
    }

    @Test
    void constructor_shouldSetInitialCoordinates() {
        assertEquals(new GridPoint2(3, 4), tree.getCoordinates());
    }

    @Test
    void tree_shouldBeImmutableObject() {
        GridPoint2 initialCoordinates = tree.getCoordinates();
        GridPoint2 coordinatesCopy = tree.getCoordinates();
        coordinatesCopy.set(999, 999);
        assertEquals(new GridPoint2(3, 4), tree.getCoordinates());
        assertEquals(initialCoordinates, tree.getCoordinates());
    }
}
