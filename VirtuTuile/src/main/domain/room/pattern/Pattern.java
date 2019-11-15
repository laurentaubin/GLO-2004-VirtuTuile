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

    public Pattern()  {
        this.virtualTileList = new ArrayList<Tile>();
    }

    public abstract ArrayList<Tile> generateTiles(Rectangle boudingRectangle, TileType tileType, MainWindow.MeasurementUnitMode measurementUnitMode);
}
