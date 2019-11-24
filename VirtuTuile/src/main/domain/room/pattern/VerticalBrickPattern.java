package domain.room.pattern;

import domain.room.Tile;
import domain.room.TileType;
import gui.MainWindow;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class VerticalBrickPattern extends Pattern {

    public VerticalBrickPattern() {
        super();
    }


    public ArrayList<Tile> generateTiles(Rectangle boundingRectangle, TileType tileType, Area area) {
        Point2D.Double boundingRectanglePosition = new Point2D.Double(boundingRectangle.getX(), boundingRectangle.getY());
        Point2D.Double position = new Point2D.Double(boundingRectanglePosition.getX(), boundingRectangle.getY());
        //Le width du bounding rectangle devrait être un double
        double boundingRectangleWidth = (int)boundingRectangle.getWidth() ;
        double boundingRectangleHeight = (int)boundingRectangle.getHeight();

        //Aller chercher la vrai dimension de grout

        double groutWidth = this.getGroutWidth();

        double numberColumn = boundingRectangleWidth / (tileType.getHeight() + groutWidth);

        numberColumn = (int)(numberColumn + 2);


        double numberRow = boundingRectangleHeight / (tileType.getWidth() + groutWidth);

        numberRow = (int)(numberRow + 2);



        for (int row = 1; row <= numberRow ; row++) {
            for (int column = 1; column <= numberColumn; column++) {

                int[] xPoints = new int[4];
                xPoints[0] = (int)(position.getX() + (groutWidth * column));
                xPoints[1] = (int)(position.getX() + tileType.getHeight() + (groutWidth * column));
                xPoints[2] = (int)(position.getX() + tileType.getHeight() + (groutWidth * column));
                xPoints[3] = (int)(position.getX() + (groutWidth * column));

                int[] yPoints = new int[4];
                yPoints[0] = (int)(position.getY() + (groutWidth * row));
                yPoints[1] = (int)(position.getY() + (groutWidth * row));
                yPoints[2] = (int)(position.getY() + tileType.getWidth() + (groutWidth * row));
                yPoints[3] = (int)(position.getY() + tileType.getWidth() + (groutWidth * row));

                virtualTileList.add(new Tile(position, xPoints, yPoints, 4));
                position.setLocation(position.getX() + tileType.getHeight(), position.getY());
            }
            if(row % 2 == 0) {
                position.setLocation(boundingRectanglePosition.getX(), position.getY() + tileType.getWidth());
            }
            else{
                position.setLocation(boundingRectanglePosition.getX(), position.getY() + tileType.getWidth());
                position.setLocation(position.getX() - (tileType.getHeight()/2), position.getY());
            }
        }
        deleteOutsideTile(area);
        return virtualTileList;
    }

    public void deleteOutsideTile(Area surface) {
        for (Tile tile : virtualTileList) {
            tile.intersect(surface);
            if(!tile.isEmpty()) {
                tile.setWidth(tile.getBounds2D().getWidth());
                tile.setHeight(tile.getBounds2D().getHeight());
            }
            if (tile.getHeight() < tile.MIN_HEIGHT || tile.getWidth() < tile.MIN_WIDTH) {
                tile.setInspect();
            }
        }
    }
}
