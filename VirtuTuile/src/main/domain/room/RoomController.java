package domain.room;

import domain.room.surface.RectangularSurface;
import domain.room.surface.Surface;

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
            room.addRectangularSurface(xPoints, yPoints, number_of_edges, type, isMouseReleased);
        }
        else if (type.equals("IRREGULAR")){
            room.addIrregularSurface(xPoints, yPoints, number_of_edges, isMouseReleased);
        }
    }

    public static ArrayList<Surface> getSurfaceList() {
        return room.getSurfaceList();
    }

    public static void deleteSurface(){ room.deleteSurface(); }

    public static ArrayList<Surface> getSurfaceProjectionList() {return room.getSurfaceProjectionList();}

    public int getNumberOfSurfaces() {
        return room.getNumberOfSurfaces();
    }

    public static boolean surfaceSelecte(){ return room.surfaceSelecte(); }

    public void switchSelectionStatus(double x, double y, boolean isShiftDown) {
        room.switchSelectionStatus(x, y, isShiftDown);
    }

    public void updateSelectedSurfacesPositions(double deltaX, double deltaY) {
        room.updateSelectedSurfacesPositions(deltaX, deltaY);
    }

    public void addPatternToSelectedSurfaces(Cover.Pattern pattern) {
        room.addPatternToSelectedSurfaces(pattern);
    }

    public float[] getSelectedRectangularSurfaceDimensions(){
        return room.getSelectedRectangularSurfaceDimensions();
    }

    public void setSelectedRectangularSurfaceDimensions(float[] dimensions){
        room.setSelectedRectangularSurfaceDimensions(dimensions);
    }
}
