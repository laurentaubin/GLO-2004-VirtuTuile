package domain.room.surface;

import domain.room.Cover;
import gui.MainWindow;
import util.UnitConverter;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Surface {
    private Point position;
    private Color color;
    private boolean selectionStatus = false;
    private Cover cover;
    private boolean mergedStatus = false;
    private boolean haveHole = false;
    private Polygon polygon;
    private MainWindow.MeasurementUnitMode currentMode;
    private double width;
    private double height;
    private ArrayList<ElementarySurface> wholeSurfaces;
    private ArrayList<ElementarySurface> holes;

    public ArrayList<ElementarySurface> getWholeSurfaces() {
        return wholeSurfaces;
    }

    public ArrayList<ElementarySurface> getHoles() {
        return holes;
    }

    public Surface() {
        wholeSurfaces = new ArrayList<ElementarySurface>();
        holes = new ArrayList<ElementarySurface>();
    }

    public void updatePolygon(Polygon polygon) {
        if (!mergedStatus) {
            if (wholeSurfaces.isEmpty()) {
                // C'est un trou
                this.polygon = new Polygon(
                        this.getHoles().get(0).xpoints,
                        this.getHoles().get(0).ypoints,
                        this.getHoles().get(0).npoints
                );
            } else {
                // C'est une surface pleine
                this.polygon = new Polygon(
                        this.getWholeSurfaces().get(0).xpoints,
                        this.getWholeSurfaces().get(0).ypoints,
                        this.getWholeSurfaces().get(0).npoints
                );
            }
            // this.polygon = polygon;
        } else {
            this.polygon = polygon;
        }
    }



    private Polygon mergePolygon(Polygon polygon) {
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

    public Color getColor() {
        return this.color;
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

    public double getWidth() {
        return this.getBoundingRectangle().getWidth();
    }

    public double getHeight() {
        return this.getBoundingRectangle().getHeight();
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

    public double getArea() {
        double area = 0d;
        for (ElementarySurface wholeSurface : this.wholeSurfaces) {
            area += wholeSurface.getArea();
        }
        for (ElementarySurface hole : this.holes) {
            area -= hole.getArea();
        }

        return area;
    }

    public void setMeasurementMode(MainWindow.MeasurementUnitMode mode) {
        // C'est batard en maudit mais c'est pour éviter un bug quand on crée un surface et qu'on switch
        // Live le bug est dans on switch à la même unité (on crée une surface en mode métrique pis on reselectionne
        // le mode métrique = la surface disparait
        // L'alternative est de passer in MeasurementMode en argument quand on crée un objet Surface, pour setter currentMeasurementMode
        // Le problème c'est que c'est laid quel dog pcq faut le faire passer de MainWindow à RoomController à Room à Surface
        if (this.currentMode == null) {
            if (mode == MainWindow.MeasurementUnitMode.METRIC) {
                this.currentMode = MainWindow.MeasurementUnitMode.IMPERIAL;
            } else {
                this.currentMode = MainWindow.MeasurementUnitMode.METRIC;
            }
        }
        if (this.currentMode == mode) { return; }

        switch (mode) {
            case METRIC:
                this.polygon = UnitConverter.convertPolygonFromInchToMeter(this.polygon);
                this.currentMode = MainWindow.MeasurementUnitMode.METRIC;
                break;
            case IMPERIAL:
                this.polygon = UnitConverter.convertPolygonFromMeterToInch(this.polygon);
                this.currentMode = MainWindow.MeasurementUnitMode.IMPERIAL;
                break;
        }
    }

    public Rectangle2D getBoundingRectangle() {
        return this.polygon.getBounds2D();
    }

    public void updateSurfacePositions(double deltaX, double deltaY) {
        this.updatePolygonPoints(deltaX, deltaY);

        for (ElementarySurface wholeSurface : this.wholeSurfaces) {
            wholeSurface.updatePoints(deltaX, deltaY);
        }

        for (ElementarySurface hole : this.holes) {
            hole.updatePoints(deltaX, deltaY);
        }
    }

    private void updatePolygonPoints(double deltaX, double deltaY) {
        System.out.println(deltaX);
        for (int i = 0; i < this.polygon.npoints; i++) {
            System.out.println(this.polygon.xpoints[i]);
            this.polygon.xpoints[i] += deltaX;
            this.polygon.ypoints[i] += deltaY;
            System.out.println(this.polygon.xpoints[i]);
        }
    }

    public Point getPosition() {
        return this.position;
    }

    public void setPosition() {

    }

    public boolean isHole() {
        return this.haveHole;
    }
}

