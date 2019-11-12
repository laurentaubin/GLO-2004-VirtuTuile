package domain.room.surface;

import domain.room.Cover;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Surface {
    private Point position;
    private Color color;
    private boolean selectionStatus = false;
    private Cover cover;
    private boolean mergedStatus = false;
    private boolean haveHole = false;
    private Polygon polygon;
    private ArrayList<ElementarySurface> wholeSurfaces;
    private ArrayList<ElementarySurface> holes;

    public ArrayList<ElementarySurface> getWholeSurfaces() {
        return wholeSurfaces;
    }

    public ArrayList<ElementarySurface> getHoles() {
        return holes;
    }

    public Surface () {
        wholeSurfaces = new ArrayList<ElementarySurface>();
        holes = new ArrayList<ElementarySurface>();
    }

    public void createPolygon(){
        if (!mergedStatus) {
            if (wholeSurfaces.isEmpty()){
                // C'est un trou
                this.polygon = this.getHoles().get(0);
            }
            else {
                // C'est une surface pleine
                this.polygon = this.getWholeSurfaces().get(0);
            }
        }
        else {
            this.polygon = this.mergePolygon();
        }
    }

    private Polygon mergePolygon() {
        //TODO algo pour créer un polygon résultant à partir d'une liste de polygon
        return new Polygon();
    }

    public void addElementaryWholeSurface(ElementarySurface elementarySurface) {
        wholeSurfaces.add(elementarySurface);
    }

    public void addHole(ElementarySurface elementarySurface) {
        if (!haveHole) {
            haveHole = true;
        }
        holes.add(elementarySurface);
    }

    public void setColor (Color color) {
        this.color = color;
    }

    public void switchSelectionStatus() {
        this.selectionStatus = !this.selectionStatus;
    }

    public boolean isSelected() {
        return this.selectionStatus;
    }

    public void unselect() {
        this.selectionStatus = false;
    }

    public void setSelectionStatus(boolean selectionStatus) {
        this.selectionStatus = selectionStatus;
    }

    public float getWidth() {
        return 0f;
    }

    public float getHeight() {
        return 0f;
    }

     public Cover getCover() {
        return this.cover;
    }

    public void setCover(Cover cover){
        this.cover = cover;
    }

    public void updateSurface() {
        this.polygon.reset();
        for(int i = 0; i < polygon.xpoints.length; i++){
            polygon.addPoint(polygon.xpoints[i], polygon.ypoints[i]);
        }
    }

    public Polygon getPolygon() {
        return polygon;
    }
}

