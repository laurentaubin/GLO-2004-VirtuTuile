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
            Area shape = new Area(current_surface.getAreaTest());
            if (zoom != 1) {
                AffineTransform at = new AffineTransform(zoom, 0,0, zoom, 0,0);
                shape.transform(at);
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
                    AffineTransform at = new AffineTransform(zoom, 0,0, zoom, 0,0);
                    tile.transform(at);
                    if (tile.isTooSmall()) {
                        g2d.setColor(tile.getInspecColor());
                    }
                    else {
                        g2d.setColor(current_surface.getTileType().getColor());
                    }
                    g2d.fill(tile);
                    g2d.setColor(Color.BLACK);
                    g2d.draw(tile);
                }
            }

            g2d.setColor(Color.BLACK);
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
            Shape shape = rectangularProjection.getAreaTest();
            AffineTransform at = new AffineTransform(zoom, 0,0, zoom, 0,0);
            ((Area) shape).transform(at);
            g2d.draw(rectangularProjection.getAreaTest());
           //g2d.draw(UnitConverter.convertPolygonToPixel(rectangularProjection.getPolygon(), this.measurementMode));
        }
    }

    public void setMeasurementUnitMode(MainWindow.MeasurementUnitMode measurementMode) {
        this.measurementMode = measurementMode;
    }
}
