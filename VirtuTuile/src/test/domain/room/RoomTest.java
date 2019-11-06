package domain.room;

import domain.room.surface.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    private Room tester;
    private int[] xPoints;
    private int[] yPoints;
    private Surface surface;

    @BeforeEach
    void setUp() {
        xPoints = new int[]{0, 10, 10, 0};
        yPoints = new int[]{0, 0, 10, 10};
        tester = new Room();
        tester.addRectangularSurface(xPoints, yPoints, 4, true);
        surface = tester.getSurfaceList().get(0);

    }

    @Test
    void addCoverToSelectedSurfaces() {
        Cover cover = Cover.createCoverWithDefaultParameters();
        surface.setSelectionStatus(true);
        tester.addCoverToSelectedSurfaces(cover);

        assertEquals(cover, surface.getCover());

    }
    @Test
    void addPatternToSelectedSurfaces() {
        Cover cover = Cover.createCoverWithDefaultParameters();
        surface.setCover(cover);
        surface.setSelectionStatus(true);
        tester.setPatternToSelectedSurfaces(Cover.Pattern.ANGLE);

        assertEquals(Cover.Pattern.ANGLE, surface.getCover().getPattern());
    }

}