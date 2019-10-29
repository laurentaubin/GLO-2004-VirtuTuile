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

    public void addRectangularSurface(String type, int width, int height, List<Point> points, boolean hole) {
        Surface newRectangularSurface = new Surface(type, width, height, points, hole);
        room.addSurface(newRectangularSurface);
    }

    public void addIrregularSurface(String type, List<Point> points, boolean hole){
        Surface newIrregularSurface = new Surface(type, points, hole);
        room.addSurface(newIrregularSurface);
    }

    public List<Surface> getSurfaceList() {
        return room.getSurfaceList();
    }

    public int getNumberOfSurfaces() {
        return room.getNumberOfSurfaces();
    }

    public void switchSelectionStatus(double x, double y) {
        room.switchSelectionStatus(x, y);
    }

}
