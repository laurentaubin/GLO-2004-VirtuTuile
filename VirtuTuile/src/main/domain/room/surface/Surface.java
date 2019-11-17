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
    private Area area;
    private MainWindow.MeasurementUnitMode currentMode = MainWindow.MeasurementUnitMode.METRIC;
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

    public Surface(Point point) {
        this.position = point;
        wholeSurfaces = new ArrayList<ElementarySurface>();
        holes = new ArrayList<ElementarySurface>();
        this.tileType = TileType.createTileWithDefaultParameters();
        this.pattern = new DefaultPattern();
        this.color = (Color.WHITE);
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
            area = new Area(this.polygon);
        }
        else {
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
        if (this.isMerged()) {
            return this.area.getBounds2D();
        }
        return this.polygon.getBounds2D();
    }

    public Point getPosition() {
        return this.position;
    }

    public void setPosition() {

    }

    public boolean isHole() {
        return this.isHole();
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

    public void translate(double deltaX, double deltaY) {
        this.polygon.translate((int)deltaX, (int)deltaY);

        for (ElementarySurface wholeSurface : wholeSurfaces) {
            wholeSurface.translate(deltaX, deltaY);
        }

        for (ElementarySurface holeSurface : holes) {
            holeSurface.translate(deltaX, deltaY);
        }

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
}

