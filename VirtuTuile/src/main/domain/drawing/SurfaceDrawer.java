package domain.drawing;

import domain.room.RoomController;
import domain.room.Tile;
import domain.room.pattern.StraightPattern;
import domain.room.pattern.VerticalBrickPattern;
import domain.room.pattern.VerticalPattern;
import domain.room.pattern.BrickPattern;
import domain.room.pattern.VerticalBrickPattern;
import domain.room.surface.Surface;
import gui.DrawingPanel;
// import javafx.scene.transform.Affine;
import util.UnitConverter;
import gui.MainWindow;
import org.w3c.dom.css.Rect;
//import gui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

//Code pour le zoom inspiré de https://stackoverflow.com/questions/13155382/jscrollpane-zoom-relative-to-mouse-position


public class SurfaceDrawer {
    private final RoomController controller;
    private Dimension initialDimension;
    private MainWindow.MeasurementUnitMode measurementMode;
    private double zoom;


    public SurfaceDrawer(RoomController controller) {
        this.controller = controller;
    }

    public void draw(Graphics2D g2d, ArrayList<Surface> surfaceList, double zoom, Point currentMousePoint) {
        drawSurface(g2d, surfaceList, zoom, currentMousePoint);
    }

    public void drawSurface(Graphics2D g2d, ArrayList<Surface> surfaceList, double zoom, Point currentMousePoint) {
        /*
    }
        for (Surface current_surface : surfaceList) {
            Shape shape;
            if (current_surface.getShape() == null) {
                Polygon pixelPolygon = UnitConverter.convertPolygonToPixel(current_surface.getPolygon(), this.measurementMode);
                Area area = new Area(pixelPolygon);
                shape = area;
                current_surface.setArea(area);
            }
            else {
                shape = current_surface.getShape();
            }

         */

        for (Surface current_surface : surfaceList) {

            Point2D.Double point = current_surface.getPosition();
            Area shape = current_surface.getAreaTest();
            //Shape shape = current_surface.getShape();
            if (zoom != 1) {
                g2d.translate(point.getX(), point.getY());
                g2d.scale(zoom,zoom);
                g2d.translate(-point.getX(), -point.getY());
                /*
                AffineTransform at = new AffineTransform(zoom, 0,0, zoom, 0,0);
                shape.transform(at);
                this.zoom = zoom;
                 */
            }
            Color fillColor = current_surface.getColor();
            g2d.setColor(fillColor);
            g2d.fill(shape);
            g2d.setStroke(new BasicStroke(1));

            if (current_surface.isCovered()) {
                current_surface.getPattern().getVirtualTileList().clear();
                current_surface.getPattern().generateTiles(current_surface.getBoundingRectangle(), current_surface.getTileType(), current_surface.getAreaTest());
                    ArrayList<Tile> array = current_surface.getPattern().getVirtualTileList();
                    for (Tile tile : array) {
                        g2d.setColor(current_surface.getTileType().getColor());
                        g2d.fill(tile);
                        g2d.setColor(Color.BLACK);
                        g2d.draw(tile);
                }
            }

            //StraightPattern pattern = new StraightPattern();
            //VerticalPattern pattern = new VerticalPattern();
            //BrickPattern pattern = new BrickPattern();
            //VerticalBrickPattern pattern = new VerticalBrickPattern();
            g2d.setColor(Color.BLACK);
            //current_surface.setPattern(pattern);
            /*
            for (Polygon polygon : current_surface.getPattern().generateTiles((Rectangle) current_surface.getBoundingRectangle(), current_surface.getTileType(), measurementMode)) {
                g2d.draw(polygon);
            }
            g2d.draw(current_surface.getBoundingRectangle());

             */
            if (current_surface.isSelected()) {
                Color selectedColor = new Color(56, 177, 255);
                g2d.setColor(selectedColor);
                g2d.setStroke(new BasicStroke(2));
            } else {
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(3));
            }
            g2d.draw(shape);
        }

        ArrayList<Surface> surfaceProjectionList = controller.getSurfaceProjectionList();
        if(!surfaceProjectionList.isEmpty()) {
            Surface rectangularProjection = surfaceProjectionList.get(surfaceProjectionList.size() - 1);
           // g2d.draw(rectangularProjection.getPolygon());
            g2d.draw(rectangularProjection.getAreaTest());
           //g2d.draw(UnitConverter.convertPolygonToPixel(rectangularProjection.getPolygon(), this.measurementMode));
        }
    }

    public void setMeasurementUnitMode(MainWindow.MeasurementUnitMode measurementMode) {
        this.measurementMode = measurementMode;
    }
}
