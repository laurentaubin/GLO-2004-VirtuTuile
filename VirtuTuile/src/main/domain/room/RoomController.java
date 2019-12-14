package domain.room;


import domain.drawing.SurfaceDrawer;
import domain.room.pattern.Pattern;
import domain.room.surface.Surface;
import gui.DrawingPanel;
import gui.MainWindow;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;


public class RoomController implements Serializable{
    private Room room;
    private SurfaceDrawer surfaceDrawer;

    private final int roomListLimit = 30;
    private ArrayList<Room> roomList;
    private int undoRedoPointer = -1;

    private ArrayList<Point> pointList;


    public RoomController(Room room) {
        this.room = room;
        addRoom();
    }

    public RoomController(){
        room = new Room();
        roomList = new ArrayList<Room>();

        pointList = new ArrayList<Point>();
        addRoom();
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void addRoom() {
//        ArrayList<Surface> surfaceListCopy = new ArrayList<Surface>();
        Room room = new Room(this.room);
        if (roomList.size() >= 30) {
            this.roomList.remove(0);
            this.roomList.add(null);
            this.undoRedoPointer = 29;
        }
        else {
//            ArrayList<Surface> thisSurfaceList = this.getSurfaceList();
//            for (int i = 0; i < thisSurfaceList.size(); i++) {
//                Surface currentSurface = new Surface(thisSurfaceList.get(i));
//                surfaceListCopy.add(currentSurface);
//            }
//            room.setSurfaceList(surfaceListCopy);

            this.roomList.add(room);
            undoRedoPointer++;
        }
    }

    //https://stackoverflow.com/questions/11530276/how-do-i-implement-a-simple-undo-redo-for-actions-in-java
    private void deleteElementsAfterPointer(int undoRedoPointerState)
    {
        if(roomList.isEmpty()) {
            return;
        }

        boolean roomRemoved = false;
        for(int i = roomList.size() - 1; i >= undoRedoPointerState; i--)
        {
            if(i > undoRedoPointerState) {
                roomList.remove(i);
                roomRemoved = true;
            }
            else if (i == undoRedoPointerState && roomList.size() > 0 && roomRemoved) {
                roomList.remove(i);
                undoRedoPointer--;
                addRoom();
            }
        }
    }

    public void undo() {
        if (undoRedoPointer <= 0) {
            return;
        }
        undoRedoPointer--;
        setRoom(roomList.get(undoRedoPointer));
    }

    public void redo() {
        if (undoRedoPointer == roomList.size() - 1) {
            return;
        }
        undoRedoPointer++;
        setRoom(roomList.get(undoRedoPointer));
    }


    public void openMenuSelected() {
        String path = Paths.get("").toAbsolutePath().toString();

        JFileChooser chooser = new JFileChooser(path);
        chooser.showOpenDialog(null);
        File curFile = chooser.getSelectedFile();

        try {
        FileInputStream inputFile = new FileInputStream(new File(String.valueOf(curFile)));
        ObjectInputStream inputObject = new ObjectInputStream(inputFile);

        this.room = (Room) inputObject.readObject();
        System.out.println(curFile);
        curFile = new File(String.valueOf(curFile).substring(0, String.valueOf(curFile).lastIndexOf('.')));
        room.setPath(curFile);

        }
        catch (IOException e) {
            System.out.println(e);
        }
        catch (ClassNotFoundException e){
            System.out.println(e);
        }
    }


    public void saveSelected(){
        System.out.println(room.getPath());
        if(room.getPath() == null){
            saveAsSelected();
        }
        else{

            try {
                FileOutputStream fileOut = new FileOutputStream(room.getPath() + ".ser");
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(this.room);
                objectOut.close();
                System.out.println("The Object  was succesfully written to a file");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void saveAsSelected(){
        String path = Paths.get("").toAbsolutePath().toString();
        JFileChooser chooser = new JFileChooser(path);
        int validation = chooser.showSaveDialog(null);
        if (validation == JFileChooser.APPROVE_OPTION) {
            File pathFile = chooser.getSelectedFile();

            try {
                FileOutputStream fileOut = new FileOutputStream(pathFile + ".ser");
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(this.room);
                objectOut.close();
                System.out.println("The Object  was succesfully written to a file");
                room.setPath(pathFile);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void newProjectItemActionPerformed(){
        String[] options = {"Enregistrer", "Continuer"};
        int choix = JOptionPane.showOptionDialog(null, "Souhaitez-vous enregirtrer votre travail avant ?",
                "Attention!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

        if(choix == 0){
            saveSelected();
        }
        this.room = new Room();
    }

    public void draw(Graphics2D g, MainWindow.MeasurementUnitMode measurementUnitMode, DrawingPanel drawingPanel, double zoom, Point currentMousePoint) {
        ArrayList<Surface> surfaceList = getSurfaceList();
        if (surfaceDrawer == null) {
            surfaceDrawer = new SurfaceDrawer(this);
        }
        surfaceDrawer.setMeasurementUnitMode(measurementUnitMode);
        surfaceDrawer.draw(g, surfaceList, zoom, currentMousePoint);
    }

    public void addRectangularProjection(Point point, double[] xPoints, double[] yPoints) {
        room.addRectangularProjection(point, xPoints, yPoints);
    }

    public void addSurface(Point point, double[] xPoints, double[] yPoints, int number_of_edges) {
        deleteElementsAfterPointer(this.undoRedoPointer);
        if (number_of_edges == 4) {
            room.addRectangularSurface(xPoints, yPoints, number_of_edges);
        }
        else {
            //room.addIrregularSurface(point, xPoints, yPoints, number_of_edges);
        }
        addRoom();
    }

    public void addSurfaceOnGrid(Point position, double[] xDrawPoints, double[] yDrawPoints, int number_of_edges, double gridGap) {
        deleteElementsAfterPointer(this.undoRedoPointer);
        if (number_of_edges == 4) {
            room.addRectangularSurfaceOnGrid(xDrawPoints, yDrawPoints, number_of_edges, gridGap);
        }
        else {
            // room.addIrregularSurfaceOnGrid(point, xPoints, yPoints, number_of_edges, gridGap);
        }
        addRoom();
    }

    private void addIrregularSurface(Point point, int[] xPoints, int[] yPoints, int number_of_edges) {
        //room.addIrregularSurface(point, xPoints, yPoints, number_of_edges);
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
        deleteElementsAfterPointer(this.undoRedoPointer);
        room.deleteSurface();
        addRoom();
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

    public void setSelectedSurfacesWidthDistance(double widthDifference) {
        room.setSelectedSurfacesWidthDistance(widthDifference);
    }

    public void setSelectedSurfacesHeightDistance(double heightDifference) {
        room.setSelectedSurfacesHeightDistance(heightDifference);
    }

    public Dimension getSelectedSurfacesDistances() {
        return room.getSelectedSurfacesDistances();
    }

    public void combineSelectedSurfaces() {
        deleteElementsAfterPointer(this.undoRedoPointer);
        room.combineSelectedSurface();
        addRoom();
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

    public void setSquarePatternToSelectedSurface() {
        room.setSquarePatternToSelectedSurface();
    }

    public void setAnglePattern() {
        room.setAnglePattern();
    }

    public void setChevronPattern() {
        room.setChevronPattern();
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

    public void verticallyAlignSelectedSurfaces() {
        room.verticallyAlignSelectedSurfaces();
    }

    public void horizontallyAlignSelectedSurfaces() {
        room.horizontallyAlignSelectedSurfaces();
    }

    public void verticallyCenterSelectedSurfaces() {
        room.verticallyCenterSelectedSurfaces();
    }

    public void horizontallyCenterSelectedSurfaces() {
        room.horizontallyCenterSelectedSurfaces();
    }

    public void leftAlignSelectedSurfaces() {
        room.leftAlignSelectedSurfaces();
    }

    public void rightAlignSelectedSurfaces() {
        room.rightAlignSelectedSurfaces();
    }

    public void topAlignSelectedSurfaces() {
        room.topAlignSelectedSurfaces();
    }

    public void bottomAlignSelectedSurfaces() {
        room.bottomAlignSelectedSurfaces();
    }

    public ArrayList<Point> getPointList() {
        return this.pointList;
    }

    public void addPoint(Point point, double zoom) {
        if (!this.pointList.isEmpty()) {
            if (Math.abs(point.x - this.pointList.get(0).x) <= (5/zoom) && Math.abs(point.y - this.pointList.get(0).y) <= (5/zoom)) {
                room.addIrregularSurface(this.pointList);
                clearPointList();
                return;
            }
        }
        this.pointList.add(point);
    }

    public void clearPointList() {
        this.pointList.clear();
    }

    public void setMismatch(double mismatch) {
        room.setMismatch(mismatch);
    }

    public void centerTiles(){
        room.centerTiles();
    }

    public void updateSelectedSurfacesPatternPosition(double deltaX, double deltaY) {
        this.room.updateSelectedSurfacesPatternPosition(deltaX, deltaY);
    }

    public void dimensionIncorrectPaquet(){
        room.dimensionIncorrectPaquet();
    }

    public void updateTile(TileType tile, double width, double height, String name, int nbrTilesPerBox, Color color) {
        room.updateTile(tile, width, height, name, nbrTilesPerBox, color);
    }

    public void updateTileWidth(TileType selectedTileType, float tileWidth) {
        room.updateTileWidth(selectedTileType, tileWidth);
    }

    public void updateTileHeight(TileType tileType, float height) {
        room.updateTileHeight(tileType, height);
    }

    public void updateTileColor(TileType tileType, Color color) {
        room.updateTileColor(tileType, color);
    }

    public void updateTileName(TileType tileType, String name) {
        room.updateTileName(tileType, name);
    }

    public void updateNumberPerBox(TileType tileType, int numberPerBox) {
        room.updateTileNumberPerBox(tileType, numberPerBox);
    }
}

