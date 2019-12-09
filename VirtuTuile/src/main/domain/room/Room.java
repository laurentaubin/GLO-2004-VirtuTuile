package domain.room;

import domain.room.pattern.*;
import domain.room.surface.ElementarySurface;
import domain.room.surface.RectangularSurface;
import domain.room.surface.Surface;
import gui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;



public class Room implements Serializable{

    private ArrayList<Surface> surfaceList;
    private ArrayList<Surface> surfaceProjectionList;
    private ArrayList<TileType> tileTypeList;

    public Room() {
        surfaceList = new ArrayList<Surface>();
        surfaceProjectionList = new ArrayList<Surface>();
        tileTypeList = new ArrayList<TileType>();
    }

    public Room(Room room) {
        surfaceList = new ArrayList<Surface>(room.getSurfaceList());
        surfaceProjectionList = new ArrayList<Surface>(room.getSurfaceProjectionList());
        tileTypeList = new ArrayList<TileType>(room.getTileList());
    }

    public void addSurfaceToList(Surface surface) {
        this.surfaceList.add(surface);
    }

    public void addSurfaceToProjectionList(Surface surface) {
        this.surfaceProjectionList.add(surface);
    }

    public void addRectangularProjection(Point point, double[] xPoints, double[] yPoints) {
        Surface surface = new Surface(xPoints, yPoints, 4);
        this.addSurfaceToProjectionList(surface);
    }

    public void addRectangularSurface(double[] xPoints, double[] yPoints, int number_of_edges) {
        Surface surface = new Surface(xPoints, yPoints, number_of_edges);
        surface.addCopy(surface);
        this.addSurfaceToList(surface);
    }

    public void addRectangularSurfaceOnGrid(double[] xDrawPoints, double[] yDrawPoints, int number_of_edges, double gridGap) {
        Surface surface = new Surface(xDrawPoints, yDrawPoints, number_of_edges);
        surface.addCopy(surface);
        this.addSurfaceToList(surface);
        this.snapSurfaceToGrid(surface, gridGap);
    }

    public void addIrregularSurface(ArrayList<Point> pointList) {
        //TODO Code pour ajouter une surface irrégulière
        int n = pointList.size();
        double[] x = new double[n];
        double[] y = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = pointList.get(i).x;
            y[i] = pointList.get(i).y;
        }
        Surface surface = new Surface(x, y, n);
        surface.addCopy(surface);
        this.addSurfaceToList(surface);
    }

    public boolean isEmpty() {
        return surfaceList.isEmpty();
    }

    public ArrayList<Surface> getSurfaceList() {
        return surfaceList;
    }

    public ArrayList<Surface> getSurfaceProjectionList() {
        return surfaceProjectionList;
    }

    public void clearSurfaceProjectionList() {
        this.surfaceProjectionList.clear();
    }

    public int getNumberOfSurfaces() {
        return surfaceList.size();
    }


    void switchSelectionStatus(double x, double y, boolean isShiftDown) {
        Boolean latestSurfaceSelected = true;

        for (int i = surfaceList.size() - 1; i >= 0; i--) {
            if (surfaceList.get(i).getAreaTest().contains(x, y) && latestSurfaceSelected == true) {
                    surfaceList.get(i).switchSelectionStatus();
                    latestSurfaceSelected = false;

            }
            else {
                if (!isShiftDown) {
                    surfaceList.get(i).unselect();
                }
            }
        }
    }

    private void switchSelectionStatusIfContains(double x, double y, boolean isShiftDown, Surface surfaceInRoom) {
        Point2D.Double point = new Point2D.Double(x, y);
        if (surfaceInRoom.getAreaTest().contains(point)) {
            surfaceInRoom.switchSelectionStatus();
        } else if (!isShiftDown) {
            surfaceInRoom.unselect();
        }
    }

    public void updateSelectedSurfacesPositions(double deltaX, double deltaY) {
        for (Surface surfaceInRoom : this.surfaceList) {
            if (surfaceInRoom.isSelected()) {
                surfaceInRoom.translate(deltaX, deltaY);
            }
        }
    }

    public boolean surfaceSelecte() {
        boolean auMoinsUne = false;
        for (Surface surface : surfaceList) {
            if (surface.isSelected()) {
                auMoinsUne = true;
                break;
            }
        }
        return auMoinsUne;
    }


    void setPatternToSelectedSurfaces(Pattern pattern) {
        for (Surface surface : this.surfaceList) {
            if (surface.isSelected()) {
                surface.getCover().setPattern(pattern);
            }
        }
    }

    void addCoverToSelectedSurfaces(Cover cover) {
        for (Surface surface : this.surfaceList) {
            if (surface.isSelected()) {
                surface.setCover(cover);
            }
        }
    }


    public double[] getSelectedRectangularSurfaceDimensions() {
        double[] dimensionList = new double[2];
        for (Surface surface : this.surfaceList) {
            if (surface.isSelected()) {
                dimensionList[0] = surface.getWidth();
                dimensionList[1] = surface.getHeight();
            }
        }
        return dimensionList;
    }

    public void deleteSurface() {
        for (int i = this.surfaceList.size() - 1; i >= 0; i--) {
            if (this.surfaceList.get(i).isSelected()) {
                surfaceList.remove(i);
            }
        }
    }

    public void setGroutToSelectedSurfaces(Grout grout) {
        for (Surface surface : this.surfaceList) {
            surface.getCover().setGrout(grout);
        }
    }

    public void setTileToSelectedSufaces(float width, float height, Color color, String name, int nbrTilesPerBox) {
        TileType tileType = new TileType(color, width, height, name, nbrTilesPerBox);
        for (Surface surface : this.surfaceList) {
            surface.setTileType(tileType);
        }
    }

    public void setMeasurementMode(MainWindow.MeasurementUnitMode mode) {
        for (Surface surface : surfaceList) {
            surface.setMeasurementMode(mode);
        }
    }

    public void setGroutColor(Color color) {
        for (Surface surface : this.surfaceList) {
            if (surface.isSelected() && surface.isCovered()) {
                surface.setColor(color);
            }
        }
    }


    public void setSelectedSurfaceColor(Color color) {
        for (Surface surface : this.surfaceList) {
            if (surface.isSelected()) {
                surface.setColor(color);
            }
        }
    }

    public Color getSelectedSurfaceColor() {
        Color color = Color.WHITE;
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surface : this.surfaceList) {
                if (surface.isSelected()) {
                    color = surface.getColor();
                }
            }
        }
        return color;
    }

    public void setSelectedSurfaceWidth(double enteredWidth) {
        int counterOfSelectedSurfaces = 0;
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected()) {
                counterOfSelectedSurfaces += 1;
                if (counterOfSelectedSurfaces > 1) {
                    break;
                }
            }
        }
        if (counterOfSelectedSurfaces == 1) {
            for (Surface surfaceInRoom : surfaceList) {
                if (surfaceInRoom.isSelected()) {
                    surfaceInRoom.setWidth(enteredWidth);
                }
            }
        }
    }

    public void setSelectedSurfaceHeight(double height) {
        int counterOfSelectedSurfaces = 0;
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected()) {
                counterOfSelectedSurfaces += 1;
                if (counterOfSelectedSurfaces > 1) {
                    break;
                }
            }
        }
        if (counterOfSelectedSurfaces == 1) {
            for (Surface surfaceInRoom : surfaceList) {
                if (surfaceInRoom.isSelected()) {
                    surfaceInRoom.setHeight(height);
                }
            }
        }
    }

    public Dimension getSelectedSurfaceDimensions() {
        Dimension dimension = new Dimension(0, 0);
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surfaceInRoom : surfaceList) {
                if (surfaceInRoom.isSelected()) {
                    dimension = surfaceInRoom.getDimensions();
                }
            }
        }
        return dimension;
    }

    public int getNumberOfSelectedSurfaces() {
        int count = 0;
        for (Surface surface : surfaceList) {
            if (surface.isSelected()) {
                count++;
            }
        }
        return count;
    }

    public boolean surfaceInTouch() {
        boolean areIntersect = true;
        ArrayList<Surface> surfacesToCombine = getSurfaceToCombine();
        Surface baseSurface = surfacesToCombine.get(0);
        surfacesToCombine.remove(0);

        for (Surface surface : surfacesToCombine) {
            if (!baseSurface.intersect(surface.getAreaTest())) {
                areIntersect = false;
            }
        }
        return areIntersect;
    }

    public void combineSelectedSurface() {
        int index = 0;
        ArrayList<Surface> surfacesToCombine = getSurfaceToCombine();

        while (surfacesToCombine.get(index).isHole()) {
            index++;
            if (index >= surfacesToCombine.size()) {
                index = 0;
                break;
            }
        }

        Surface baseSurface = surfacesToCombine.get(index);
        surfacesToCombine.remove(index);

        for (Surface surface : surfacesToCombine) {
            if (baseSurface.intersect(surface.getAreaTest())) {
                if (surface.isHole()) {
                    if (baseSurface.isHole()) {
                        baseSurface.merge(surface);
                        baseSurface.addMergeSurfaceToList(surface);
                        surfaceList.remove(surface);
                    } else {
                        baseSurface.setHole(surface);
                        baseSurface.addMergeSurfaceToList(surface);
                        surfaceList.remove(surface);
                    }
                } else {
                    baseSurface.merge(surface);
                    baseSurface.addMergeSurfaceToList(surface);
                    surfaceList.remove(surface);
                }
            }
        }
    }

    private ArrayList<Surface> getSurfaceToCombine() {
        ArrayList<Surface> surfacesToCombine = new ArrayList<Surface>();
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected()) {
                surfacesToCombine.add(surfaceInRoom);
            }
        }
        return surfacesToCombine;
    }

    public void createTileFromUserInput(Color color, float width, float height, String name, int nbrTilesPerBox) {
        TileType tileType = new TileType(color, width, height, name, nbrTilesPerBox);
        tileTypeList.add(tileType);
    }

    public ArrayList<TileType> getTileList() {
        return tileTypeList;
    }


    public void setSelectedTileToSelectedSurface(TileType selectedTileType) {
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected()) {
                surfaceInRoom.setTileType(selectedTileType);
            }

            if (surfaceInRoom.isCovered()) {
                surfaceInRoom.getPattern().generateTiles(surfaceInRoom.getBoundingRectangle(), surfaceInRoom.getTileType(), surfaceInRoom.getAreaTest(), surfaceInRoom.getGroutWidth());
            }
        }
    }

    public void setStraightPatternToSelectedSurface() {
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected() && !surfaceInRoom.isHole()) {
                StraightPattern straightPattern = new StraightPattern();
                straightPattern.generateTiles(surfaceInRoom.getBoundingRectangle(), surfaceInRoom.getTileType(), surfaceInRoom.getAreaTest(), surfaceInRoom.getGroutWidth());
                surfaceInRoom.setPattern(straightPattern);
            }
        }
    }

    public void setHorizontalPatternToSelectedSurface() {
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected() && !surfaceInRoom.isHole()) {
                BrickPattern brickPattern = new BrickPattern(surfaceInRoom.getMismatch());
                brickPattern.generateTiles(surfaceInRoom.getBoundingRectangle(), surfaceInRoom.getTileType(), surfaceInRoom.getAreaTest(), surfaceInRoom.getGroutWidth());
                surfaceInRoom.setPattern(brickPattern);
            }
        }
    }

    public void setVerticalPatternToSelectedSurface() {

        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected() && !surfaceInRoom.isHole()) {
                VerticalPattern verticalPattern = new VerticalPattern();
                verticalPattern.generateTiles(surfaceInRoom.getBoundingRectangle(), surfaceInRoom.getTileType(), surfaceInRoom.getAreaTest(), surfaceInRoom.getGroutWidth());
                surfaceInRoom.setPattern(verticalPattern);
            }
        }
    }

    public void setVerticalBrickPatternToSelectedSurface() {
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected() && !surfaceInRoom.isHole()) {
                VerticalBrickPattern verticalBrickPattern = new VerticalBrickPattern(surfaceInRoom.getMismatch());
                verticalBrickPattern.generateTiles(surfaceInRoom.getBoundingRectangle(), surfaceInRoom.getTileType(), surfaceInRoom.getAreaTest(), surfaceInRoom.getGroutWidth());
                surfaceInRoom.setPattern(verticalBrickPattern);
            }
        }
    }

    public void setAnglePattern() {
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected()) {
                InclinePattern inclinePattern = new InclinePattern();
                inclinePattern.generateTiles(surfaceInRoom.getBoundingRectangle(), surfaceInRoom.getTileType(), surfaceInRoom.getAreaTest(), surfaceInRoom.getGroutWidth());
                surfaceInRoom.setPattern(inclinePattern);
            }
        }
    }


    public void setSquarePatternToSelectedSurface() {
        boolean incorrect = false;
        for (Surface surface : surfaceList) {
            if (surface.isSelected()) {
                if((surface.getTileType().getWidth() / surface.getTileType().getHeight()) == 2) {
                    SquarePattern squarePattern = new SquarePattern();
                    squarePattern.generateTiles(surface.getBoundingRectangle(), surface.getTileType(), surface.getAreaTest(), surface.getGroutWidth());
                    surface.setPattern(squarePattern);
                }
                else{
                    incorrect = true;
                }
            }
        }
        if (incorrect){

            String[] options = {"Ok"};
            int indexReponse = JOptionPane.showOptionDialog(null, "Les dimensions des tuiles sont invalides, elles doivent avoir un ratio de 2:1",
                    "Attention!",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

        }
    }

    public void setChevronPattern() {
        for (Surface surface : surfaceList) {
            if (surface.isSelected()) {
                ChevronPattern chevronPattern = new ChevronPattern();
                chevronPattern.generateTiles(surface.getBoundingRectangle(), surface.getTileType(), surface.getAreaTest(), surface.getGroutWidth());
                surface.setPattern(chevronPattern);
            }
        }
    }


    public void setSelectedSurfaceAsHole() {
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surfaceInRoom : surfaceList) {
                if (surfaceInRoom.isSelected())
                    surfaceInRoom.setIsHole();
            }
        }
    }

    public void setSelectedSurfaceAsWhole() {
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surfaceInRoom : surfaceList) {
                if (surfaceInRoom.isSelected())
                    surfaceInRoom.setIsHoleAsFalse();
            }
        }
    }

    public boolean getIfSelectedSurfaceIsAHole() {
        boolean isAHole = false;
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surface : this.surfaceList) {
                if (surface.isSelected()) {
                    isAHole = surface.isHole();
                }
            }
        }
        return isAHole;
    }

    public boolean checkIfMouseAboveTile(int xPos, int yPos) {
        boolean mouseIsAboveTile = false;
        for (Surface surface : surfaceList) {
            for (Tile tile : surface.getPattern().getVirtualTileList()) {
                if (tile.contains(xPos, yPos)) {
                    mouseIsAboveTile = true;
                }
            }
        }
        return mouseIsAboveTile;
    }

    public ArrayList<Double> getTileDimensions(int xPos, int yPos) {
        ArrayList<Double> array = new ArrayList<Double>();
        for (Surface surface : surfaceList) {
            for (Tile tile : surface.getPattern().getVirtualTileList()) {
                if (tile.contains(xPos, yPos)) {
                    array.add(0, tile.getWidth());
                    array.add(1, tile.getHeight());
                }
            }
        }
        return array;
    }

    public void setSelectedSurfaceGroutWidth(double width) {
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected()) {
                surfaceInRoom.setGroutWidth(width);
            }
        }
    }

    public double getSelectedSurfaceGroutWidth() {
        double width = 0d;
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surface : surfaceList) {
                if (surface.isSelected()) {
                    width = surface.getGroutWidth();
                }
            }
        }
        return width;
    }

    public void verticallyAlignSelectedSurfaces() throws IllegalStateException {
        if (this.getNumberOfSelectedSurfaces() != 2) {
            throw new IllegalStateException("More or less than 2 surfaces are selected");
        }
        ArrayList<Surface> selectedSurfaceList = this.getSelectedSurfaces();
        if (selectedSurfaceList.get(0).isBeneath(selectedSurfaceList.get(1))) {
            verticallyAlignSurfaces(selectedSurfaceList.get(0), selectedSurfaceList.get(1));
        } else {
            verticallyAlignSurfaces(selectedSurfaceList.get(1), selectedSurfaceList.get(0));
        }
    }

    private void verticallyAlignSurfaces(Surface bottomSurface, Surface topSurface) {
        Point2D bottomSurfaceTopMostPoint = bottomSurface.getTopMostPoint();
        Point2D topSurfaceBottomMostPoint = topSurface.getBottomMostPoint();
        double heightDifference = bottomSurfaceTopMostPoint.getY() - topSurfaceBottomMostPoint.getY();

        Point2D bottomSurfaceTopLeftPoint = bottomSurface.getTopLeftPoint();
        bottomSurfaceTopLeftPoint.setLocation(bottomSurfaceTopLeftPoint.getX(), bottomSurfaceTopLeftPoint.getY() - heightDifference);

        bottomSurface.snapToPoint(bottomSurfaceTopLeftPoint);
    }

    public void horizontallyAlignSelectedSurfaces() throws IllegalStateException {
        if (this.getNumberOfSelectedSurfaces() != 2) {
            throw new IllegalStateException("More or less than 2 surfaces are selected");
        }
        ArrayList<Surface> selectedSurfaceList = this.getSelectedSurfaces();
        if (selectedSurfaceList.get(0).isToTheLeft(selectedSurfaceList.get(1))) {
            horizontallyAlignSurfaces(selectedSurfaceList.get(0), selectedSurfaceList.get(1));
        } else {
            horizontallyAlignSurfaces(selectedSurfaceList.get(1), selectedSurfaceList.get(0));
        }
    }

    private void horizontallyAlignSurfaces(Surface leftSurface, Surface rightSurface) {
        Point2D rightSurfaceLeftMostPoint = rightSurface.getLeftMostPoint();
        Point2D leftSurfaceRightMostPoint = leftSurface.getRightMostPoint();
        double widthDifference = rightSurfaceLeftMostPoint.getX() - leftSurfaceRightMostPoint.getX();

        Point2D rightSurfaceTopLeftPoint = rightSurface.getTopLeftPoint();
        rightSurfaceTopLeftPoint.setLocation(rightSurfaceTopLeftPoint.getX() - widthDifference, rightSurfaceTopLeftPoint.getY());

        rightSurface.snapToPoint(rightSurfaceTopLeftPoint);
    }

    public void verticallyCenterSelectedSurfaces() {
        if (this.getNumberOfSelectedSurfaces() == 2) {
            ArrayList<Surface> selectedSurfaceList = this.getSelectedSurfaces();
            if (selectedSurfaceList.get(0).isToTheLeft(selectedSurfaceList.get(1))) {
                Surface leftSurface = selectedSurfaceList.get(0);
                Surface rightSurface = selectedSurfaceList.get(1);

                Point2D leftSurfaceMiddlePoint = leftSurface.getLeftMostPoint();
                leftSurfaceMiddlePoint.setLocation(leftSurfaceMiddlePoint.getX(), leftSurfaceMiddlePoint.getY() + (leftSurface.getHeight() / 2.0));

                Point2D rightSurfaceLeftMostPoint = rightSurface.getLeftMostPoint();
                rightSurfaceLeftMostPoint.setLocation(rightSurfaceLeftMostPoint.getX(), leftSurfaceMiddlePoint.getY() - (rightSurface.getHeight() / 2.0));

                rightSurface.snapToPoint(rightSurfaceLeftMostPoint);
            } else {
                Surface rightSurface = selectedSurfaceList.get(0);
                Surface leftSurface = selectedSurfaceList.get(1);

                Point2D rightSurfaceMiddlePoint = rightSurface.getLeftMostPoint();
                rightSurfaceMiddlePoint.setLocation(rightSurfaceMiddlePoint.getX(), rightSurfaceMiddlePoint.getY() + (rightSurface.getHeight() / 2.0));

                Point2D leftSurfaceLeftMostPoint = leftSurface.getLeftMostPoint();
                leftSurfaceLeftMostPoint.setLocation(leftSurfaceLeftMostPoint.getX(), rightSurfaceMiddlePoint.getY() - (leftSurface.getHeight() / 2.0));

                leftSurface.snapToPoint(leftSurfaceLeftMostPoint);
            }
        }
    }

    public void horizontallyCenterSelectedSurfaces() {
        if (this.getNumberOfSelectedSurfaces() == 2) {
            ArrayList<Surface> selectedSurfaceList = this.getSelectedSurfaces();
            if (selectedSurfaceList.get(0).isToTheLeft(selectedSurfaceList.get(1))) {
                Surface leftSurface = selectedSurfaceList.get(0);
                Surface rightSurface = selectedSurfaceList.get(1);

                Point2D leftSurfaceMiddlePoint = leftSurface.getLeftMostPoint();
                leftSurfaceMiddlePoint.setLocation(leftSurfaceMiddlePoint.getX() + (leftSurface.getWidth() / 2.0), leftSurfaceMiddlePoint.getY());

                Point2D rightSurfaceLeftMostPoint = rightSurface.getLeftMostPoint();
                rightSurfaceLeftMostPoint.setLocation(leftSurfaceMiddlePoint.getX() - (rightSurface.getWidth() / 2.0), rightSurfaceLeftMostPoint.getY());

                rightSurface.snapToPoint(rightSurfaceLeftMostPoint);
            } else {
                Surface rightSurface = selectedSurfaceList.get(0);
                Surface leftSurface = selectedSurfaceList.get(1);

                Point2D rightSurfaceMiddlePoint = rightSurface.getLeftMostPoint();
                rightSurfaceMiddlePoint.setLocation(rightSurfaceMiddlePoint.getX() + (rightSurface.getWidth() / 2.0), rightSurfaceMiddlePoint.getY());

                Point2D leftSurfaceLeftMostPoint = leftSurface.getLeftMostPoint();
                leftSurfaceLeftMostPoint.setLocation(rightSurfaceMiddlePoint.getX() - (leftSurface.getWidth() / 2.0), leftSurfaceLeftMostPoint.getY());

                leftSurface.snapToPoint(leftSurfaceLeftMostPoint);
            }
        }
    }

    public void leftAlignSelectedSurfaces() {
        if (this.getNumberOfSelectedSurfaces() == 2) {
            ArrayList<Surface> selectedSurfaceList = this.getSurfaceList();

            if (selectedSurfaceList.get(0).isToTheLeft(selectedSurfaceList.get(1))) {
                Surface leftSurface = selectedSurfaceList.get(0);
                Surface rightSurface = selectedSurfaceList.get(1);
                leftAlign(leftSurface, rightSurface);

            }
            else {
                Surface leftSurface = selectedSurfaceList.get(1);
                Surface rightSurface = selectedSurfaceList.get(0);
                leftAlign(leftSurface, rightSurface);
            }
        }
    }

    public void leftAlign(Surface leftSurface, Surface rightSurface) {
        Point2D leftPos = leftSurface.getLeftMostPoint();
        Point2D rightSurfaceTopLeftPoint = rightSurface.getTopLeftPoint();
        double widthDifference = rightSurfaceTopLeftPoint.getX() - leftPos.getX();

        rightSurfaceTopLeftPoint.setLocation(rightSurfaceTopLeftPoint.getX() - widthDifference, rightSurfaceTopLeftPoint.getY());
        rightSurface.snapToPoint(rightSurfaceTopLeftPoint);
    }

    public void rightAlignSelectedSurfaces(){
        if(this.getNumberOfSelectedSurfaces() == 2) {
            ArrayList<Surface> selectedSurfaceList = this.getSurfaceList();

            if (selectedSurfaceList.get(0).isToTheLeft(selectedSurfaceList.get(1))) {
                Surface leftSurface = selectedSurfaceList.get(0);
                Surface rightSurface = selectedSurfaceList.get(1);
                rightAlign(leftSurface, rightSurface);

            }
            else {
                Surface leftSurface = selectedSurfaceList.get(1);
                Surface rightSurface = selectedSurfaceList.get(0);
                rightAlign(leftSurface, rightSurface);
            }
        }
    }

    public void rightAlign(Surface leftSurface, Surface rightSurface) {
        Point2D rightPos = rightSurface.getRightMostPoint();
        Point2D leftSurfaceTopLeftPoint = leftSurface.getTopLeftPoint();
        double widthDifference = rightPos.getX() - leftSurface.getRightMostPoint().getX();

        leftSurfaceTopLeftPoint.setLocation(leftSurfaceTopLeftPoint.getX() + widthDifference, leftSurfaceTopLeftPoint.getY());
        leftSurface.snapToPoint(leftSurfaceTopLeftPoint);
    }

    private ArrayList<Surface> getSelectedSurfaces() {
        ArrayList<Surface> selectedSurfaceList = new ArrayList<Surface>();
        for (Surface surface : this.surfaceList) {
            if (surface.isSelected()) {
                selectedSurfaceList.add(surface);
            }
        }
        return selectedSurfaceList;
    }

    public void snapSelectedSurfaceToGrid(double gridGap) {
        for (Surface surface : this.surfaceList) {
            if (surface.isSelected()) {
                this.snapSurfaceToGrid(surface, gridGap);
            }
        }
    }

    private void snapSurfaceToGrid(Surface surface, double gridGap) {
        Point2D topLeftCornerPoint = surface.getTopLeftPoint();
        int horizontalGridSquare = (int) (topLeftCornerPoint.getX() / gridGap);
        int verticalGridSquare = (int) (topLeftCornerPoint.getY() / gridGap);

        Point2D[] gridSquareCorners = getGridSquareCorners(horizontalGridSquare, verticalGridSquare, gridGap);
        Point2D closestCorner = getClosestCorner(surface, gridSquareCorners);

        surface.snapToPoint(closestCorner);
    }

    private Point2D[] getGridSquareCorners(double horizontalGridSquare, double verticalGridSquare, double gridGap) {
        double[] gridSquarePos = getGridSquarePos(horizontalGridSquare, verticalGridSquare, gridGap);

        Point2D topLeft = new Point2D.Double(gridSquarePos[0], gridSquarePos[2]);
        Point2D topRight = new Point2D.Double(gridSquarePos[1], gridSquarePos[2]);
        Point2D bottomRight = new Point2D.Double(gridSquarePos[1], gridSquarePos[3]);
        Point2D bottomLeft = new Point2D.Double(gridSquarePos[0], gridSquarePos[3]);

        return new Point2D[]{topLeft, topRight, bottomRight, bottomLeft};
    }

    private double[] getGridSquarePos(double horizontalGridSquare, double verticalGridSquare, double gridGap) {
        double firstX = horizontalGridSquare * gridGap;
        double secondX = (horizontalGridSquare + 1) * gridGap;
        double firstY = (verticalGridSquare + 1) * gridGap;
        double secondY = verticalGridSquare * gridGap;

        return new double[]{firstX, secondX, firstY, secondY};
    }

    private Point2D getClosestCorner(Surface surface, Point2D[] gridSquareCorners) {
        double[] distanceFromSurfaceCorner = new double[4];
        for (int i = 0; i < 4; i++) {
            distanceFromSurfaceCorner[i] = surface.getDistanceFromPoint(gridSquareCorners[i]);
        }
        int closestCornerIndex = getMinimumElementIndex(distanceFromSurfaceCorner);
        return gridSquareCorners[closestCornerIndex];
    }

    private int getMinimumElementIndex(double[] unsortedArray) {
        double[] sortedArray = new double[4];
        System.arraycopy(unsortedArray, 0, sortedArray, 0, 4);
        Arrays.sort(sortedArray);
        for (int i = 0; i < sortedArray.length; i++) {
            if (unsortedArray[i] == sortedArray[0]) {
                return i;
            }
        }
        return 0;
    }

    public float getCurrentTileWidth() {
        float tileWidth = 0;
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surface : surfaceList) {
                tileWidth = surface.getTileType().getWidth();
            }
        }
        return tileWidth;
    }

    public float getCurrentTileHeight() {
        float tileHeight = 0;
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surface : surfaceList) {
                tileHeight = surface.getTileType().getHeight();
            }
        }
        return tileHeight;
    }

    public String getCurrentTileName() {
        String name = "";
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surface : surfaceList) {
                name = surface.getTileType().getName();
            }
        }
        return name;
    }

    public Color getCurrentTileColor() {
        Color color = Color.WHITE;
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surface : surfaceList) {
                color = surface.getTileType().getColor();
            }
        }
        return color;
    }

    public int getCurrentTileNumberPerBox() {
        int nbrOfTilePerBox = 0;
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surface : surfaceList) {
                nbrOfTilePerBox = surface.getTileType().getNbrTilesPerBox();
            }
        }
        return nbrOfTilePerBox;
    }

    public void unselectAllSurfaces() {
        for (Surface surface : this.surfaceList) {
            surface.unselect();
        }
    }

    public void separateSelectedSurface() {
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            ArrayList<Surface> copyList = new ArrayList<Surface>(surfaceList);
            for (Surface surface : copyList) {
                if (surface.isSelected()) {
                    ArrayList<Surface> compositeSurface = surface.getElementarySurface();
                    Surface baseSurface = compositeSurface.get(0);
                    for (int i = 0; i < compositeSurface.size(); i++) {
                        if (i == 0) {
                            Surface newBaseSurface = new Surface(baseSurface.getxPoints(), baseSurface.getyPoints(), baseSurface.getnPoints());
                            newBaseSurface.addCopy(newBaseSurface);
                            newBaseSurface.setTileType(baseSurface.getTileType());
                            newBaseSurface.setColor(baseSurface.getColor());
                            if (baseSurface.isCovered()) {
                                newBaseSurface.setPattern(baseSurface.getPattern());
                            }
                            surfaceList.add(newBaseSurface);
                        } else {
                            surfaceList.add(compositeSurface.get(i));
                        }
                    }
                    surfaceList.remove(surface);
                }
            }
        }
    }


    public void setMismatch(double mismatch) {
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surface : surfaceList) {
                if (surface.isSelected()) {
                    surface.setMismatch(mismatch);
                }
            }
        }
    }

    public void updateSelectedSurfacesPatternPosition(double deltaX, double deltaY) {
        int counter = getNumberOfSelectedSurfaces();
        if (counter == 1) {
            for (Surface surface : surfaceList) {
                if (surface.isSelected()) {
                    surface.translatePattern(deltaX, deltaY);
                }
            }
        }
    }
}
