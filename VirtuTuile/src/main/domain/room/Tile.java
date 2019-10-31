package domain.room;

import java.awt.*;

public class Tile {
    private Color color;
    private int height;
    private int width;
    private String name;
    private int nbrTilesPerBox;

    public Tile(Color color, int width, int height, String name, int nbrTilesPerBox) {
        this.color = color;
        this.width = width;
        this.height = height;
        this.name = name;
        this.nbrTilesPerBox = nbrTilesPerBox;
    }

    static Tile createTileWithDefaultParameters() {
        return new Tile(Color.WHITE, 10, 10, "Default", 100);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Point getDimensions() {
        return new Point(this.getWidth(), this.getHeight());
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
}


