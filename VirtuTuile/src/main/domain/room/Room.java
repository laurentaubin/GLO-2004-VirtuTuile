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

    public void addSurface(Surface surface) {
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
            } else if (!isShiftDown){
                item.unselect();
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

    void addPatternToSelectedSurfaces(Cover.Pattern pattern) {
        for (Surface surface : this.surfaceList) {
            if (surface.isSelected()) {
                surface.getCover().setPattern(pattern);
            }
        }
    }

    void addCoverToSelectedSurfaces(Cover cover) {
        for (Surface surface : this.surfaceList) {
            if (surface.isSelected()) {
                surface.setCover(cover);
            }
        }
    }
}
