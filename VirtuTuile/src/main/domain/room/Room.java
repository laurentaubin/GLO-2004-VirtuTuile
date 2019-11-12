package domain.room;

import domain.room.surface.ElementarySurface;
import domain.room.surface.RectangularSurface;
import domain.room.surface.Surface;

import javax.print.ServiceUIFactory;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Room {

    private ArrayList<Surface> surfaceList;
    private ArrayList<Surface> surfaceProjectionList;

    public Room() {
        surfaceList = new ArrayList<Surface>();
        surfaceProjectionList = new ArrayList<Surface>();
    }

    public void addSurfaceToList(Surface surface) {
        this.surfaceList.add(surface);
    }

    public void addRectangularProjection(Point point, int[] xPoints,int[] yPoints) {
        RectangularSurface rectangularSurfaceProjection = new RectangularSurface(point, xPoints, yPoints);
        Surface surfaceProjection = new Surface();
        surfaceProjection.addElementaryWholeSurface(rectangularSurfaceProjection);
        surfaceProjection.createPolygon();
        surfaceProjectionList.add(surfaceProjection);
    }

    public void addRectangularSurface(Point point, int[] xPoints, int[] yPoints) {
        RectangularSurface rectangularSurface = new RectangularSurface(point, xPoints, yPoints);
        Surface surface = new Surface();
        surface.addElementaryWholeSurface(rectangularSurface);
        surface.createPolygon();
        this.addSurfaceToList(surface);
    }

    public void addRectangularSurface(Point point, int width, int height) {
        RectangularSurface rectangularSurface = new RectangularSurface(point, width, height);
        Surface surface = new Surface();
        surface.addElementaryWholeSurface(rectangularSurface);
        this.addSurfaceToList(surface);
    }

    public void addIrregularSurface(Point point, int[] xPoints, int[] yPoints, int number_of_edges) {
        //TODO Code pour ajouter une surface irrégulière
    }

    public boolean isEmpty() {
        return surfaceList.isEmpty();
    }

    public ArrayList<Surface> getSurfaceList() {
        return surfaceList;
    }

    public ArrayList<Surface> getSurfaceProjectionList() {
        return surfaceProjectionList;
    }

    public void clearSurfaceProjectionList() {
        this.surfaceProjectionList.clear();
    }

    public int getNumberOfSurfaces() {
        return surfaceList.size();
    }

    void switchSelectionStatus(double x, double y, boolean isShiftDown) {
        for (Surface surfaceInRoom : this.surfaceList) {
            this.switchSelectionStatusIfContains(x, y, isShiftDown, surfaceInRoom);
            for (ElementarySurface elementarySurface : surfaceInRoom.getHoles()) {
                this.switchSelectionStatusIfContains(x, y, isShiftDown, surfaceInRoom);
            }
        }
    }

    private void switchSelectionStatusIfContains(double x, double y, boolean isShiftDown, Surface surfaceInRoom) {
        Point2D.Double point = new Point2D.Double(x, y);
        if (surfaceInRoom.getPolygon().contains(point)) {
            System.out.println("T'es dedans");
            surfaceInRoom.switchSelectionStatus();
        }
        else if (!isShiftDown){
            surfaceInRoom.unselect();
        }
    }

    void updateSelectedSurfacesPositions(double deltaX, double deltaY) {
        for (Surface surfaceInRoom : this.surfaceList) {
            if (surfaceInRoom.isSelected()) {
                updateSurfacePositions(deltaX, deltaY, surfaceInRoom);

                /*
                for (ElementarySurface elementarySurface : surfaceInRoom.getWholeSurfaces()) {
                    updateSurfacePositions(deltaX, deltaY, elementarySurface);
                }
                for (ElementarySurface elementarySurface : surfaceInRoom.getHoles()) {
                    updateSurfacePositions(deltaX, deltaY, elementarySurface);
                }

                 */
                //Pas encore implémentée
                surfaceInRoom.updateSurface();
            }
        }
    }

    private void updateSurfacePositions(double deltaX, double deltaY, Surface surface) {
            int[] x = surface.getPolygon().xpoints;
            int[] y = surface.getPolygon().ypoints;

            for (int i = 0; i < x.length; i++) {
                surface.getPolygon().xpoints[i] = (int) (x[i] + deltaX);
                surface.getPolygon().ypoints[i] = (int)(y[i] + deltaY);
            }

            for (ElementarySurface elementarySurface : surface.getWholeSurfaces()) {
                int[] xPoints = elementarySurface.xpoints;
                int[] yPoints = elementarySurface.ypoints;

                for (int i = 0; i < xPoints.length; i++) {
                    elementarySurface.xpoints[i] = (int) (x[i] + deltaX);
                    elementarySurface.ypoints[i] = (int)(y[i] + deltaY);
                }
                elementarySurface.updateElementarySurface();
            }
            surface.updateSurface();
    }

    public boolean surfaceSelecte(){
        boolean auMoinsUne = false;
        for(Surface surface: surfaceList){
            if(surface.isSelected()){
                auMoinsUne = true;
                break;
            }
        }
        return auMoinsUne;
    }


    void setPatternToSelectedSurfaces(Cover.Pattern pattern) {
        for (Surface surface : this.surfaceList) {
            if (surface.isSelected()) {
                System.out.println(pattern);
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

    /*

    public double[] getSelectedRectangularSurfaceDimensions() {
        double[] dimensionList = new double[2];
        for (Surface surface : this.surfaceList){
            if (surface.isSelected()){
                dimensionList[0] = surface.getWidth();
                dimensionList[1] = surface.getHeight();
            }
        }
        return dimensionList;
    }

     */

    /*

    public void setSelectedRectangularSurfaceDimensions(double[] dimensions) {
        for (Surface surface: this.surfaceList) {
            if (surface.isSelected()){
                surface.setDimensions(dimensions);
            }
        }
    }
     */

    public void deleteSurface(){
        for(int i = this.surfaceList.size() - 1; i >= 0; i--){
            if(this.surfaceList.get(i).isSelected()){
                surfaceList.remove(i);
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
