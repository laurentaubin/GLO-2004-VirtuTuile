package domain.room.pattern;

import domain.room.Tile;
import domain.room.TileType;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;
/*
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


                xPoints[0] = (int)(position.getX() + xOffset + (groutWidth * column));
                xPoints[1] = (int)(position.getX() + xOffset + tileType.getWidth() + (groutWidth * column));
                xPoints[2] = (int)(position.getX() + xOffset + tileType.getWidth() + (groutWidth * column));
                xPoints[3] = (int)(position.getX() + xOffset + (groutWidth * column));



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

}*/


public class SquarePattern extends Pattern{
    public SquarePattern() {
        super();
    }


    public ArrayList<Tile> generateTiles(Rectangle boundingRectangle, TileType tileType, Area area, double groutWidth) {
        Point2D.Double boundingRectanglePosition = new Point2D.Double(boundingRectangle.getX(), boundingRectangle.getY());
        Point2D.Double position = new Point2D.Double(boundingRectanglePosition.getX(), boundingRectangle.getY());
        //Le width du bounding rectangle devrait être un double
        double boundingRectangleWidth = (int)boundingRectangle.getWidth() ;
        double boundingRectangleHeight = (int)boundingRectangle.getHeight();

        //Aller chercher la vrai dimension de grout
        double numberColumn = boundingRectangleWidth / (tileType.getHeight() + groutWidth);

        numberColumn = (int)(numberColumn + 2);


        double numberRow = boundingRectangleHeight / (tileType.getWidth() + groutWidth);

        numberRow = (int)(numberRow + 2);



        for (int row = 1; row <= numberRow; row++) {
            for (int column = 1; column <= numberColumn; column++) {
                int[] xPoints = new int[4];
                int[] yPoints = new int[4];


                if ((column % 2 != 0 && row % 2 != 0) || (column % 2 == 0 && row % 2 == 0)) {



                    for(int i = 0; i < 2; i++) {
                        xPoints[0] = (int) (position.getX());
                        xPoints[1] = (int) (position.getX() + tileType.getHeight());
                        xPoints[2] = (int) (position.getX() + tileType.getHeight());
                        xPoints[3] = (int) (position.getX());

                        yPoints[0] = (int) (position.getY());
                        yPoints[1] = (int) (position.getY());
                        yPoints[2] = (int) (position.getY() + tileType.getWidth());
                        yPoints[3] = (int) (position.getY() + tileType.getWidth());

                        virtualTileList.add(new Tile(position, xPoints, yPoints, 4));
                        position.setLocation(position.getX() + tileType.getHeight(), position.getY());
                    }

                    position.setLocation(position.getX() + groutWidth, position.getY());

                }
                else{

                    for(int i = 0; i < 2; i++) {

                        xPoints[0] = (int) (position.getX());
                        xPoints[1] = (int) (position.getX() + tileType.getWidth());
                        xPoints[2] = (int) (position.getX() + tileType.getWidth());
                        xPoints[3] = (int) (position.getX());


                        yPoints[0] = (int) (position.getY());
                        yPoints[1] = (int) (position.getY());
                        yPoints[2] = (int) (position.getY() + tileType.getHeight());
                        yPoints[3] = (int) (position.getY() + tileType.getHeight());

                        virtualTileList.add(new Tile(position, xPoints, yPoints, 4));
                        position.setLocation(position.getX(), position.getY() + tileType.getHeight());
                    }

                    position.setLocation(position.getX() + tileType.getWidth() + groutWidth, position.getY() - (2*tileType.getHeight()));

                }

                if(position.getX() > boundingRectanglePosition.getX() + boundingRectangleWidth) {
                    break;
                }
            }

            position.setLocation(boundingRectanglePosition.getX(), position.getY() + tileType.getWidth() + (groutWidth));

            if(position.getY() > boundingRectangleWidth){
                break;
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
            tile.inspect();
        }
    }
}

