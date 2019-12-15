package domain.room;

import domain.room.pattern.*;
import domain.room.surface.Surface;
import gui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;



public class Room implements Serializable{

    private ArrayList<Surface> surfaceList;
    private ArrayList<Surface> surfaceProjectionList;
    private ArrayList<TileType> tileTypeList;
    static File path;

    public Room() {
        surfaceList = new ArrayList<Surface>();
        surfaceProjectionList = new ArrayList<Surface>();
        tileTypeList = new ArrayList<TileType>();

    }

    public Room(Room roomToCopy) {
//        surfaceList = new ArrayList<Surface>(room.getSurfaceList());
//        surfaceProjectionList = new ArrayList<Surface>(room.getSurfaceProjectionList());
//        tileTypeList = new ArrayList<TileType>(room.getTileList());
        if(roomToCopy.surfaceList != null) {
            this.surfaceList = new ArrayList<>();
            for (Surface surface : roomToCopy.surfaceList) {
                Surface surfaceToCopy = new Surface(surface);
                this.surfaceList.add(surfaceToCopy);
            }
        }
        if(roomToCopy.surfaceProjectionList != null) {
            this.surfaceProjectionList = new ArrayList<>();
            for (Surface surfaceProjection : roomToCopy.surfaceProjectionList) {
                Surface surfaceProjectionToCopy = new Surface(surfaceProjection);
                this.surfaceProjectionList.add(surfaceProjectionToCopy);
            }
        }
        if(roomToCopy.tileTypeList != null) {
            this.tileTypeList = new ArrayList<>();
            for (TileType tileType : roomToCopy.tileTypeList) {
                TileType tileTypeToCopy = new TileType(tileType);
                this.tileTypeList.add(tileTypeToCopy);
            }
        }
    }

    public void desactivateInspectorMode(){
        for (Surface surface : surfaceList){
            surface.getTileType().setEtatInspector(false);
        }
    }

    public void activateInspectorMode(){
        for (Surface surface : surfaceList){
            surface.getTileType().setEtatInspector(true);
        }
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
            if (surfaceList.get(i).getArea().contains(x, y) && latestSurfaceSelected == true) {
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
        if (surfaceInRoom.getArea().contains(point)) {
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

    public void setPath(File path){
        this.path = path;
    }

    public File getPath(){
        return this.path;
    }

    public void setPatternToSelectedSurfaces(Pattern pattern) {
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

        for (Surface surface : this.surfaceList) { ;
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

public void setSelectedSurfacesWidthDistance(double widthDifference) {
    if (getNumberOfSelectedSurfaces() == 2) {
        ArrayList<Surface> selectedSurfaceList = this.getSelectedSurfaces();
        Surface firstSurface = selectedSurfaceList.get(0);
        Surface secondSurface = selectedSurfaceList.get(1);

        if (firstSurface.getBoundingRectangle().getMinX() <= secondSurface.getBoundingRectangle().getMinX()) {
            double firstSurfacePos = firstSurface.getBoundingRectangle().getMinX();
            Point2D secondSurfacePos = new Point2D.Double(firstSurfacePos + widthDifference, secondSurface.getBoundingRectangle().getMinY());
            secondSurface.boundingRectangleSnapToPoint(secondSurfacePos);
        }
        else {
            double secondSurfacePos = secondSurface.getBoundingRectangle().getMinX();
            Point2D firstSurfacePos = new Point2D.Double(secondSurfacePos + widthDifference, firstSurface.getBoundingRectangle().getMinY());
            firstSurface.boundingRectangleSnapToPoint(firstSurfacePos);
        }
    }
}

public void setSelectedSurfacesHeightDistance(double heightDifference) {
    if (getNumberOfSelectedSurfaces() == 2) {
        ArrayList<Surface> selectedSurfaceList = this.getSelectedSurfaces();
        Surface firstSurface = selectedSurfaceList.get(0);
        Surface secondSurface = selectedSurfaceList.get(1);

        if (firstSurface.getBoundingRectangle().getMinY() <= secondSurface.getBoundingRectangle().getMinY()) {
            double firstSurfacePos = firstSurface.getBoundingRectangle().getMinY();
            Point2D secondSurfacePos = new Point2D.Double(secondSurface.getBoundingRectangle().getMinX(), firstSurfacePos + heightDifference);
            secondSurface.boundingRectangleSnapToPoint(secondSurfacePos);
        }
        else {
            double secondSurfacePos = secondSurface.getBoundingRectangle().getMinY();
            Point2D firstSurfacePos = new Point2D.Double(firstSurface.getBoundingRectangle().getMinX(), secondSurfacePos + heightDifference);
            firstSurface.boundingRectangleSnapToPoint(firstSurfacePos);
        }
    }
}

    public Dimension getSelectedSurfacesDistances() {
        Dimension dimension = new Dimension(0, 0);
        double widthDifference = 0.0;
        double heightDifference = 0.0;
        if (getNumberOfSelectedSurfaces() == 2) {
            ArrayList<Surface> selectedSurfaceList = this.getSelectedSurfaces();
            Surface firstSurface = selectedSurfaceList.get(0);
            Surface secondSurface = selectedSurfaceList.get(1);

            if (firstSurface.getBoundingRectangle().getMinX() > secondSurface.getBoundingRectangle().getMinX()) {
                widthDifference = firstSurface.getBoundingRectangle().getMinX() - secondSurface.getBoundingRectangle().getMinX();
            }
            else if (firstSurface.getBoundingRectangle().getMinX() < secondSurface.getBoundingRectangle().getMinX()) {
                widthDifference = secondSurface.getBoundingRectangle().getMinX() - firstSurface.getBoundingRectangle().getMinX();
            }
            else {
                // Les surfaces sont alignées horizontalement (selon leur coin supérieur gauche)
                widthDifference = 0.0;
            }

            if (firstSurface.getBoundingRectangle().getMinY() > secondSurface.getBoundingRectangle().getMinY()) {
                heightDifference = firstSurface.getBoundingRectangle().getMinY() - secondSurface.getBoundingRectangle().getMinY();
            }
            else if (firstSurface.getBoundingRectangle().getMinY() < secondSurface.getBoundingRectangle().getMinY()) {
                heightDifference = secondSurface.getBoundingRectangle().getMinY() - firstSurface.getBoundingRectangle().getMinY();
            }
            else {
                // Les surfaces sont alignées verticalement (selon leur coin supérieur gauche)
                heightDifference = 0.0;
            }
            dimension.setSize(widthDifference, heightDifference);
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
            if (!baseSurface.intersect(surface.getArea())) {
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
            if (baseSurface.intersect(surface.getArea())) {
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
        }
    }

    public void setStraightPatternToSelectedSurface() {
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected() && !surfaceInRoom.isHole()) {
                StraightPattern straightPattern = new StraightPattern();
                straightPattern.generateTiles(surfaceInRoom.getBoundingRectangle(), surfaceInRoom.getTileType(), surfaceInRoom.getArea(), surfaceInRoom.getGroutWidth(), surfaceInRoom.getCoverCenter());
                surfaceInRoom.setPattern(straightPattern);
            }
        }
    }

    public void setHorizontalPatternToSelectedSurface() {
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected() && !surfaceInRoom.isHole()) {
                BrickPattern brickPattern = new BrickPattern(surfaceInRoom.getMismatch());
                brickPattern.generateTiles(surfaceInRoom.getBoundingRectangle(), surfaceInRoom.getTileType(), surfaceInRoom.getArea(), surfaceInRoom.getGroutWidth(), surfaceInRoom.getCoverCenter());
                surfaceInRoom.setPattern(brickPattern);
            }
        }
    }

    public void setVerticalPatternToSelectedSurface() {

        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected() && !surfaceInRoom.isHole()) {
                VerticalPattern verticalPattern = new VerticalPattern();
                verticalPattern.generateTiles(surfaceInRoom.getBoundingRectangle(), surfaceInRoom.getTileType(), surfaceInRoom.getArea(), surfaceInRoom.getGroutWidth(), surfaceInRoom.getCoverCenter());
                surfaceInRoom.setPattern(verticalPattern);
            }
        }
    }

    public void setVerticalBrickPatternToSelectedSurface() {
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected() && !surfaceInRoom.isHole()) {
                VerticalBrickPattern verticalBrickPattern = new VerticalBrickPattern(surfaceInRoom.getMismatch());
                verticalBrickPattern.generateTiles(surfaceInRoom.getBoundingRectangle(), surfaceInRoom.getTileType(), surfaceInRoom.getArea(), surfaceInRoom.getGroutWidth(), surfaceInRoom.getCoverCenter());
                surfaceInRoom.setPattern(verticalBrickPattern);
            }
        }
    }

    public void setAnglePattern() {
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected()) {
                InclinePattern inclinePattern = new InclinePattern();
                inclinePattern.generateTiles(surfaceInRoom.getBoundingRectangle(), surfaceInRoom.getTileType(), surfaceInRoom.getArea(), surfaceInRoom.getGroutWidth(),surfaceInRoom.getCoverCenter());
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
                    squarePattern.generateTiles(surface.getBoundingRectangle(), surface.getTileType(), surface.getArea(), surface.getGroutWidth(), surface.getCoverCenter());
                    surface.setPattern(squarePattern);
                }
                else{
                    incorrect = true;
                }
            }
        }
        if (incorrect){

//            String[] options = {"Ok"};
//            int indexReponse = JOptionPane.showOptionDialog(null, "Les dimensions des tuiles sont invalides, elles doivent avoir un ratio de 2:1",
//                    "Attention!",
//                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            dimensionIncorrectPaquet();
        }
    }

    public void setChevronPattern() {
        for (Surface surface : surfaceList) {
            if (surface.isSelected()) {
                ChevronPattern chevronPattern = new ChevronPattern();
                chevronPattern.generateTiles(surface.getBoundingRectangle(), surface.getTileType(), surface.getArea(), surface.getGroutWidth(), surface.getCoverCenter());
                surface.setPattern(chevronPattern);
            }
        }
    }

    public void dimensionIncorrectPaquet() {
        String[] options = {"Ok"};
        JOptionPane.showOptionDialog(null, "Les dimensions des tuiles sont invalides, elles doivent avoir un ratio de 2:1",
                "Attention!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
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
            if (selectedSurfaceList.get(0).isBeneath(selectedSurfaceList.get(1))) {
                Surface bottomSurface = selectedSurfaceList.get(0);
                Surface topSurface = selectedSurfaceList.get(1);

                double bottomSurfacePos = bottomSurface.getBoundingRectangle().getCenterY();
                Point2D topSurfacePos = new Point2D.Double(topSurface.getBoundingRectangle().getMinX(), bottomSurfacePos - (topSurface.getBoundingRectangle().getHeight() / 2.0));

                topSurface.boundingRectangleSnapToPoint(topSurfacePos);
            }
            else if (selectedSurfaceList.get(1).isBeneath(selectedSurfaceList.get(0))){
                Surface topSurface = selectedSurfaceList.get(0);
                Surface bottomSurface = selectedSurfaceList.get(1);

                double topSurfacePos = topSurface.getBoundingRectangle().getCenterY();
                Point2D bottomSurfacePos = new Point2D.Double(bottomSurface.getBoundingRectangle().getMinX(), topSurfacePos - (bottomSurface.getBoundingRectangle().getHeight() / 2.0));

                bottomSurface.boundingRectangleSnapToPoint(bottomSurfacePos);
            }
            else {
                return;
            }
        }
    }

    public void horizontallyCenterSelectedSurfaces() {
        if (this.getNumberOfSelectedSurfaces() == 2) {
            ArrayList<Surface> selectedSurfaceList = this.getSelectedSurfaces();
            if (selectedSurfaceList.get(0).isToTheLeft(selectedSurfaceList.get(1))) {
                Surface leftSurface = selectedSurfaceList.get(0);
                Surface rightSurface = selectedSurfaceList.get(1);

                double leftSurfacePos = leftSurface.getBoundingRectangle().getCenterX();
                Point2D rightSurfacePos = new Point2D.Double(leftSurfacePos - (rightSurface.getBoundingRectangle().getWidth() / 2.0), rightSurface.getBoundingRectangle().getMinY());

                rightSurface.boundingRectangleSnapToPoint(rightSurfacePos);
            }
            else if (selectedSurfaceList.get(1).isToTheLeft(selectedSurfaceList.get(0))){
                Surface rightSurface = selectedSurfaceList.get(0);
                Surface leftSurface = selectedSurfaceList.get(1);

                double rightSurfacePos = rightSurface.getBoundingRectangle().getCenterX();
                Point2D leftSurfacePos = new Point2D.Double(rightSurfacePos - (leftSurface.getBoundingRectangle().getWidth() / 2.0), leftSurface.getBoundingRectangle().getMinY());

                leftSurface.boundingRectangleSnapToPoint(leftSurfacePos);
            }
            else {
                return;
            }
        }
    }

    public void leftAlignSelectedSurfaces() {
        if (this.getNumberOfSelectedSurfaces() == 2) {
            ArrayList<Surface> selectedSurfaceList = this.getSelectedSurfaces();

            if (selectedSurfaceList.get(0).isToTheLeft(selectedSurfaceList.get(1))) {
                Surface leftSurface = selectedSurfaceList.get(0);
                Surface rightSurface = selectedSurfaceList.get(1);
                leftAlign(leftSurface, rightSurface);

            }
            else if (selectedSurfaceList.get(1).isToTheLeft(selectedSurfaceList.get(0))) {
                Surface leftSurface = selectedSurfaceList.get(1);
                Surface rightSurface = selectedSurfaceList.get(0);
                leftAlign(leftSurface, rightSurface);
            }
            else {
                // Les surfaces sont centrées horizontalement
                // On aligne à la surface ayant le coin supérieur le plus à gauche
                if (selectedSurfaceList.get(0).leftMostCorner(selectedSurfaceList.get(1))) {
                    Surface leftSurface = selectedSurfaceList.get(0);
                    Surface rightSurface = selectedSurfaceList.get(1);
                    leftAlign(leftSurface, rightSurface);
                }
                else if (selectedSurfaceList.get(0).leftMostCorner(selectedSurfaceList.get(1))) {
                    Surface leftSurface = selectedSurfaceList.get(1);
                    Surface rightSurface = selectedSurfaceList.get(0);
                    leftAlign(leftSurface, rightSurface);
                }
                else {
                    return;
                }
            }
        }
    }

    public void leftAlign(Surface leftSurface, Surface rightSurface) {
        double leftPos = leftSurface.getBoundingRectangle().getMinX();
        Point2D rightSurfaceTopLeftPoint = new Point2D.Double(rightSurface.getBoundingRectangle().getMinX(), rightSurface.getBoundingRectangle().getMinY());
        double widthDifference = rightSurfaceTopLeftPoint.getX() - leftPos;

        rightSurfaceTopLeftPoint.setLocation(rightSurfaceTopLeftPoint.getX() - widthDifference, rightSurfaceTopLeftPoint.getY());
        rightSurface.boundingRectangleSnapToPoint(rightSurfaceTopLeftPoint);
    }

    public void rightAlignSelectedSurfaces(){
        if(this.getNumberOfSelectedSurfaces() == 2) {
            ArrayList<Surface> selectedSurfaceList = this.getSelectedSurfaces();

            if (selectedSurfaceList.get(0).isToTheLeft(selectedSurfaceList.get(1))) {
                Surface leftSurface = selectedSurfaceList.get(0);
                Surface rightSurface = selectedSurfaceList.get(1);
                rightAlign(leftSurface, rightSurface);

            }
            else if (selectedSurfaceList.get(1).isToTheLeft(selectedSurfaceList.get(0))) {
                Surface leftSurface = selectedSurfaceList.get(1);
                Surface rightSurface = selectedSurfaceList.get(0);
                rightAlign(leftSurface, rightSurface);
            }
            else {
                // Les surfaces sont centrées horizontalement
                // On aligne à la surface ayant le coin inférieur le plus à droite
                if (selectedSurfaceList.get(0).rightMostCorner(selectedSurfaceList.get(1))) {
                    Surface rightSurface = selectedSurfaceList.get(0);
                    Surface leftSurface = selectedSurfaceList.get(1);
                    rightAlign(leftSurface, rightSurface);
                }
                else if (selectedSurfaceList.get(1).rightMostCorner(selectedSurfaceList.get(0))) {
                    Surface rightSurface = selectedSurfaceList.get(1);
                    Surface leftSurface = selectedSurfaceList.get(0);
                    rightAlign(leftSurface, rightSurface);
                }
                else {
                    return;
                }
            }
        }
    }

    public void rightAlign(Surface leftSurface, Surface rightSurface) {
        double rightPos = rightSurface.getBoundingRectangle().getMaxX();
        Point2D leftSurfaceTopLeftPoint = new Point2D.Double(leftSurface.getBoundingRectangle().getMinX(), leftSurface.getBoundingRectangle().getMinY());
        double widthDifference = rightPos - leftSurface.getBoundingRectangle().getMaxX();

        leftSurfaceTopLeftPoint.setLocation(leftSurfaceTopLeftPoint.getX() + widthDifference, leftSurfaceTopLeftPoint.getY());
        leftSurface.boundingRectangleSnapToPoint(leftSurfaceTopLeftPoint);
    }

    public void topAlignSelectedSurfaces() {
        if (this.getNumberOfSelectedSurfaces() == 2) {
            ArrayList<Surface> selectedSurfaceList = this.getSelectedSurfaces();

            if (selectedSurfaceList.get(0).isBeneath(selectedSurfaceList.get(1))) {
                Surface bottomSurface = selectedSurfaceList.get(0);
                Surface topSurface = selectedSurfaceList.get(1);
                topAlign(bottomSurface, topSurface);
            }
            else if (selectedSurfaceList.get(1).isBeneath(selectedSurfaceList.get(0))) {
                Surface bottomSurface = selectedSurfaceList.get(1);
                Surface topSurface = selectedSurfaceList.get(0);
                topAlign(bottomSurface, topSurface);
            }
            else {
                // Les surfaces sont centrées verticalement
                // On aligne à la surface ayant le coin supérieur le plus haut
                if (selectedSurfaceList.get(0).topMostCorner(selectedSurfaceList.get(1))) {
                    Surface topSurface = selectedSurfaceList.get(0);
                    Surface bottomSurface = selectedSurfaceList.get(1);
                    topAlign(bottomSurface, topSurface);
                }
                else if (selectedSurfaceList.get(1).topMostCorner(selectedSurfaceList.get(0))) {
                    Surface topSurface = selectedSurfaceList.get(1);
                    Surface bottomSurface = selectedSurfaceList.get(0);
                    topAlign(bottomSurface, topSurface);
                }
                else {
                    return;
                }
            }
        }
    }

    public void topAlign(Surface bottomSurface, Surface topSurface) {
        double topPos = topSurface.getBoundingRectangle().getMinY();
        Point2D bottomSurfaceTopLeftPoint = new Point2D.Double(bottomSurface.getBoundingRectangle().getMinX(), bottomSurface.getBoundingRectangle().getMinY());
        double heightDifference = bottomSurfaceTopLeftPoint.getY() - topPos;

        bottomSurfaceTopLeftPoint.setLocation(bottomSurfaceTopLeftPoint.getX(), bottomSurfaceTopLeftPoint.getY() - heightDifference);
        bottomSurface.boundingRectangleSnapToPoint(bottomSurfaceTopLeftPoint);
    }

    public void bottomAlignSelectedSurfaces() {
        if (this.getNumberOfSelectedSurfaces() == 2) {
            ArrayList<Surface> selectedSurfaceList = this.getSelectedSurfaces();

            if (selectedSurfaceList.get(0).isBeneath(selectedSurfaceList.get(1))) {
                Surface bottomSurface = selectedSurfaceList.get(0);
                Surface topSurface = selectedSurfaceList.get(1);
                bottomAlign(bottomSurface, topSurface);
            }
            else if (selectedSurfaceList.get(1).isBeneath(selectedSurfaceList.get(0))) {
                Surface bottomSurface = selectedSurfaceList.get(1);
                Surface topSurface = selectedSurfaceList.get(0);
                bottomAlign(bottomSurface, topSurface);
            }
            else {
                // Les surfaces sont centrées verticalement
                // On aligne à la surface ayant le coin inférieur le plus bas
                if (selectedSurfaceList.get(0).bottomMostCorner(selectedSurfaceList.get(1))) {
                    Surface bottomSurface = selectedSurfaceList.get(0);
                    Surface topSurface = selectedSurfaceList.get(1);
                    bottomAlign(bottomSurface, topSurface);
                }
                else if (selectedSurfaceList.get(1).topMostCorner(selectedSurfaceList.get(0))) {
                    Surface bottomSurface = selectedSurfaceList.get(1);
                    Surface topSurface = selectedSurfaceList.get(0);
                    bottomAlign(bottomSurface, topSurface);
                }
                else {
                    return;
                }
            }
        }
    }

    public void bottomAlign(Surface bottomSurface, Surface topSurface) {
        double bottomPos = bottomSurface.getBoundingRectangle().getMaxY();
        Point2D topSurfaceTopLeftPoint = new Point2D.Double(topSurface.getBoundingRectangle().getMinX(), topSurface.getBoundingRectangle().getMinY());
        double heightDifference = bottomPos - topSurface.getBoundingRectangle().getMaxY();

        topSurfaceTopLeftPoint.setLocation(topSurfaceTopLeftPoint.getX(), topSurfaceTopLeftPoint.getY() + heightDifference);
        topSurface.boundingRectangleSnapToPoint(topSurfaceTopLeftPoint);
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


    public void centerTiles(){
        for(Surface surface : surfaceList){
            if(surface.isSelected()){
                surface.setCoverCenter();
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

    public void updateTile(TileType tile, double width, double height, String name, int nbrTilesPerBox, Color color) {
        tile.setWidth((float) width);
        tile.setHeight((float) height);
        tile.setName(name);
        tile.setNbrTilesPerBox(nbrTilesPerBox);
        tile.setColor(color);
    }

    public void updateTileWidth(TileType tileType, double width) {
        tileType.setWidth((float)width);
    }

    public void updateTileHeight(TileType tileType, double height) {
        tileType.setHeight((float)height);
    }

    public void updateTileName(TileType tileType, String name) {
        tileType.setName(name);
    }

    public void updateTileNumberPerBox(TileType tileType, int num) {
        tileType.setNbrTilesPerBox(num);
    }

    public void updateTileColor(TileType tileType, Color color) {
        tileType.setColor(color);
    }

    public int getSelectedSurfaceNbTile() {
        for (Surface surface : this.surfaceList) {
            if (surface.isSelected()) {
                return surface.getNumberOfTiles();
            }
        }
        return 0;
    }

    public int getSelectedSurfaceNbBox() {
        for (Surface surface : this.surfaceList) {
            if (surface.isSelected()) {
                return (int) Math.ceil(surface.getNumberOfBoxes());
            }
        }
        return 0;
    }

    public int getAllSurfaceNbTile() {
        int sum = 0;
        for (Surface surface : this.surfaceList) {
            sum += surface.getNumberOfTiles();
        }
        return sum;
    }

    public int getAllSurfaceNbBox() {
        double sum = 0;
        for (Surface surface : this.surfaceList) {
            sum += surface.getNumberOfBoxes();
        }
        return (int) Math.ceil(sum);
    }
}
