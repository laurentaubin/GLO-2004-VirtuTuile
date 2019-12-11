package domain.room.pattern;

import domain.room.Tile;
import domain.room.TileType;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;



public class InclinePattern extends Pattern {

    public InclinePattern() {
        super();
    }

    public ArrayList<Tile> generateTiles(Rectangle boundingRectangle, TileType tileType, Area area, double groutWidth, boolean center) {
        double xOffset = tileType.getxOffset();
        double yOffset = tileType.getyOffset();
        double tileWidth = tileType.getWidth();
        double tileHeight = tileType.getHeight();

        double angle = 45;
        angle = -Math.toRadians(30);

        Point2D.Double boundingRectanglePosition = new Point2D.Double(boundingRectangle.getX(), boundingRectangle.getY());
        Point2D.Double position = new Point2D.Double(boundingRectanglePosition.getX(), boundingRectangle.getY());

        Point2D.Double initPoint = new Point2D.Double(boundingRectangle.getX(), boundingRectangle.getY());

        double boundingRectangleWidth = (int)boundingRectangle.getWidth() ;
        double boundingRectangleHeight = (int)boundingRectangle.getHeight();

        double numberColumn = boundingRectangleWidth / (tileType.getWidth() + groutWidth);
        if (numberColumn / (int)numberColumn != 0) {
            numberColumn = (int)(numberColumn + 1);
        }

        double numberRow = boundingRectangleHeight / (tileType.getHeight() + groutWidth);
        if(numberRow / (int)numberRow != 0) {
            numberRow = (int)(numberRow + 1);
        }

        for (int row = 1; row <= numberRow ; row++) {
            for (int column = 1; column <= numberColumn; column++){

                int[] xPoints = new int[4];
                int[] yPoints = new int[4];



                xPoints[0] = (int)(position.getX() + xOffset + (groutWidth * column));
                xPoints[1] = (int)(position.getX() + xOffset + tileType.getWidth() + (groutWidth * column));
                xPoints[2] = (int)(position.getX() + xOffset + tileType.getWidth() + (groutWidth * column));
                xPoints[3] = (int)(position.getX() + xOffset + (groutWidth * column));

                yPoints[0] = (int)(position.getY() + yOffset + (groutWidth * row));
                yPoints[1] = (int)(position.getY() + yOffset + (groutWidth * row));
                yPoints[2] = (int)(position.getY() + yOffset + tileType.getHeight() + (groutWidth * row));
                yPoints[3] = (int)(position.getY() + yOffset + tileType.getHeight() + (groutWidth * row));

                Tile tile = new Tile(position, xPoints, yPoints, 4);
                AffineTransform at = new AffineTransform(1, 0, 0, 1, 0, 0);
                at.rotate(angle, xPoints[0], yPoints[0]);
                tile.transform(at);
                virtualTileList.add(tile);
                tile.setWidth(Math.abs(xPoints[0] - xPoints[1]) + Math.abs(yPoints[0] - yPoints[1]));
                tile.setHeight(Math.abs(xPoints[1] - xPoints[2]) + Math.abs(yPoints[1] - yPoints[2]));
                position.setLocation(position.getX() + tileType.getWidth(), position.getY());
            }
            position.setLocation(initPoint.getX() + tileHeight*Math.sin((Math.PI/2) - angle), position.getY() - tileHeight*Math.cos((Math.PI/2) - angle));
            initPoint = new Point2D.Double(initPoint.getX() + tileHeight/Math.sqrt(2),position.getY() + (tileType.getHeight()/Math.sqrt(2)) );
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
                tile.inspect();
            }
        }
    }
}