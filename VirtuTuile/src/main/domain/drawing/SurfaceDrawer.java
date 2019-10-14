package domain.drawing;

import domain.room.RoomController;
import domain.room.Surface;
import java.awt.*;
import java.util.List;


public class SurfaceDrawer {
    private final RoomController controller;
    private Dimension initialDimension;

    public SurfaceDrawer(RoomController controller, Dimension initialDimension) {
        this.controller = controller;
        this.initialDimension = initialDimension;
    }

    public void draw(Graphics g) {
        drawSurface(g);
    }


    public void drawSurface(Graphics g){
        int width = (int) initialDimension.getWidth();
        int height = (int) initialDimension.getHeight();

        g.setColor(new Color(140, 98, 57));
        g.fillRect(10,10,10,10);


        /*
        List<Surface> items = controller.getSurfaceList();
        for (Surface item : items) {
            List<Point> sommetsSurface = item.getPoints();
            if (item.getType() == "rectangular") {

            }
        }

         */
    }
}
