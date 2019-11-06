package domain.room;

import domain.room.surface.RectangularSurface;
import domain.room.surface.Surface;

import java.util.ArrayList;

public class Room {

    private ArrayList<Surface> surfaceList;
    private ArrayList<Surface> surfaceProjectionList;

    public Room() {
        surfaceList = new ArrayList<Surface>(); surfaceProjectionList = new ArrayList<Surface>();}


    public void addSurfaceToList(Surface surface) {
        this.surfaceList.add(surface);
    }

    public void addRectangularSurface(int[] xPoints, int[] yPoints, int number_of_summits, boolean isMouseReleased) {
        RectangularSurface surface = new RectangularSurface(xPoints, yPoints, number_of_summits, "RECTANGULAR");

        if (isMouseReleased) {
            surfaceProjectionList.clear();
            addSurfaceToList(surface);
        } else surfaceProjectionList.add(surface);
    }

    public void addIrregularSurface(int[] xPoints, int[] yPoints, int number_of_edges, boolean isMouseReleased) {
        //TODO Code pour ajouter une surface irrégulière
    }

    public boolean isEmpty() {
        return surfaceList.isEmpty();
    }

    public ArrayList<Surface> getSurfaceList() {return surfaceList;}

    public ArrayList<Surface> getSurfaceProjectionList() {return surfaceProjectionList;}

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
        for (Surface surface : this.surfaceList) {
            if(surface.isSelected()) {
                int[] x = surface.getxCoord();
                int[] y = surface.getyCoord();

                for (int i = 0; i < x.length; i++) {
                    surface.setxCoord((int) (x[i] + deltaX), i);
                    surface.setyCoord((int) (y[i] + deltaY), i);
                }
            }
            surface.updateSurface();
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

    public float[] getSelectedRectangularSurfaceDimensions() {
        float[] dimensionList = new float[2];
        for (Surface surface : this.surfaceList){
            if (surface.isSelected() && surface.getType().equals("RECTANGULAR")){
                dimensionList[0] = surface.getWidth();
                dimensionList[1] = surface.getHeight();
            }
        }
        return dimensionList;
    }

    public void setSelectedRectangularSurfaceDimensions(float[] dimensions) {
        for (Surface surface: this.surfaceList) {
            if (surface.isSelected() && surface.getType().equals("RECTANGULAR")){
                surface.setDimensions(dimensions);
            }
        }
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
