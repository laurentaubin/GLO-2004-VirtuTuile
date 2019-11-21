package domain.room;

import domain.drawing.SurfaceDrawer;
import domain.room.pattern.Pattern;
import domain.room.surface.Surface;
import gui.DrawingPanel;
import gui.MainWindow;

import java.awt.*;
import java.util.ArrayList;

public class RoomController {
    private Room room;
    private SurfaceDrawer surfaceDrawer;
    //private final Room room;

    public RoomController(Room room) {
        this.room = room;
    }


    public RoomController(){
        room = new Room();
    }

    public void draw(Graphics2D g, MainWindow.MeasurementUnitMode measurementUnitMode, DrawingPanel drawingPanel, double zoom, Point currentMousePoint) {
        ArrayList<Surface> surfaceList = getSurfaceList();
        surfaceDrawer = new SurfaceDrawer(this);
        surfaceDrawer.setMeasurementUnitMode(measurementUnitMode);
        surfaceDrawer.draw(g, surfaceList, zoom, currentMousePoint);
    }

    public void addRectangularProjection(Point point, double[] xPoints, double[] yPoints) {
        room.addRectangularProjection(point, xPoints, yPoints);
    }

    /*
    public void addRectangularProjection(Point point, int[] xPoints, int[] yPoints) {
        room.addRectangularProjection(point, xPoints, yPoints);
    }

     */
    public void addRectangularSurface(Point point, double[] xPoints, double[] yPoints) {
        room.addRectangularSurface(point, xPoints, yPoints);
    }

    /*
    public void addRectangularSurface(Point point, int[] xPoints, int[] yPoints) {
        room.addRectangularSurface(point, xPoints, yPoints);
    }

     */

    /*
    public void addRectangularSurface(Point position, int width, int height){
        room.addRectangularSurface(position, width, height);
    }

     */

    public void addSurface(Point point, double[] xPoints, double[] yPoints, int number_of_edges) {
        if (number_of_edges == 4) {
            this.addRectangularSurface(point, xPoints, yPoints);
        }
        else {
            //this.addIrregularSurface(point, xPoints, yPoints, number_of_edges);
        }
    }

    /*
    public void addSurface(Point point, int[] xPoints, int[] yPoints, int number_of_edges) {
        if (number_of_edges == 4) {
            this.addRectangularSurface(point, xPoints, yPoints);
        }
        else {
            this.addIrregularSurface(point, xPoints, yPoints, number_of_edges);
        }
    }

     */

    private void addIrregularSurface(Point point, int[] xPoints, int[] yPoints, int number_of_edges) {
        room.addIrregularSurface(point, xPoints, yPoints, number_of_edges);
    }

    public ArrayList<Surface> getSurfaceList() {
        return room.getSurfaceList();
    }

    public ArrayList<Surface> getSurfaceProjectionList() {
        return room.getSurfaceProjectionList();
    }

    public void clearSurfaceProjectionList() {
        room.clearSurfaceProjectionList();
    }

    public void deleteSurface(){
        room.deleteSurface();
    }


    public int getNumberOfSurfaces() {
        return room.getNumberOfSurfaces();
    }

    public boolean surfaceSelecte(){
        return room.surfaceSelecte();
    }

    public void switchSelectionStatus(double x, double y, boolean isShiftDown) {
        room.switchSelectionStatus(x, y, isShiftDown);
    }

    public void updateSelectedSurfacesPositions(double deltaX, double deltaY) {
        room.updateSelectedSurfacesPositions(deltaX, deltaY);
    }

    /*
    public void updateSelectedSurfacesPositions(double deltaX, double deltaY, double pixelX, double pixelY) {
        room.updateSelectedSurfacesPositions(deltaX, deltaY, pixelX, pixelY);
    }

     */

    public void setPatternToSelectedSurfaces(Pattern pattern) {
        room.setPatternToSelectedSurfaces(pattern);
    }

    public void setGroutToSelectedSurfaces(Grout grout) {
        room.setGroutToSelectedSurfaces(grout);
    }

    public void setTileToSelectedSurfaces(Point point, float width, float height, Color color, String name, int nbrTilesPerBox) {
        room.setTileToSelectedSufaces(width, height, color, name, nbrTilesPerBox);
    }

    public void setMeasurementMode(MainWindow.MeasurementUnitMode mode) {
        room.setMeasurementMode(mode);
    }


    public double[] getSelectedRectangularSurfaceDimensions(){
        return room.getSelectedRectangularSurfaceDimensions();
    }

    /*
    public void setSelectedRectangularSurfaceDimensions(double[] dimensions){
        room.setSelectedRectangularSurfaceDimensions(dimensions);
    }
     */

    public boolean doesSelectedSurfacesIntersect(){
        return room.surfaceInTouch();
    }

    public int numberOfSelectedSurfaces(){
        return room.getNumberOfSelectedSurfaces();
    }

    public void setSelectedSurfaceColor(Color color){
        room.setSelectedSurfaceColor(color);

    }

    public Color getSelectedSurfaceColor() {
        return room.getSelectedSurfaceColor();
    }

    public void setSelectedSurfaceWidth(double enteredWidth) {
        room.setSelectedSurfaceWidth(enteredWidth);
    }

    public void setSelectedSurfaceHeight(double height) {
        room.setSelectedSurfaceHeight(height);
    }

    public Dimension getSelectedSurfaceDimensions() {
        return room.getSelectedSurfaceDimensions();
    }

    public void combineSelectedSurfaces() {
        room.combineSelectedSurface();
    }

    public void createTileFromUserInput(Color color, float width, float height, String name, int nbrTilesPerBox) {
        room.createTileFromUserInput(color, width, height, name, nbrTilesPerBox);
    }

    public ArrayList<TileType> getTileList() {
        return room.getTileList();
    }

    public void setSelectedTileToSelectedSurface(TileType selectedTileType) {
        room.setSelectedTileToSelectedSurface(selectedTileType);
    }

    public void setStraightPatternToSelectedSurface() {
        room.setStraightPatternToSelectedSurface();
    }

    public void setHorizontalPatternToSelectedSurface() {
        room.setHorizontalPatternToSelectedSurface();
    }

    public void setVerticalPatternToSelectedSurface() {
        room.setVerticalPatternToSelectedSurface();
    }

    public void setVerticalBrickPatternToSelectedSurface() {
        room.setVerticalBrickPatternToSelectedSurface();
    }

    public void setSelectedSurfaceAsHole() {
        room.setSelectedSurfaceAsHole();
    }

    public void setSelectedSurfaceAsWhole() {
        room.setSelectedSurfaceAsWhole();
    }
    public boolean getIfSelectedSurfaceIsAHole() {
        return room.getIfSelectedSurfaceIsAHole();
    }

    public int getNumberOfSelectedSurfaces() {
        return room.getNumberOfSelectedSurfaces();
    }

}

