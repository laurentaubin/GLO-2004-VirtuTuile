package domain.drawing;

import domain.room.RoomController;
import domain.room.surface.Surface;
//import gui.MainWindow;

import java.awt.*;
import java.util.ArrayList;


public class SurfaceDrawer {
    private final RoomController controller;
    private Dimension initialDimension;



    public SurfaceDrawer(RoomController controller, Dimension initialDimension) {
        this.controller = controller;
        this.initialDimension = initialDimension;
    }

    public void draw(Graphics g, float zoom, float prevZoom, Point zoomPoint, double xOffset, double yOffset) {
        drawSurface(g, zoom, prevZoom, zoomPoint, xOffset, yOffset);
    }

    public void drawSurface(Graphics g, float zoom, float prevZoom, Point zoomPoint, double xOffset, double yOffset){
        Graphics2D g2d = (Graphics2D) g;
        ArrayList<Surface> surfaces = RoomController.getSurfaceList();
        if (zoom != 1) {
        }
        for (Surface current_surface: surfaces) {
            Polygon polygon = current_surface.getPolygon();
            if (current_surface.isSelected()){
                Color selectedColor = new Color(189, 227, 255);
                g2d.setColor(selectedColor);
                g2d.setStroke(new BasicStroke(2));
            }
            else {
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(3));
            }
            g2d.draw(polygon);
        }

        ArrayList<Surface> surfaceProjectionList = RoomController.getSurfaceProjectionList();
        if(!surfaceProjectionList.isEmpty()) {
            Surface rectangularProjection = surfaceProjectionList.get(surfaceProjectionList.size() - 1);
            g2d.draw(rectangularProjection.getPolygon());
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
