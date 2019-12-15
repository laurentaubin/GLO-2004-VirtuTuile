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

    public ArrayList<Tile> generateTiles(Rectangle boundingRectangle, TileType tileType, Area area, double groutWidth, boolean center) {

        double tileWidth = tileType.getWidth();
        double tileHeight = tileType.getHeight();
        Point2D.Double boundingRectanglePosition = new Point2D.Double(boundingRectangle.getX(), boundingRectangle.getY());
        Point2D.Double position = new Point2D.Double(boundingRectanglePosition.getX(), boundingRectangle.getY());

        double xOffset = this.getxOffset();
        double yOffset = this.getyOffset();

        if (xOffset <= 0) {
            position.x = position.x + xOffset;
        }

        else {
            position.x = position.x - tileWidth + (xOffset%2*tileWidth);
        }

        if (yOffset <= 0) {
            position.y = position.y + yOffset;
        }
        else {
            position.y = position.y - tileHeight + (yOffset%2*tileHeight);

        }
        Point2D.Double initPosition = new Point2D.Double(position.getX(), position.getY());


        double boundingRectangleWidth = (int)boundingRectangle.getWidth() + Math.abs(xOffset);
        double boundingRectangleHeight = (int)boundingRectangle.getHeight() + Math.abs(yOffset);


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
                        yPoints[0] = (int) (position.getY());

                        xPoints[1] = (int) (position.getX() + tileType.getHeight());
                        yPoints[1] = (int) (position.getY());

                        xPoints[2] = (int) (position.getX() + tileType.getHeight());
                        yPoints[2] = (int) (position.getY() + tileType.getWidth());

                        xPoints[3] = (int) (position.getX());
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

            position.setLocation(initPosition.getX(), position.getY());
        }
        deleteOutsideTile(area);
        return virtualTileList;
    }

    public String getName(){
        this.name = "Square";
        return this.name;
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
