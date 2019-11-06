package domain.room;

import domain.room.surface.RectangularSurface;
import domain.room.surface.Surface;

import java.util.ArrayList;

public class Room {

    private ArrayList<Surface> surfaceList;

    public Room() {
        surfaceList = new ArrayList<Surface>();
    }

    public void addSurfaceToList(Surface surface) {
        this.surfaceList.add(surface);
    }

    public void addRectangularSurface(int[] xPoints, int[] yPoints, int number_of_summits) {
        RectangularSurface surface = new RectangularSurface(xPoints, yPoints, number_of_summits, "RECTANGULAR");
        addSurfaceToList(surface);
    }

    public void addIrregularSurface(int[] xPoints, int[] yPoints, int number_of_edges) {
        //TODO Code pour ajouter une surface irrégulière
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

    void setPatternToSelectedSurfaces(Cover.Pattern pattern) {
        for (Surface surface : this.surfaceList) {
            if (surface.isSelected()) {
                surface.getCover().setPattern(pattern);
            }
        }
    }

    void addCoverToSelectedSurfaces(Cover cover){
        for (Surface surface : this.surfaceList) {
            if (surface.isSelected()) {
                surface.setCover(cover);
            }
        }
    }

    public float getSelectedRectangularSurfaceWidth() {
        float width = 0f;
        // Ca fuck toute si ya plus que 1 surface sélectionnée
        // tant qu'a ca fait return this.surfacelist.get(0).getWidth()
        for (Surface surface : this.surfaceList){
            if (surface.isSelected() && surface.getType().equals("RECTANGULAR")){
                width = surface.getWidth();
            }
        }
        System.out.println("width de la surface sélectionnée: " + width);
        return width;
    }

    public void setGroutToSelectedSurfaces(Grout grout) {
        for (Surface surface : this.surfaceList) {
            surface.getCover().setGrout(grout);
        }
    }

    public void setTileToSelectedSufaces(Tile tile) {
        for (Surface surface : this.surfaceList) {
            surface.getCover().setTile(tile);
        }
    }
}
