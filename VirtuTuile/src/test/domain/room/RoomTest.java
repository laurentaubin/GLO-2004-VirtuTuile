package domain.room;

import domain.room.surface.*;
import gui.MainWindow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    private Room tester;
    private Surface surface;

    @BeforeEach
    void setUp() {
        int[] xPoints = new int[]{0, 10, 10, 0};
        int[] yPoints = new int[]{0, 0, 10, 10};
        Point point = new Point(10, 10);
        RectangularSurface rectangularSurface = new RectangularSurface(point, xPoints, yPoints);

        this.surface = new Surface();
        this.surface.addElementaryWholeSurface(rectangularSurface);
        this.tester = new Room();
        tester.addSurfaceToList(surface);

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
        tester.setPatternToSelectedSurfaces(new AnglePattern());

        assertEquals(new AnglePattern(), surface.getCover().getPattern());
    }
}