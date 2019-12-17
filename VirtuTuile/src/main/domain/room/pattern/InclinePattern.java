package domain.room.pattern;

import domain.room.Tile;
import domain.room.TileType;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;



public class InclinePattern extends Pattern {
    private int angle;

    public InclinePattern(int angle) {
        super();
        this.name = "Incline";
        this.angle = angle;

    }

    public InclinePattern(Pattern patternToCopy) {
        super(patternToCopy);
        this.angle = patternToCopy.angle;
        this.name = "Incline";
    }

    public ArrayList<Tile> generateTiles(Rectangle boundingRectangle, TileType tileType, Area area, double groutWidth, boolean center) {
        double xOffset = tileType.getxOffset();
        double yOffset = tileType.getyOffset();
        double tileWidth = tileType.getWidth();
        double tileHeight = tileType.getHeight();
        //TODO mettre une case decalage comme pour brick et si pas égale a zéro ça applique le décalage
        double decalage = 0;

        System.out.println(angle);

        double angleCalcule = Math.toRadians(90 - this.angle);

        Point2D.Double boundingRectanglePosition = new Point2D.Double(boundingRectangle.getX(), boundingRectangle.getY());
        Point2D.Double position = new Point2D.Double(boundingRectanglePosition.getX() - tileHeight * Math.cos(angleCalcule), boundingRectangle.getY() - tileHeight * Math.sin(angleCalcule));

        Point2D.Double initPoint = new Point2D.Double(boundingRectangle.getX() + groutWidth, boundingRectangle.getY() + groutWidth);

        double boundingRectangleWidth = (int) boundingRectangle.getWidth();
        double boundingRectangleHeight = (int) boundingRectangle.getHeight();

        double numberColumn = boundingRectangleWidth / (tileType.getWidth() + groutWidth);
        if (numberColumn / (int) numberColumn != 0) {
            numberColumn = (int) (numberColumn + 1);
        }

        double numberRow = boundingRectangleHeight / (tileType.getHeight() + groutWidth);
        if (numberRow / (int) numberRow != 0) {
            numberRow = (int) (numberRow + 1);
        }
        int maxCount;


        if((boundingRectangleHeight / tileType.getHeight()) > (boundingRectangleWidth / tileType.getHeight())){
            maxCount = (int)(2*(boundingRectangleHeight / tileType.getHeight()) + 3);
        }
        else{
            maxCount = (int)(2*(boundingRectangleWidth / tileType.getHeight()) + 3);
        }

        int count = 0;
        boolean isInside;
        boolean outside;
        int diagonal;

        if(tileType.getWidth() < tileType.getHeight()){
            diagonal = (int)Math.sqrt(Math.pow(numberColumn, 2) + Math.pow((boundingRectangleHeight / (tileType.getWidth() + groutWidth)), 2)) + 2;

            if(boundingRectangleHeight > boundingRectangleWidth){
                maxCount = (int)(2*(boundingRectangleHeight / tileType.getWidth()) + 2);
            }

            else{
                maxCount = (int)(2*(boundingRectangleWidth / tileType.getWidth()) + 2);
            }
        }
        else{
            diagonal = (int)Math.sqrt(Math.pow((boundingRectangleWidth / (tileType.getHeight() + groutWidth)), 2) + Math.pow(numberRow, 2)) + 2;
            if(boundingRectangleHeight > boundingRectangleWidth){
                maxCount = (int)(2*(boundingRectangleHeight / tileType.getHeight()) + 2);
            }

            else{
                maxCount = (int)(2*(boundingRectangleWidth / tileType.getHeight()) + 2);
            }

        }


        for (int i = 1; i <= diagonal; i++) {
            int[] xPoints = new int[4];
            int[] yPoints = new int[4];

            outside = true;
            count = 0;

            if ((i % 2 == 1)) {

                position.setLocation(position.getX() - (tileType.getWidth() + groutWidth) * Math.sin(angleCalcule), position.getY() + (tileType.getWidth() + groutWidth) * Math.cos(angleCalcule));
                position.setLocation(position.getX() - (tileType.getWidth() + groutWidth) * Math.sin(angleCalcule), position.getY() + (tileType.getWidth() + groutWidth) * Math.cos(angleCalcule));


                if(decalage != 0 && i > 1){
                    position.setLocation(position.getX() + (decalage/100 * tileType.getWidth()) * Math.sin(angleCalcule),
                            position.getY() - (decalage/100 * tileType.getWidth()) * Math.cos(angleCalcule));
                }

                do {
                    count++;
                    xPoints[0] = (int) Math.ceil(position.getX());
                    yPoints[0] = (int) Math.ceil(position.getY());

                    xPoints[1] = (int) Math.ceil(position.getX() + tileType.getWidth() * Math.sin(angleCalcule));
                    yPoints[1] = (int) Math.ceil(position.getY() - tileType.getWidth() * Math.cos(angleCalcule));

                    xPoints[2] = (int) Math.ceil(xPoints[1] + tileType.getHeight() * Math.cos(angleCalcule));
                    yPoints[2] = (int) Math.ceil(yPoints[1] + tileType.getHeight() * Math.sin(angleCalcule));

                    xPoints[3] = (int) Math.ceil(position.getX() + tileType.getHeight() * Math.cos(angleCalcule));
                    yPoints[3] = (int) Math.ceil(position.getY() + tileType.getHeight() * Math.sin(angleCalcule));

                    isInside = (boundingRectangle.contains(xPoints[0], yPoints[0]) ||
                            boundingRectangle.contains(xPoints[1], yPoints[1]) ||
                            boundingRectangle.contains(xPoints[2], yPoints[2]) ||
                            boundingRectangle.contains(xPoints[3], yPoints[3]));

                    if(count > maxCount){
                        break;
                    }
                    virtualTileList.add(new Tile(position, xPoints, yPoints, 4));
                    if(isInside){
                        outside = false;
                    }
                    if (isInside || outside) {
                        position.setLocation(position.getX() + (tileType.getWidth() + groutWidth) * Math.sin(angleCalcule), position.getY() - (tileType.getWidth() + groutWidth) * Math.cos(angleCalcule));
                    }


                }
                while (isInside || outside);

                position.setLocation(position.getX() + (tileType.getHeight() + groutWidth) * Math.cos(angleCalcule), position.getY() + (tileType.getHeight() + groutWidth) * Math.sin(angleCalcule));

            } else {
                position.setLocation(position.getX() + (tileType.getWidth() + groutWidth) * Math.sin(angleCalcule), position.getY() - (tileType.getWidth() + groutWidth) * Math.cos(angleCalcule));
                position.setLocation(position.getX() + (tileType.getWidth() + groutWidth) * Math.sin(angleCalcule), position.getY() - (tileType.getWidth() + groutWidth) * Math.cos(angleCalcule));


                if(decalage != 0){
                    position.setLocation(position.getX() - (decalage/100 * tileType.getWidth()) * Math.sin(angleCalcule),
                            position.getY() + (decalage/100 * tileType.getWidth()) * Math.cos(angleCalcule));
                }

                do {
                    count++;
                    xPoints[0] = (int) Math.ceil(position.getX());
                    yPoints[0] = (int) Math.ceil(position.getY());

                    xPoints[1] = (int) Math.ceil(position.getX() + tileType.getWidth() * Math.sin(angleCalcule));
                    yPoints[1] = (int) Math.ceil(position.getY() - tileType.getWidth() * Math.cos(angleCalcule));

                    xPoints[2] = (int) Math.ceil(xPoints[1] + tileType.getHeight() * Math.cos(angleCalcule));
                    yPoints[2] = (int) Math.ceil(yPoints[1] + tileType.getHeight() * Math.sin(angleCalcule));

                    xPoints[3] = (int) Math.ceil(position.getX() + tileType.getHeight() * Math.cos(angleCalcule));
                    yPoints[3] = (int) Math.ceil(position.getY() + tileType.getHeight() * Math.sin(angleCalcule));

                    isInside = (boundingRectangle.contains(xPoints[0], yPoints[0]) ||
                            boundingRectangle.contains(xPoints[1], yPoints[1]) ||
                            boundingRectangle.contains(xPoints[2], yPoints[2]) ||
                            boundingRectangle.contains(xPoints[3], yPoints[3]));

                    if(count > maxCount){
                        break;
                    }

                    virtualTileList.add(new Tile(position, xPoints, yPoints, 4));

                    if(isInside){
                        outside = false;
                    }

                    if (isInside || outside) {
                        position.setLocation(position.getX() - (tileType.getWidth() + groutWidth) * Math.sin(angleCalcule), position.getY() + (tileType.getWidth() + groutWidth) * Math.cos(angleCalcule));
                    }


                }
                while (isInside || outside);



                position.setLocation(position.getX() + (tileType.getHeight() + groutWidth) * Math.cos(angleCalcule), position.getY() + (tileType.getHeight() + groutWidth) * Math.sin(angleCalcule));

            }
        }
        deleteOutsideTile(area, tileWidth, tileHeight);
        return virtualTileList;
    }



    public int getAngle(){
        return this.angle;
    }



    public void deleteOutsideTile(Area surface, double baseTileWidth, double baseTileHeight) {
        for (Tile tile : virtualTileList) {
            tile.intersect(surface);
            int[] xPoints = tile.getXPoints();
            int[] yPoints = tile.getYPoints();
            boolean allInside = surface.contains(xPoints[0], yPoints[0]) &&
                    surface.contains(xPoints[1], yPoints[1]) &&
                    surface.contains(xPoints[2], yPoints[2]) &&
                    surface.contains(xPoints[3], yPoints[3]);

            if (!allInside) {
                tile.setWidth(1);
                tile.setHeight(1);
                tile.inspect();
            }
            else{
                tile.setWidth(100);
                tile.setHeight(100);
                tile.inspect();
            }
        }
    }
}

