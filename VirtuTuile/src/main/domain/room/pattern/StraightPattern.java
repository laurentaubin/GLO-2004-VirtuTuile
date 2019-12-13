package domain.room.pattern;

import domain.room.Tile;
import domain.room.TileType;
import gui.MainWindow;
//import javafx.scene.transform.Affine;
import util.UnitConverter;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class StraightPattern extends Pattern {

    public StraightPattern() {
        super();
    }

    public ArrayList<Tile> generateTiles(Rectangle boundingRectangle, TileType tileType, Area area, double groutWidth, boolean center) {
        double xOffset = tileType.getxOffset();
        double yOffset = tileType.getyOffset();
        double decalageCenterX = 0;
        double decalageCenterY = 0;

        double tileWidth = tileType.getWidth();
        double tileHeight = tileType.getHeight();
        Point2D.Double boundingRectanglePosition = new Point2D.Double(boundingRectangle.getX(), boundingRectangle.getY());
        Point2D.Double position = new Point2D.Double(boundingRectanglePosition.getX(), boundingRectangle.getY());

        if(center){
            decalageCenterX = ((tileWidth - ((boundingRectangle.getWidth() % (tileWidth + groutWidth))/2)) + 1.5*groutWidth);
            decalageCenterY = ((tileHeight - ((boundingRectangle.getHeight() % (tileHeight + groutWidth))/2)) + 1.5*groutWidth);
        }

        if (xOffset <= 0) {
            position.x = position.x + xOffset - decalageCenterX;
        }

        else {
            position.x = position.x - tileWidth + (xOffset%tileWidth) - decalageCenterX;
        }

        if (yOffset <= 0) {
            position.y = position.y + yOffset - decalageCenterY;
        }
        else {
            position.y = position.y - tileHeight + (yOffset%tileHeight) - decalageCenterY;

        }



        Point2D.Double initPosition = new Point2D.Double(position.getX(), position.getY());


        double boundingRectangleWidth = (int)boundingRectangle.getWidth() + Math.abs(xOffset);
        double boundingRectangleHeight = (int)boundingRectangle.getHeight() + Math.abs(yOffset);


        double numberColumn = boundingRectangleWidth / (tileType.getWidth() + groutWidth);
        if (numberColumn / (int)numberColumn != 0) {
            numberColumn = (int)(numberColumn + 2);
        }

        double numberRow = boundingRectangleHeight / (tileType.getHeight() + groutWidth);
        if(numberRow / (int)numberRow != 0) {
            numberRow = (int)(numberRow + 2);
        }

        for (int row = 1; row <= numberRow ; row++) {
            for (int column = 1; column <= numberColumn; column++){
                int[] xPoints = new int[4];
                int[] yPoints = new int[4];
                xPoints[0] = (int)(position.getX() + (groutWidth * column));
                xPoints[1] = (int)(position.getX() + tileType.getWidth() + (groutWidth * column));
                xPoints[2] = (int)(position.getX() + tileType.getWidth() + (groutWidth * column));
                xPoints[3] = (int)(position.getX() + (groutWidth * column));

                yPoints[0] = (int)(position.getY() + (groutWidth * row));
                yPoints[1] = (int)(position.getY() + (groutWidth * row));
                yPoints[2] = (int)(position.getY() + tileType.getHeight() + (groutWidth * row));
                yPoints[3] = (int)(position.getY() + tileType.getHeight() + (groutWidth * row));

                Tile tile = new Tile(position, xPoints, yPoints, 4);
                virtualTileList.add(tile);
                position.setLocation(position.getX() + tileType.getWidth(), position.getY());
            }
            position.setLocation(initPosition.getX(), position.getY() + tileType.getHeight());
        }
        deleteOutsideTile(area, tileWidth, tileHeight);
        return virtualTileList;
    }



    public void deleteOutsideTile(Area surface, double baseTileWidth, double baseTileHeight) {
        for (Tile tile : virtualTileList) {
            tile.intersect(surface);
            if(!tile.isEmpty()) {
                tile.setWidth(tile.getBounds2D().getWidth());
                tile.setHeight(tile.getBounds2D().getHeight());

            }
            tile.inspect();
        }
    }
}
