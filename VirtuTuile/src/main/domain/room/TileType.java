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
    private boolean etatInspector;


    public TileType(Color color, float width, float height, String name, int nbrTilesPerBox) {
        this.color = color;
        this.width = width;
        this.height = height;
        this.name = name;
        this.nbrTilesPerBox = nbrTilesPerBox;

        this.xOffset = 0;
        this.yOffset = 0;
    }

    public TileType(TileType tileTypeToCopy) {
        this.color = new Color(tileTypeToCopy.color.getRGB());
        this.height = new Float(tileTypeToCopy.height);
        this.width = new Float(tileTypeToCopy.width);
        this.name = new String(tileTypeToCopy.name);
        this.nbrTilesPerBox = new Integer(tileTypeToCopy.nbrTilesPerBox);
        this.xOffset = new Double(tileTypeToCopy.xOffset);
        this.yOffset = new Double(tileTypeToCopy.yOffset);

    }

    public static TileType createTileWithDefaultParameters() {
        return new TileType(Color.WHITE, 20, 10, "Default", 100);
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

    public boolean equals(TileType other) {
        return this.getName().equals(other.getName());
    }

    public void setEtatInspector(boolean etat){
        this.etatInspector = etat;
    }
    public boolean getEtatInspector(){
        return this.etatInspector;
    }
}


