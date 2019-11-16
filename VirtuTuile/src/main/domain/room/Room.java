package domain.room;

import domain.room.pattern.Pattern;
import domain.room.surface.ElementarySurface;
import domain.room.surface.RectangularSurface;
import domain.room.surface.Surface;
import gui.MainWindow;
import util.UnitConverter;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;


public class Room {

    private ArrayList<Surface> surfaceList;
    private ArrayList<Surface> surfaceProjectionList;
    private ArrayList<TileType> tileTypeList;

    public Room() {
        surfaceList = new ArrayList<Surface>();
        surfaceProjectionList = new ArrayList<Surface>();
        tileTypeList = new ArrayList<TileType>();
    }

    public void addSurfaceToList(Surface surface) {
        this.surfaceList.add(surface);
    }

    public void addSurfaceToProjectionList(Surface surface) {
        this.surfaceProjectionList.add(surface);
    }

    public void addRectangularProjection(Point point, int[] xPoints,int[] yPoints) {
        RectangularSurface rectangularSurfaceProjection = new RectangularSurface(point, xPoints, yPoints);
        Surface surfaceProjection = new Surface(point);
        surfaceProjection.addElementaryWholeSurface(rectangularSurfaceProjection);
        surfaceProjection.updatePolygon();
        this.addSurfaceToProjectionList(surfaceProjection);
    }

    public void addRectangularSurface(Point point, int[] xPoints, int[] yPoints) {
        RectangularSurface rectangularSurface = new RectangularSurface(point, xPoints, yPoints);
        Surface surface = new Surface(point);
        surface.addElementaryWholeSurface(rectangularSurface);
        surface.updatePolygon();
        this.addSurfaceToList(surface);
    }

    public void addRectangularSurface(Point point, int width, int height) {
        RectangularSurface rectangularSurface = new RectangularSurface(point, width, height);
        Surface surface = new Surface(point);
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
        }
    }

    private void switchSelectionStatusIfContains(double x, double y, boolean isShiftDown, Surface surfaceInRoom) {
        Point2D.Double point = new Point2D.Double(x, y);
        //TODO changer le OR pour une meilleure condition
        if (surfaceInRoom.getPolygon().contains(point) || surfaceInRoom.getAreaTest().contains(point)) {
            surfaceInRoom.switchSelectionStatus();
        }
        else if (!isShiftDown){
            surfaceInRoom.unselect();
        }
    }

    public void updateSelectedSurfacesPositions(double deltaX, double deltaY) {
        for (Surface surfaceInRoom : this.surfaceList) {
            if (surfaceInRoom.isSelected()) {
                surfaceInRoom.translate(deltaX, deltaY);
            }
        }
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


    void setPatternToSelectedSurfaces(Pattern pattern) {
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

    /*

    public void setSelectedRectangularSurfaceDimensions(double[] dimensions) {
        for (Surface surface: this.surfaceList) {
            if (surface.isSelected()){
                double deltaW = dimensions[0] - surface.getWidth();
                double deltaH = dimensions[1] - surface.getHeight();

                surface.getPolygon().xpoints[1] += deltaW;
                surface.getPolygon().xpoints[2] += deltaW;
                surface.getPolygon().ypoints[2] += deltaH;
                surface.getPolygon().ypoints[3] += deltaH;

                surface.updateSurface();
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

    public void setTileToSelectedSufaces(Point point, float width, float height, Color color, String name, int nbrTilesPerBox) {
        TileType tileType = new TileType(color, width, height, name, nbrTilesPerBox);
        for (Surface surface : this.surfaceList) {
            surface.getCover().setTileType(tileType);
            System.out.println(surface.getCover().getTileType().getName());
        }
    }

    public void setMeasurementMode(MainWindow.MeasurementUnitMode mode) {
        for (Surface surface : surfaceList) {
            surface.setMeasurementMode(mode);
            System.out.println(Arrays.toString(surface.getPolygon().xpoints));
        }
    }

    public void setSelectedSurfaceColor(Color color) {
        for (Surface surface : this.surfaceList) {
            if (surface.isSelected()) {
                surface.setColor(color);
            }
        }
    }

    public Color getSelectedSurfaceColor() {
        int counter = 0;
        Color color = Color.WHITE;
        for (Surface surface: this.surfaceList) {
            if (surface.isSelected()) {
                counter += 1;
            }
        }
        if (counter == 1) {
            for (Surface surface : this.surfaceList) {
                if (surface.isSelected()) {
                    color = surface.getColor();
                }
            }
        }
        return color;
    }

    public void setSelectedSurfaceWidth(double enteredWidth) {
        int counterOfSelectedSurfaces = 0;
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected()) {
                counterOfSelectedSurfaces += 1;
                if (counterOfSelectedSurfaces > 1) {
                    break;
                }
            }
        }
        if (counterOfSelectedSurfaces == 1) {
            for (Surface surfaceInRoom : surfaceList) {
                if (surfaceInRoom.isSelected()) {
                    surfaceInRoom.setWidth(enteredWidth);
                }
            }
        }
    }

    public void setSelectedSurfaceHeight(double height) {
        int counterOfSelectedSurfaces = 0;
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected()) {
                counterOfSelectedSurfaces += 1;
                if (counterOfSelectedSurfaces > 1) {
                    break;
                }
            }
        }
        if (counterOfSelectedSurfaces == 1) {
            for (Surface surfaceInRoom : surfaceList) {
                if (surfaceInRoom.isSelected()) {
                    surfaceInRoom.setHeight(height);
                }
            }
        }
    }

    public Dimension getSelectedSurfaceDimensions() {
        int counterOfSelectedSurfaces = 0;
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected()) {
                counterOfSelectedSurfaces += 1;
                if (counterOfSelectedSurfaces > 1) {
                    break;
                }
            }
        }
        if (counterOfSelectedSurfaces == 1) {
            for (Surface surfaceInRoom : surfaceList) {
                if (surfaceInRoom.isSelected()) {
                    return surfaceInRoom.getDimensions();
                }
            }
        }
        else {
            Dimension dimension = new Dimension();
            dimension.setSize(0d, 0d);
            return dimension;
        }
        return null;
    }
    public int numberOfSelectedSurfaces(){
        int count = 0;
        for(Surface surface:surfaceList){
            if(surface.isSelected()){
                count++;
            }
        }
        return count;
    }

    public boolean surfaceInTouch(){

        for (int i = 0; i < surfaceList.size() - 1; i++){
            if(!surfaceList.get(i).getAreaTest().intersects(surfaceList.get(i+1).getBoundingRectangle())){
                return false;
            }
        }
        return true;
    }

    public void combineSelectedSurface() {
        ArrayList<Surface> surfacesToCombine = new ArrayList<Surface>();
        for (Surface surfaceInRoom : surfaceList) {
            if (surfaceInRoom.isSelected()) {
                surfacesToCombine.add(surfaceInRoom);
            }
        }
        if (surfacesToCombine.size() > 1) {
            Surface baseSurface = surfacesToCombine.get(0);

            surfacesToCombine.remove(0);
            for (Surface surface : surfacesToCombine) {
                if (surfaceInTouch()) {
                    baseSurface.merge(surface);
                    surfaceList.remove(surface);
                }else{

                }
            }
        }
    }

}
