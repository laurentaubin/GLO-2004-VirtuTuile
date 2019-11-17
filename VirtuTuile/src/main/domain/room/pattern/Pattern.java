package domain.room.pattern;

import domain.room.Tile;
import domain.room.TileType;
import gui.MainWindow;

import java.awt.*;
import java.util.ArrayList;

public abstract class Pattern {
    private double xOffset;
    private double yOffset;
    private int angle;
    private double groutWidth;
    private Color groutColor;
    public ArrayList<Tile> virtualTileList;

    public Pattern(double xOffset, double yOffset, int angle, double groutWidth, Color groutColor)  {
        this.xOffset = xOffset;
        this. yOffset = yOffset;
        this.angle = angle;
        this.groutWidth = groutWidth;
        this.groutColor = groutColor;
        this.virtualTileList = new ArrayList<Tile>();
    }

    public double getxOffset() {
        return this.xOffset;
    }

    public double getyOffset() {
        return  this.yOffset;
    }

    public int getAngle() {
        return this.angle;
    }

    public double getGroutWidth() {
        return this.groutWidth;
    }

    public Color getGroutColor() {
        return this.groutColor;
    }

    public abstract ArrayList<Tile> generateTiles(Rectangle boudingRectangle, TileType tileType, MainWindow.MeasurementUnitMode measurementUnitMode);
}
