package domain.room;

import java.awt.Point;
import java.util.List;

public class RoomController {
    private final Room room;

    public RoomController(Room room) {
        this.room = room;
    }

    public RoomController(){
        room = new Room();
    }

    public void addRectangularSurface(Point mousePoint) {
    }

    public void addIrregularSurface(Point mousePoint) {

    }

    public List<Surface> getSurfaceList() {
        return room.getSurfaceList();
    }

    public int getNumberOfSurfaces() {
        return room.getNumberOfSurfaces();
    }

}
