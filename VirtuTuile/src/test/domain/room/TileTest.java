package domain.room;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

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
        assertEquals(new Point(10, 25), tester.getDimensions());
    }
}
