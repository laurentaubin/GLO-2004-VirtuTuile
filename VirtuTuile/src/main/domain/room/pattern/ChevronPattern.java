package domain.room.pattern;

import domain.room.Tile;
import domain.room.TileType;
import gui.MainWindow;
//import javafx.scene.transform.Affine;
import util.UnitConverter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Dictionary;

public class ChevronPattern extends Pattern {

    public ChevronPattern() {
        super();
    }

    public ArrayList<Tile> generateTiles(Rectangle boundingRectangle, TileType tileType, Area area, double groutWidth, boolean center) {

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

        double nbHeight = boundingRectangleWidth / (tileType.getHeight() + groutWidth);
        if (nbHeight / (int)nbHeight != 0) {
            nbHeight = (int)(nbHeight + 5);
        }

        int count = 0;

        boolean isInside;

        for (int i = 1; i <= (nbHeight*2); i++){
            int[] xPoints = new int[4];
            int[] yPoints = new int[4];


            if ((i % 2 == 1)) {
                count = 0;

                do {
                    count++;
                    xPoints[0] = (int) (position.getX());
                    yPoints[0] = (int) (position.getY());

                    xPoints[1] = (int) (position.getX() + tileType.getHeight());
                    yPoints[1] = (int) (position.getY());

                    xPoints[2] = (int) (position.getX() + tileType.getHeight());
                    yPoints[2] = (int) (position.getY() + tileType.getWidth());

                    xPoints[3] = (int) (position.getX());
                    yPoints[3] = (int) (position.getY() + tileType.getWidth());

                    virtualTileList.add(new Tile(position, xPoints, yPoints, 4));
                    position.setLocation(position.getX() + tileType.getHeight() + groutWidth, position.getY() - (tileType.getHeight() + groutWidth));

                    isInside = (boundingRectangle.contains(xPoints[0],  yPoints[0]) ||
                            boundingRectangle.contains(xPoints[1],  yPoints[1]) ||
                            boundingRectangle.contains(xPoints[2],  yPoints[2]) ||
                            boundingRectangle.contains(xPoints[3],  yPoints[3])) || count == 1;
                }
                while(isInside);

                position.setLocation(position.getX() - (tileType.getHeight() + groutWidth), position.getY() + tileType.getWidth() + tileType.getHeight() + 2*groutWidth);

            }

            else {
                count = 0;

                do {
                    count++;
                    xPoints[0] = (int) (position.getX());
                    xPoints[1] = (int) (position.getX() + tileType.getWidth());
                    xPoints[2] = (int) (position.getX() + tileType.getWidth());
                    xPoints[3] = (int) (position.getX());


                    yPoints[0] = (int) (position.getY());
                    yPoints[1] = (int) (position.getY());
                    yPoints[2] = (int) (position.getY() + tileType.getHeight());
                    yPoints[3] = (int) (position.getY() + tileType.getHeight());

                    virtualTileList.add(new Tile(position, xPoints, yPoints, 4));
                    position.setLocation(position.getX() - tileType.getHeight() - groutWidth, position.getY() + (tileType.getHeight() + groutWidth));

                    isInside = (boundingRectangle.contains(xPoints[0],  yPoints[0]) ||
                            boundingRectangle.contains(xPoints[1],  yPoints[1]) ||
                            boundingRectangle.contains(xPoints[2],  yPoints[2]) ||
                            boundingRectangle.contains(xPoints[3],  yPoints[3])) || count == 1;
                }
                while(isInside);

                position.setLocation(position.getX() + tileType.getWidth() + tileType.getHeight() + 2*groutWidth, position.getY() - (tileType.getHeight() + groutWidth));
            }

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