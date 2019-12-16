package domain.room.pattern;

import domain.room.Tile;
import domain.room.TileType;
import gui.MainWindow;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Pattern implements Serializable {
    private double xOffset = 0;
    private double yOffset = 0;
    private double groutWidth;
    private Color groutColor;
    public ArrayList<Tile> virtualTileList;
    public String name;
    public int angle;
    public double mismatch;

    public Pattern() {
        this.virtualTileList = new ArrayList<Tile>();
        this.groutWidth = 0d;
        this.groutColor = Color.GRAY;

    }

    public Pattern(double xOffset, double yOffset, int angle, double groutWidth, Color groutColor)  {
        this.xOffset = xOffset;
        this. yOffset = yOffset;
        this.angle = angle;
        this.groutWidth = groutWidth;
        this.groutColor = groutColor;
        this.virtualTileList = new ArrayList<Tile>();
    }

    public Pattern(Pattern patternToCopy) {
        this.xOffset = new Double(patternToCopy.xOffset);
        this.yOffset = new Double(patternToCopy.yOffset);
        this.angle = new Integer(patternToCopy.angle);
        this.groutWidth = new Double(patternToCopy.groutWidth);
        this.groutColor = new Color(patternToCopy.groutColor.getRGB());

        if(patternToCopy.getVirtualTileList() != null) {
            this.virtualTileList = new ArrayList<>();
            for (Tile tile : patternToCopy.virtualTileList) {
                Tile tileToCopy = new Tile(tile);
                this.virtualTileList.add(tileToCopy);
            }
        }
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

    public void setGroutWidth(double width) {
        this.groutWidth = width;
    }
    public double getGroutWidth() {
        return this.groutWidth;
    }

    public Color getGroutColor() {
        return this.groutColor;
    }

    public ArrayList<Tile> getVirtualTileList() {
        return this.virtualTileList;
    }

    public String getName(){
        return this.name;
    }


    public abstract ArrayList<Tile> generateTiles(Rectangle boudingRectangle, TileType tileType, Area area, double groutWidth, boolean center);

    public void setMismatch(double mismatch){
        this.mismatch = mismatch;
    }

    public void setAngle(int angle){
        this.angle = angle;
    }


    public void setOffset(double x, double y) {
        this.xOffset += x;
        this.yOffset += y;
    }

    public void initOffset() {
        this.xOffset = 0;
        this.yOffset = 0;
    }

}
