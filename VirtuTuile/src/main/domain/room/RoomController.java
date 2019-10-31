package domain.room;

import domain.room.surface.Surface;

import java.util.ArrayList;

public class RoomController {
    private final Room room;

    public RoomController(Room room) {
        this.room = room;
    }

    public RoomController(){
        room = new Room();
    }

    public void addSurface(int[] xPoints, int[] yPoints, int number_of_edges, String type) {
        if (type.equals("RECTANGULAR")){
            room.addRectangularSurface(xPoints, yPoints, number_of_edges, type);
        }
        else if (type.equals("IRREGULAR")){
            //TODO Code pour ajouter une surface irrégulière
        }
    }

    public void addIrregularSurface(int[] xPoints, int[] yPoints, int number_of_edges, String type){

    }

    public ArrayList<Surface> getSurfaceList() {
        return room.getSurfaceList();
    }

    public int getNumberOfSurfaces() {
        return room.getNumberOfSurfaces();
    }

    public void switchSelectionStatus(double x, double y, boolean isShiftDown) {
        room.switchSelectionStatus(x, y, isShiftDown);
    }

    public void updateSelectedSurfacesPositions(double deltaX, double deltaY) {
        room.updateSelectedSurfacesPositions(deltaX, deltaY);
    }

}
