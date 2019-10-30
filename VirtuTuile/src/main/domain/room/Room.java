package domain.room;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Room {

    private ArrayList<Surface> surfaceList;

    public Room() {
        surfaceList = new ArrayList<Surface>();
    }

    public void addSurface(int[] xPoints, int[] yPoints, int number_of_summits) {
        Surface surface = new Surface(xPoints, yPoints, number_of_summits);
        surfaceList.add(surface);

    }

    public boolean isEmpty() {
        return surfaceList.isEmpty();
    }

    public ArrayList<Surface> getSurfaceList() {
        return surfaceList;
    }

    public int getNumberOfSurfaces() {
        return surfaceList.size();
    }

    void switchSelectionStatus(double x, double y, boolean isShiftDown) {
        for (Surface item : this.surfaceList) {
            if (item.contains(x, y)) {
                item.switchSelectionStatus();
            } else if (isShiftDown == false){
                item.deseleted();
            }
        }
    }

    void updateSelectedSurfacesPositions(double deltaX, double deltaY) {
        for (Surface surface : this.surfaceList){
            if (surface.isSelected()){
                surface.translate((int)deltaX, (int)deltaY);
            }
        }
    }
}
