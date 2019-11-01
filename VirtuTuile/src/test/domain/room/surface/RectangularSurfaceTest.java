package domain.room.surface;

import domain.room.Cover;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangularSurfaceTest {

    private RectangularSurface tester;
    private int[] xPoints;
    private int[] yPoints;

    @BeforeEach
    void setup() {
        xPoints = new int[]{0, 105, 105, 0};
        yPoints = new int[]{0, 0, 138, 138};
        tester = new RectangularSurface(xPoints, yPoints, 4, "RECTANGULAR");
        tester.setCover(Cover.createCoverWithDefaultParameters());
    }

    @Test
    void getTilesAcross() {
        assertEquals(11, tester.getTilesAcross());
    }

    @Test
    void getTilesDown() {
        assertEquals(14, tester.getTilesDown());
    }
}