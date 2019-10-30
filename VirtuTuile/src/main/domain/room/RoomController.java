package domain.room;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class RoomController {
    private final Room room;

    public RoomController(Room room) {
        this.room = room;
    }

    public RoomController(){
        room = new Room();
    }

    public void addSurface(int[] xPoints, int[] yPoints, int number_of_summits) {
        room.addSurface(xPoints, yPoints, number_of_summits);
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
