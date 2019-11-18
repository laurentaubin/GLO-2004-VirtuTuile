package domain.room.pattern;

import domain.room.Tile;
import domain.room.TileType;
import gui.MainWindow;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class VerticalBrickPattern extends Pattern {

    public VerticalBrickPattern() {

    }

    public VerticalBrickPattern(double xOffset, double yOffset, int angle, double groutWidth, Color groutColor) {
        super(xOffset, yOffset, angle, groutWidth, groutColor);
    }

    public ArrayList<Tile> generateTiles(Rectangle boundingRectangle, TileType tileType, MainWindow.MeasurementUnitMode measurementMode) {
        Point2D.Double boundingRectanglePosition = new Point2D.Double(boundingRectangle.getX(), boundingRectangle.getY());
        Point2D.Double position = new Point2D.Double(boundingRectanglePosition.getX(), boundingRectangle.getY());
        //Le width du bounding rectangle devrait être un double
        double boundingRectangleWidth = (int)boundingRectangle.getWidth() ;
        double boundingRectangleHeight = (int)boundingRectangle.getHeight();

        //Aller chercher la vrai dimension de grout
        int groutWidth = 2;

        System.out.println(boundingRectangleHeight);

        double numberColumn = boundingRectangleWidth / (tileType.getHeight() + groutWidth);
        if (numberColumn / (int)numberColumn != 0) {
            numberColumn = (int)(numberColumn + 1);
        }

        double numberRow = boundingRectangleHeight / (tileType.getWidth() + groutWidth);
        if(numberRow / (int)numberRow != 0) {
            numberRow = (int)(numberRow + 1);
        }


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
        return virtualTileList;
    }
}
