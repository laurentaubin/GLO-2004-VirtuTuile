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

    public void addSurface(int[] xPoints, int[] yPoints, int number_of_edges, String type, boolean isMouseReleased) {
        if (type.equals("RECTANGULAR")){
            room.addRectangularSurface(xPoints, yPoints, number_of_edges, isMouseReleased);
        }
        else if (type.equals("IRREGULAR")){
            room.addIrregularSurface(xPoints, yPoints, number_of_edges, isMouseReleased);
        }
    }

    public static ArrayList<Surface> getSurfaceList() {
        return room.getSurfaceList();
    }

    public static ArrayList<Surface> getSurfaceProjectionList() {return room.getSurfaceProjectionList();}

    public int getNumberOfSurfaces() {
        return room.getNumberOfSurfaces();
    }

    public void switchSelectionStatus(double x, double y, boolean isShiftDown) {
        room.switchSelectionStatus(x, y, isShiftDown);
    }

    public void updateSelectedSurfacesPositions(double deltaX, double deltaY) {
        room.updateSelectedSurfacesPositions(deltaX, deltaY);
    }

    public void setPatternToSelectedSurfaces(Cover.Pattern pattern) {
        room.setPatternToSelectedSurfaces(pattern);
    }

    public void setGroutToSelectedSurfaces(Grout grout) {
        room.setGroutToSelectedSurfaces(grout);
    }

    public void setTileToSelectedSurfaces(float width, float height, Color color, String name, int nbrTilesPerBox) {
        room.setTileToSelectedSufaces(width, height, color, name, nbrTilesPerBox);
    }

    public float[] getSelectedRectangularSurfaceDimensions(){
        return room.getSelectedRectangularSurfaceDimensions();
    }

    public void setSelectedRectangularSurfaceDimensions(float[] dimensions){
        room.setSelectedRectangularSurfaceDimensions(dimensions);
    }
}
