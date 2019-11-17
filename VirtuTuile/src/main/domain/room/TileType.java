package domain.room;

import java.awt.*;
import java.awt.geom.Point2D;

public class TileType extends Polygon{
    private Color color;
    private float height;
    private float width;
    private String name;
    private int nbrTilesPerBox;

    public TileType(Color color, float width, float height, String name, int nbrTilesPerBox) {
        this.color = color;
        this.width = width;
        this.height = height;
        this.name = name;
        this.nbrTilesPerBox = nbrTilesPerBox;
    }

    public static TileType createTileWithDefaultParameters() {
        return new TileType(Color.WHITE, 100, 100, "Default", 100);
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

    @Override
    public String toString() {
        return getName();
    }
}


