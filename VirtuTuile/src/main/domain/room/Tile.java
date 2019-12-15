package domain.room;

import domain.room.surface.Surface;


import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Tile extends Area implements Serializable {
    final public double MIN_LENGTH = 20;
    private Point2D.Double position;
    private double width;
    private double height;
    private Color inspecColor;
    private boolean tooSmall = false;
    private int[] xPoints;
    private int[] yPoints;

    public Tile(Point2D.Double position, int[] xPoints, int[] yPoints, int nPoint) {
        super(new Polygon(xPoints, yPoints, nPoint));
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        this.position = position;
        this.inspecColor = new Color(255, 214, 10);
    }

    public Tile(Tile tileToCopy) {
        this.position = new Point2D.Double(tileToCopy.position.x, tileToCopy.position.y);
        this.width = new Double(tileToCopy.width);
        this.height = new Double(tileToCopy.height);
        this.inspecColor = new Color(tileToCopy.inspecColor.getRGB());
        this.tooSmall = new Boolean(tileToCopy.tooSmall);
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public Color getInspecColor() {
        return this.inspecColor;
    }

    public int[] getXPoints(){
        return this.xPoints;
    }

    public int[] getYPoints(){
        return this.yPoints;
    }

    public boolean isTooSmall() {
        return this.tooSmall;
    }




    public void setInspect() {
        this.tooSmall = true;
    }

    public void inspect() {
        ArrayList<Point> cornerList = new ArrayList<Point>();
        float[] floats = new float[6];

        PathIterator pathIterator = this.getPathIterator(null);
        while(!pathIterator.isDone()) {
            int type = pathIterator.currentSegment(floats);
            int x = (int)floats[0];
            int y = (int)floats[1];
            if (type != PathIterator.SEG_CLOSE) {
                cornerList.add(new Point(x, y));
            }
            pathIterator.next();
        }

        for (int i = 0; i < cornerList.size(); i++) {
            Point point1 = cornerList.get(i);
            int x1 = point1.x;
            int y1 = point1.y;

            Point point2;
            if (i == cornerList.size() - 1) {
                point2 = cornerList.get(0);
            }
            else {
                point2 = cornerList.get(i + 1);
            }
            int x2 = point2.x;
            int y2 = point2.y;

            double segmentLength = Math.abs(x2 - x1) + Math.abs(y2 - y1);
            if (segmentLength <= MIN_LENGTH) {
                this.setInspect();
                break;
            }
        }
    }
}
