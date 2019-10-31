package domain.room;

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
        surface = new Surface(xPoints, yPoints, 4);
        tester = new Room();
        tester.addSurface(surface);

    }

    @Test
    void addCoverToSelectedSurfaces() {
        Cover cover = Cover.createCoverWithDefaultParameters();
        surface.setSelectionStatus(true);
        tester.addCoverToSelectedSurfaces(cover);

        Surface item = tester.getSurfaceList().get(0);
        assertEquals(cover, item.getCover());

    }
    @Test
    void addPatternToSelectedSurfaces() {
        Cover cover = Cover.createCoverWithDefaultParameters();
        surface.setCover(cover);
        surface.setSelectionStatus(true);
        tester.addPatternToSelectedSurfaces(Cover.Pattern.ANGLE);


        Surface item = tester.getSurfaceList().get(0);
        assertEquals(Cover.Pattern.ANGLE, item.getCover().getPattern());
    }

}