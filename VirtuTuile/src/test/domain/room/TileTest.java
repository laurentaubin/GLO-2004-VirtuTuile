package domain.room;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TileTest {

    private Color color;
    private Tile tester;

    @BeforeEach
    void setUp() {
        color = new Color(255,255,255);
        tester = Tile.createTileWithDefaultParameters();
    }

    @Test
    void getDimensions() {
        assertEquals(new Point2D.Float(10f, 10f), tester.getDimensions());
    }
}