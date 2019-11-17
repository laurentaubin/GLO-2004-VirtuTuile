package domain.room.pattern;

import domain.room.Tile;
import domain.room.TileType;
import gui.MainWindow;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class DefaultPattern extends Pattern {

    public DefaultPattern(double xOffset, double yOffset, int angle, double groutWidth, Color groutColor) {
        super(xOffset, yOffset, angle, groutWidth, groutColor);
    }

    public ArrayList<Tile> generateTiles(Rectangle boundingRectangle, TileType tileType, MainWindow.MeasurementUnitMode measurementMode) {
        Point2D.Double boudingRectanglePosition = new Point2D.Double(boundingRectangle.getX(), boundingRectangle.getY());
        double boundingRectangleWidth = boundingRectangle.getWidth();
        double boundingRectangleHeight = boundingRectangle.getHeight();

        ArrayList<Tile> array = new ArrayList<Tile>();

        return array;
    }
}
