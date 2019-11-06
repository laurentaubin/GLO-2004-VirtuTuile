package domain.room;

import domain.room.surface.RectangularSurface;
import domain.room.surface.Surface;

import java.awt.*;
import java.util.ArrayList;

public class RoomController {
    private static Room room;
    //private final Room room;

    public RoomController(Room room) {
        this.room = room;
    }

    public RoomController(){
        room = new Room();
    }

    public void addRectangularSurface(Point position, int width, int height){
        room.addRectangularSurface(position, width, height);
    }

    public void addSurface(Point point, int[] xPoints, int[] yPoints, int number_of_edges) {
        room.addRectangularSurface(point, xPoints, yPoints, number_of_edges);
    }

    public static ArrayList<Surface> getSurfaceList() {
        return room.getSurfaceList();
    }

    public int getNumberOfSurfaces() {
        return room.getNumberOfSurfaces();
    }

    public void switchSelectionStatus(double x, double y, boolean isShiftDown) {
        room.switchSelectionStatus(x, y, isShiftDown);
    }

    /*
    public void updateSelectedSurfacesPositions(double deltaX, double deltaY) {
        room.updateSelectedSurfacesPositions(deltaX, deltaY);
    }

     */

    public void setPatternToSelectedSurfaces(Cover.Pattern pattern) {
        room.setPatternToSelectedSurfaces(pattern);
    }

    public void setGroutToSelectedSurfaces(Grout grout) {
        room.setGroutToSelectedSurfaces(grout);
    }

    public void setTileToSelectedSurfaces(Tile tile) {
        room.setTileToSelectedSufaces(tile);
    }

    public double[] getSelectedRectangularSurfaceDimensions(){
        return room.getSelectedRectangularSurfaceDimensions();
    }

    public void setSelectedRectangularSurfaceDimensions(double[] dimensions){
        room.setSelectedRectangularSurfaceDimensions(dimensions);
    }
}
