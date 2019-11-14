package domain.drawing;

import domain.room.RoomController;
import domain.room.surface.Surface;
import util.UnitConverter;
import gui.MainWindow;
import org.w3c.dom.css.Rect;
//import gui.MainWindow;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

//Code pour le zoom inspiré de https://stackoverflow.com/questions/13155382/jscrollpane-zoom-relative-to-mouse-position


public class SurfaceDrawer {
    private final RoomController controller;
    private Dimension initialDimension;
    private MainWindow.MeasurementUnitMode measurementMode;



    public SurfaceDrawer(RoomController controller, Dimension initialDimension, MainWindow.MeasurementUnitMode measurementMode) {
        this.controller = controller;
        this.initialDimension = initialDimension;
        this.measurementMode = measurementMode;
    }

    public void draw(Graphics g, double zoom, float prevZoom, Point zoomPoint, double xOffset, double yOffset) {
        drawSurface(g, zoom, prevZoom, zoomPoint, xOffset, yOffset);
    }

    public void drawSurface(Graphics g, double zoom, float prevZoom, Point zoomPoint, double xOffset, double yOffset){
        Graphics2D g2d = (Graphics2D) g;
        ArrayList<Surface> surfaces = RoomController.getSurfaceList();
        if (zoom != 1) {

            g2d.scale(zoom, zoom);
        }
        for (Surface current_surface : surfaces) {
            Polygon polygon = UnitConverter.convertPolygonToPixel(current_surface.getPolygon(), measurementMode);
            if (current_surface.isSelected()){
                Color selectedColor = new Color(56, 177, 255);
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
            g2d.draw(UnitConverter.convertPolygonToPixel(rectangularProjection.getPolygon(), measurementMode));
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
