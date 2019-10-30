package domain.drawing;

import domain.room.RoomController;
import domain.room.Surface;
import java.awt.*;
import java.util.ArrayList;
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
        Graphics2D g2d = (Graphics2D) g;
        ArrayList<Surface> surfaces = controller.getSurfaceList();
        for (Surface current_surface: surfaces) {
            if (current_surface.isSelected()){
                System.out.println("Hekrgwnje");
                g2d.setColor(Color.BLACK);
                g2d.fill(current_surface);
            }
            g2d.draw(current_surface);
        }


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
