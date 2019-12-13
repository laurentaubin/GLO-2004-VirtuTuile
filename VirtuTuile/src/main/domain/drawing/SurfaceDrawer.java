package domain.drawing;

import domain.room.RoomController;
import domain.room.Tile;
import domain.room.surface.Surface;

import gui.MainWindow;
//import gui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
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
        AffineTransform at = new AffineTransform();
        at.setToScale(zoom, zoom);
        for (Surface current_surface : surfaceList) {
            /*
            Path2D.Double path = new Path2D.Double();
            for (int i = 0; i < current_surface.nPoints; i++) {
                if (i == 0) {
                    path.moveTo(current_surface.xPoints[i], current_surface.yPoints[i]);
                }
                else {
                    path.lineTo(current_surface.xPoints[i], current_surface.yPoints[i]);
                }
            }
            path.closePath();
            Area shape = new Area(path);
             */

            Area shapeTest = new Area(current_surface.getArea());
            Path2D.Double shape = new Path2D.Double(shapeTest);
            Area otherShape = new Area();
            ArrayList<Surface> elementarySurface = new ArrayList<>(current_surface.getElementarySurface());
            ArrayList<Surface> imaginarySurfaces = new ArrayList<>();
            ArrayList<Surface> imaginaryHoles = new ArrayList<>();
            AffineTransform tx = new AffineTransform();
            double groutWidth = current_surface.getGroutWidth();


            for (Surface es : elementarySurface) {
                Point2D surfaceMiddlePoint = es.getCenterOfMass();
                double widthRatio = this.getRatioWidth(es, groutWidth);
                double heightRatio = this.getRatioHeight(es, groutWidth);
                if (es.isHole()) {
                    widthRatio = 1 / widthRatio;
                    heightRatio = 1 /heightRatio;
                }

                tx.scale(widthRatio, heightRatio);
                Surface imaginarySurface = new Surface(es, tx);
                Point2D imaginaryMiddle = imaginarySurface.getCenterOfMass();
                double x = surfaceMiddlePoint.getX() - imaginaryMiddle.getX();
                double y = surfaceMiddlePoint.getY() - imaginaryMiddle.getY();
                imaginarySurface.translatePointsTest(x, y);

                if (es.isHole()) { imaginaryHoles.add(imaginarySurface); }
                else { imaginarySurfaces.add(imaginarySurface); }

                tx.setToIdentity();
            }

            for (Surface scaledSurfaces : imaginarySurfaces) {
                Area scaledArea = scaledSurfaces.getArea();
                if (otherShape.isEmpty()) {
                    otherShape = new Area(scaledArea);
                }
                else {
                    otherShape.add(scaledArea);
                }
            }

            for (Surface scaledSurfaces : imaginaryHoles) {
                Area scaledArea = scaledSurfaces.getArea();
                if (otherShape.isEmpty()) {
                    otherShape = new Area(scaledArea);
                }
                else {
                    otherShape.subtract(scaledArea);
                }
            }


            if (zoom != 1) {
                //AffineTransform at = new AffineTransform(zoom, 0,0, zoom, 0,0);
                shape.transform(at);
            }

            Color fillColor = current_surface.getColor();
            g2d.setColor(fillColor);
            g2d.fill(shape);
            g2d.setStroke(new BasicStroke(1));

            if (current_surface.isCovered()) {
                current_surface.getPattern().getVirtualTileList().clear();
                // TODO avoir du grout autour de la surface ou pas
                if(current_surface.getPattern().getName() == "Square" && !((current_surface.getTileType().getWidth() / current_surface.getTileType().getHeight()) == 2)){
                    controller.dimensionIncorrectPaquet();
                }
                else {
                    current_surface.getPattern().generateTiles(current_surface.getBoundingRectangle(), current_surface.getTileType(), otherShape, current_surface.getGroutWidth(), current_surface.getCoverCenter());
                    //  current_surface.getPattern().generateTiles(current_surface.getBoundingRectangle(), current_surface.getTileType(), current_surface.getAreaTest(), current_surface.getGroutWidth(), current_surface.getCoverCenter());
                }


                ArrayList<Tile> array = current_surface.getPattern().getVirtualTileList();
                for (Tile tile : array) {
                    //AffineTransform at = new AffineTransform(zoom, 0,0, zoom, 0,0);
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
            g2d.setColor(Color.RED);

            g2d.setStroke(new BasicStroke(1));
            for (Surface surface : current_surface.getElementarySurface()) {
                Area elem = new Area(surface.getArea());
                //AffineTransform at = new AffineTransform(zoom, 0,0, zoom, 0,0);
                elem.transform(at);
                g2d.draw(elem);
            }

            g2d.setStroke(new BasicStroke(1));
            g2d.setColor(Color.GREEN);
            //g2d.draw(otherShape);

            /*
            g2d.setStroke(new BasicStroke(1));
            g2d.setColor(Color.GREEN);

            for (Surface imagine : imaginarySurfaces) {
                Area bleh = new Area(imagine.getAreaTest());
                g2d.draw(bleh);
            }

            g2d.setStroke(new BasicStroke(1));
            g2d.setColor(Color.BLACK);

             */
        }

        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.BLACK);

        ArrayList<Surface> surfaceProjectionList = controller.getSurfaceProjectionList();
        if(!surfaceProjectionList.isEmpty()) {
            Surface rectangularProjection = surfaceProjectionList.get(surfaceProjectionList.size() - 1);
           // g2d.draw(rectangularProjection.getPolygon());
            Area shapeTest = new Area(rectangularProjection.getArea());
            Path2D.Double shape = new Path2D.Double(shapeTest);
            //AffineTransform at = new AffineTransform(zoom, 0,0, zoom, 0,0);
            shape.transform(at);
            g2d.draw(shape);
           //g2d.draw(UnitConverter.convertPolygonToPixel(rectangularProjection.getPolygon(), this.measurementMode));
        }

        ArrayList<Point> irregularPointList = controller.getPointList();
        if(!irregularPointList.isEmpty()) {
            //AffineTransform at = new AffineTransform(zoom, 0,0, zoom, 0,0);
            g2d.setColor(Color.RED);
            for (int i = 0; i < irregularPointList.size(); i++) {
                int currentX;
                int currentY;
                int lastX;
                int lastY;
                currentX = irregularPointList.get(i).x;
                currentY = irregularPointList.get(i).y;
                if (i == 0) {
                    lastX = currentX;
                    lastY = currentY;
                }
                else {
                    lastX = irregularPointList.get(i - 1).x;
                    lastY = irregularPointList.get(i - 1).y;
                }

                Line2D linish = new Line2D.Double(lastX, lastY, currentX, currentY);
                Path2D line = new Path2D.Double(linish);
                line.transform(at);
                g2d.draw(line);

                Ellipse2D dotish = new Ellipse2D.Double(currentX - 3/zoom, currentY - 3/zoom, 6/zoom, 6/zoom);
                Area dot = new Area(dotish);
                dot.transform(at);
                g2d.fill(dot);
                g2d.draw(dot);
            }
        }
    }

    public void setMeasurementUnitMode(MainWindow.MeasurementUnitMode measurementMode) {
        this.measurementMode = measurementMode;
    }

    private double getRatioWidth(Surface surface, double groutWidth) {
        double realWidth = surface.getWidth();
        double width = realWidth - 2*groutWidth;
        return width/realWidth;
    }

    private double getRatioHeight(Surface surface, double groutWidth) {
        double realHeight = surface.getHeight();
        double height = realHeight - 2*groutWidth;
        return height/realHeight;
    }
}
