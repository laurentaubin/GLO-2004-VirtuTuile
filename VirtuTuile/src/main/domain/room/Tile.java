package domain.room;

import java.awt.*;

public class Tile {
    private Color color;
    private int length;
    private int width;
    private String name;
    private int nbrTilesPerBox;

    public Tile(Color color, int width, int height, String name, int nbrTilesPerBox) {
        this.color = color;
        this.width = width;
        this.length = height;
        this.width = width;
        this.name = name;
        this.nbrTilesPerBox = nbrTilesPerBox;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
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


