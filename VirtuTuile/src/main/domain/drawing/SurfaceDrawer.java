package domain.drawing;

import domain.room.RoomController;
import domain.room.surface.RectangularSurface;
import domain.room.surface.Surface;
import gui.MainWindow;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.ArrayList;


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
        ArrayList<Surface> surfaces = RoomController.getSurfaceList();
        for (Surface current_surface: surfaces) {
            Polygon polygon = current_surface.getPolygon();
            if (current_surface.isSelected()){
                Color selectedColor = new Color(189, 227, 255);
                g2d.setColor(selectedColor);
                g2d.setStroke(new BasicStroke(2));
            }
            else {
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(1));
            }
            g2d.draw(polygon);
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
