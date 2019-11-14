package domain.room.surface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class RectangularSurfaceTest {

    private RectangularSurface tester;

    @BeforeEach
    void setup() {
        Point point = new Point(10, 10);
        int[] xPoints = new int[]{0, 105, 105, 0};
        int[] yPoints = new int[]{0, 0, 138, 138};
        tester = new RectangularSurface(point, xPoints, yPoints);
    }

    @Test
    void setDimensions() {
        double[] dimensions = new double[]{100, 205};
        tester.setDimensions(dimensions);

        assertEquals(100f, tester.getWidth());
        assertEquals(205f, tester.getHeight());
    }
}
