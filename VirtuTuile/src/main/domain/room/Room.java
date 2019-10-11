package domain.room;

import java.util.LinkedList;
import java.util.List;

public class Room {

    private List<Surface> surfaceList;

    public Room() {
        surfaceList = new LinkedList<Surface>();
    }

    public void addSurface(Surface surface){
        surfaceList.add(surface);
    }

    public boolean isEmpty(){
        return surfaceList.isEmpty();
    }

    public List<Surface> getSurfaceList(){
        return surfaceList;
    }

    public int getNumberOfSurfaces() {
        return surfaceList.size();
    }
}
