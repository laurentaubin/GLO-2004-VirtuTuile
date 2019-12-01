package domain.room;

import domain.drawing.SurfaceDrawer;
import domain.room.pattern.Pattern;
import domain.room.surface.Surface;
import gui.DrawingPanel;
import gui.MainWindow;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.util.ArrayList;

public class RoomController {
    private Room room;
    private SurfaceDrawer surfaceDrawer;

    private final int roomListLimit = 30;
    private ArrayList<Room> roomList;
    private int undoRedoPointer = -1;


    public RoomController(Room room) {
        this.room = room;
    }

    public RoomController(){
        room = new Room();
        roomList = new ArrayList<Room>();
        //roomList.add(new Room(room));
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void addRoom() {
        deleteElementsAfterPointer(this.undoRedoPointer);
        Room room = new Room(this.room);
        if (roomList.size() >= 30) {
            this.roomList.remove(0);
            this.roomList.add(room);
            this.undoRedoPointer = 29;
        }
        else {
            this.roomList.add(room);
            undoRedoPointer++;
        }
    }

    //https://stackoverflow.com/questions/11530276/how-do-i-implement-a-simple-undo-redo-for-actions-in-java
    private void deleteElementsAfterPointer(int undoRedoPointer)
    {
        if(roomList.isEmpty()) return;
        for(int i = roomList.size()-1; i > undoRedoPointer; i--)
        {
            roomList.remove(i);
        }
    }

    public void undo() {
        if (undoRedoPointer < 0) {
            return;
        }
        setRoom(roomList.get(undoRedoPointer));
        undoRedoPointer--;
    }

    public void redo() {
        if (undoRedoPointer == roomList.size() - 1) {
            return;
        }
        undoRedoPointer++;
        setRoom(roomList.get(undoRedoPointer));
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

    public void addSurface(Point point, double[] xPoints, double[] yPoints, int number_of_edges) {
        addRoom();
        if (number_of_edges == 4) {
            room.addRectangularSurface(xPoints, yPoints, number_of_edges);
        }
        else {
            //this.addIrregularSurface(point, xPoints, yPoints, number_of_edges);
        }
    }

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

    public void setGroutColor(Color color){
        room.setGroutColor(color);
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

    public boolean checkIfMouseAboveTile(int xPos, int yPos) {
        return room.checkIfMouseAboveTile(xPos, yPos);
    }

    public ArrayList<Double> getTileDimensions(int xPos, int yPos) {
        return room.getTileDimensions(xPos, yPos);
    }

    public void setSelectedSurfaceGroutWidth(double width) {
        room.setSelectedSurfaceGroutWidth(width);
    }

    public double getSelectedSurfaceGroutWidth() {
        return room.getSelectedSurfaceGroutWidth();
    }

    public void snapSelectedSurfaceToGrid(double gridGap) {
        room.snapSelectedSurfaceToGrid(gridGap);
    }

    public float getCurrentTileWidth() {
        return this.room.getCurrentTileWidth();
    }

    public float getCurrentTileHeight() {
        return this.room.getCurrentTileHeight();
    }

    public String getCurrentNameTile() {
        return this.room.getCurrentTileName();
    }

    public Color getCurrentTileColor() {
        return this.room.getCurrentTileColor();
    }

    public int getCurrentTilePerBox() {
        return this.room.getCurrentTileNumberPerBox();
    }

    public void unselectAllSurfaces() {
        room.unselectAllSurfaces();
    }

    public void separateSelectedSurface() {
        room.separateSelectedSurface();
    }
}

