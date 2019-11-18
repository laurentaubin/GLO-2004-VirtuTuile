package domain.room.surface;

import domain.room.Cover;
import domain.room.Tile;
import domain.room.TileType;
import domain.room.pattern.DefaultPattern;
import domain.room.pattern.Pattern;
import gui.MainWindow;
import util.UnitConverter;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class Surface {
    private Point position;
    private Color color;
    private boolean selectionStatus = false;
    private TileType tileType;
    private Cover cover;
    private Pattern pattern;
    private boolean mergedStatus = false;
    private boolean haveHole = false;
    private Polygon polygon;
    private MainWindow.MeasurementUnitMode measurementMode;
    private Area area;
    private double width;
    private double height;
    private ArrayList<ElementarySurface> wholeSurfaces;
    private ArrayList<ElementarySurface> holes;

    public Surface(Point point) {
        this.position = point;
        wholeSurfaces = new ArrayList<ElementarySurface>();
        holes = new ArrayList<ElementarySurface>();
        this.tileType = TileType.createTileWithDefaultParameters();
        this.pattern = new DefaultPattern();
        this.color = (Color.WHITE);
    }

    public ArrayList<ElementarySurface> getWholeSurfaces() {
        return wholeSurfaces;
    }

    public ArrayList<ElementarySurface> getHoles() {
        return holes;
    }


    public void updatePolygon() {
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
        }

        //En pixel selon le bounding rectangle
        this.width = this.polygon.getBounds2D().getWidth();
        this.height = this.polygon.getBounds2D().getHeight();
    }

    public Area getAreaTest() {
        return this.area;
    }

    public boolean isMerged() {
        return this.mergedStatus;
    }

    public void merge(Surface surface) {
        //TODO Tester les ajouts
        ArrayList<ElementarySurface> wholeSurface = surface.getWholeSurfaces();
        ArrayList<ElementarySurface> holeSurface = surface.getHoles();
        //addAll proposé par IDE
        this.wholeSurfaces.addAll(wholeSurface);
        this.holes.addAll(holeSurface);
        this.mergedStatus = true;
        this.area.add(surface.area);
    }

    public Shape getShape(){
        return this.area;
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

    public Polygon getPolygon() {
        return this.polygon;
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
        if (this.measurementMode == null) {
            if (mode == MainWindow.MeasurementUnitMode.METRIC) {
                this.measurementMode = MainWindow.MeasurementUnitMode.IMPERIAL;
            } else {
                this.measurementMode = MainWindow.MeasurementUnitMode.METRIC;
            }
        }
        if (this.measurementMode == mode) { return; }

        switch (mode) {
            case METRIC:
                this.polygon = UnitConverter.convertPolygonFromInchToMeter(this.polygon);
                this.measurementMode = MainWindow.MeasurementUnitMode.METRIC;
                break;
            case IMPERIAL:
                this.polygon = UnitConverter.convertPolygonFromMeterToInch(this.polygon);
                this.measurementMode = MainWindow.MeasurementUnitMode.IMPERIAL;
                break;
        }
    }

    public Rectangle2D getBoundingRectangle() {
        if (this.isMerged()) {
            return this.area.getBounds2D();
        }
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
        for (int i = 0; i < this.polygon.npoints; i++) {
            this.polygon.xpoints[i] += deltaX;
            this.polygon.ypoints[i] += deltaY;
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

    public TileType getTileType(){
        return this.tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public Pattern getPattern(){
        return this.pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public void translate(double deltaX, double deltaY, double pixelX, double pixelY) {
        this.translatePolygon(deltaX, deltaY);
        this.translateArea(pixelX, pixelY);
    }

    private void translatePolygon(double deltaX, double deltaY) {
        this.polygon.translate((int)deltaX, (int)deltaY);
        translateElementaryPolygons(deltaX, deltaY);
    }

    private void translateElementaryPolygons(double deltaX, double deltaY) {
        for (ElementarySurface wholeSurface : wholeSurfaces) {
            wholeSurface.translate(deltaX, deltaY);
        }

        for (ElementarySurface holeSurface : holes) {
            holeSurface.translate(deltaX, deltaY);
        }
    }

    private void translateArea(double deltaX, double deltaY) {
        AffineTransform at = new AffineTransform(1, 0, 0, 1, deltaX, deltaY);
        this.area.transform(at);
    }

    public void setWidth(double enteredWidth) {
        double deltaX = enteredWidth - this.width;
        this.width = enteredWidth;

        if (this.polygon.npoints == 4) {
            this.polygon.xpoints[1] += deltaX;
            this.polygon.xpoints[2] += deltaX;
        }
        //TODO modifier les dimensions des surfaces élémentaires
        //TODO Faire le code pour les surfaces irrégulières
    }

    public void setHeight(double height) {
        double deltaY = height - this.height;
        this.height = height;

        if (this.polygon.npoints == 4) {
            this.polygon.ypoints[2] += deltaY;
            this.polygon.ypoints[3] += deltaY;
        }
        //TODO modifier les dimensions des surfaces élémentaires
        //TODO Faire le code pour les surfaces irrégulières
    }

    public Dimension getDimensions() {
        Dimension dimension = new Dimension();
        dimension.setSize(this.getWidth(), this.getHeight());
        return dimension;
    }

    //Inspiré de: https://stackoverflow.com/questions/31209541/convert-from-java-awt-geom-area-to-java-awt-polygon
    public boolean intersect(Area otherArea) {
        boolean isIntersect = false;
        PathIterator pathIterator = otherArea.getPathIterator(null);
        float[] floats = new float[6];
        while (!pathIterator.isDone()) {
            int type = pathIterator.currentSegment(floats);
            int x = (int) floats[0];
            int y = (int) floats[1];
            if (type != PathIterator.SEG_CLOSE) {
                if (this.area.contains(x, y)) {
                    isIntersect = true;
                    break;
                }
            }
            pathIterator.next();
        }
        return isIntersect;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}

