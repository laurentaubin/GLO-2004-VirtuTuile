package domain.room.pattern;

import domain.room.Tile;
import domain.room.TileType;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class SquarePattern extends Pattern{
    public SquarePattern() {
        super();
    }

    public ArrayList<Tile> generateTiles(Rectangle boundingRectangle, TileType tileType, Area area, double groutWidth) {
        double xOffset = tileType.getxOffset();
        double yOffset = tileType.getyOffset();

        double tileWidth = tileType.getWidth();
        double tileHeight = tileType.getHeight();
        Point2D.Double boundingRectanglePosition = new Point2D.Double(boundingRectangle.getX(), boundingRectangle.getY());
        Point2D.Double position = new Point2D.Double(boundingRectanglePosition.getX(), boundingRectangle.getY());
        if (xOffset <= 0) {
            position.x = position.x + xOffset;
        }

        else {
            position.x = position.x - tileWidth + (xOffset%tileWidth);
        }

        if (yOffset <= 0) {
            position.y = position.y + yOffset;
        }
        else {
            position.y = position.y - tileHeight + (yOffset%tileHeight);

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

                /*
                xPoints[0] = (int)(position.getX() + xOffset + (groutWidth * column));
                xPoints[1] = (int)(position.getX() + xOffset + tileType.getWidth() + (groutWidth * column));
                xPoints[2] = (int)(position.getX() + xOffset + tileType.getWidth() + (groutWidth * column));
                xPoints[3] = (int)(position.getX() + xOffset + (groutWidth * column));

                 */

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
