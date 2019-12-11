package domain.room;

import java.awt.*;
import java.awt.geom.Point2D;

public class TileType extends Polygon{
    private Color color;
    private float height;
    private float width;
    private String name;
    private int nbrTilesPerBox;

    private double xOffset;
    private double yOffset;

    public TileType(Color color, float width, float height, String name, int nbrTilesPerBox) {
        this.color = color;
        this.width = width;
        this.height = height;
        this.name = name;
        this.nbrTilesPerBox = nbrTilesPerBox;

        this.xOffset = 0;
        this.yOffset = 0;
    }

    public static TileType createTileWithDefaultParameters() {
        return new TileType(Color.WHITE, 100, 20, "Default", 100);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public Point2D getDimensions() {
        return new Point2D.Float(this.getWidth(), this.getHeight());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNbrTilesPerBox() {
        return nbrTilesPerBox;
    }

    public void setNbrTilesPerBox(int nbrTilesPerBox) {
        this.nbrTilesPerBox = nbrTilesPerBox;
    }

    public double getxOffset() {
        return this.xOffset;
    }

    public double getyOffset() {
        return this.yOffset;
    }

    public void setxOffset(double x) {
        this.xOffset += x;
    }

    public void setyOffset(double y) {
        this.yOffset += y;
    }

    @Override
    public String toString() {
        return getName();
    }
}


