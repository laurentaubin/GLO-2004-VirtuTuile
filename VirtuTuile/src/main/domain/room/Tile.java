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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return length;
    }

    public Point getDimensions() {
        return new Point(this.getWidth(), this.getHeight());
    }

    public String getName() {
        return name;
    }

    public int getNbrTilesPerBox() {
        return nbrTilesPerBox;
    }

}


