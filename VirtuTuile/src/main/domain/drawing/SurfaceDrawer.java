package domain.drawing;

import domain.room.RoomController;
import domain.room.pattern.StraightPattern;
import domain.room.surface.Surface;
import util.UnitConverter;
import gui.MainWindow;
import org.w3c.dom.css.Rect;
//import gui.MainWindow;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

//Code pour le zoom inspiré de https://stackoverflow.com/questions/13155382/jscrollpane-zoom-relative-to-mouse-position


public class SurfaceDrawer {
    private final RoomController controller;
    private Dimension initialDimension;
    private MainWindow.MeasurementUnitMode measurementMode;


    public SurfaceDrawer(RoomController controller) {
        this.controller = controller;
    }

    public void draw(Graphics2D g2d, ArrayList<Surface> surfaceList) {
        drawSurface(g2d, surfaceList, measurementMode);
    }

    public void drawSurface(Graphics2D g2d, ArrayList<Surface> surfaceList, MainWindow.MeasurementUnitMode measurementMode) {
        for (Surface current_surface : surfaceList) {
            StraightPattern pattern = new StraightPattern();
            current_surface.setPattern(pattern);


            for (Polygon polygon : current_surface.getPattern().generateTiles((Rectangle) current_surface.getBoundingRectangle(), current_surface.getTileType(), measurementMode)) {
                g2d.draw(polygon);
            }


            g2d.draw(current_surface.getBoundingRectangle());

            //Color surfaceColor = current_surface.getColor();
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

    public void setMeasurementUnitMode(MainWindow.MeasurementUnitMode measurementMode) {
        this.measurementMode = measurementMode;
    }
}
