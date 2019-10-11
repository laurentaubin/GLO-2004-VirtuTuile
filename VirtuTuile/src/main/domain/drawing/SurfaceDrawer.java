package domain.drawing;

import domain.room.RoomController;
import domain.room.Surface;
import java.awt.*;

public class SurfaceDrawer {
    private final RoomController controller;
    private Dimension initialDimension;

    public SurfaceDrawer(RoomController controller, Dimension initialDimension) {
        this.controller = controller;
        this.initialDimension = initialDimension;
    }

    public void draw(Graphics g) {
        drawSurfaces(g);
    }

    private void drawSurfaces(Graphics g) {
        /*
        List<Surface> items = controller.getSurfaceList();
        for (Surface item: items) {

        }
        */
    }
}
