package domain.room;

import domain.room.surface.Surface;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Tile extends Area {
    private Point2D.Double position;

    public Tile(Point2D.Double position, int[] xPoints, int[] yPoints, int nPoint) {
        super(new Polygon(xPoints, yPoints, nPoint));
        this.position = position;
    }
}
