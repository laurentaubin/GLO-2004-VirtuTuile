package domain.room.surface;

import domain.room.Cover;
import domain.room.Tile;
import domain.room.TileType;
import domain.room.pattern.Pattern;
import domain.room.pattern.StraightPattern;
import gui.MainWindow;
import util.UnitConverter;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Surface {
    private Point2D.Double position;
    private Color color;
    private boolean selectionStatus = false;
    private TileType tileType;
    private Cover cover;
    private Pattern pattern;
    private boolean isCovered = false;
    private boolean mergedStatus = false;
    private boolean haveHole = false;
    private Polygon polygon;
    private MainWindow.MeasurementUnitMode measurementMode;
    private Area area;
    private double width;
    private double height;
    private ArrayList<ElementarySurface> wholeSurfaces;
    private ArrayList<ElementarySurface> holes;
    private int numberSummit;
    private boolean isHole;

    //Attributs tests
    public double[] xPoints;
    public double[] yPoints;
    public int nPoints;
    private ArrayList<Surface> elementarySurface;
    private double groutWidth;



    public ArrayList<ElementarySurface> getWholeSurfaces() {
        return wholeSurfaces;
    }

    public ArrayList<ElementarySurface> getHoles() {
        return holes;
    }

    public Surface(double[] xPoints, double[] yPoints, int nbr_points) {
        Path2D.Double path = new Path2D.Double();
        for (int i = 0; i < nbr_points; i++) {
            if (i == 0) {
                this.position = new Point2D.Double(xPoints[i], yPoints[i]);
                path.moveTo(xPoints[i], yPoints[i]);
            }
            else {
                path.lineTo(xPoints[i], yPoints[i]);
            }
        }
        path.closePath();
        this.area = new Area(path);
        this.width = area.getBounds2D().getWidth();
        this.height = area.getBounds2D().getHeight();

        wholeSurfaces = new ArrayList<ElementarySurface>();
        holes = new ArrayList<ElementarySurface>();
        this.pattern = new StraightPattern();
        this.tileType = TileType.createTileWithDefaultParameters();
        this.color = (Color.WHITE);

        //Test
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        this.nPoints = nbr_points;
        this.elementarySurface = new ArrayList<Surface>();
        this.elementarySurface.add(this);
        this.groutWidth = 0d;
    }

    public Surface(Point2D.Double point) {
        this.position = point;
        wholeSurfaces = new ArrayList<ElementarySurface>();
        holes = new ArrayList<ElementarySurface>();
        this.pattern = new StraightPattern();
        this.tileType = TileType.createTileWithDefaultParameters();
        //this.pattern = new DefaultPattern();
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
        }

        this.area = new Area(this.polygon);
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
        return this.width;
        //return this.getBoundingRectangle().getWidth();
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

    public Rectangle getBoundingRectangle() {
        return this.area.getBounds();
    }

    public Point2D.Double getPosition() {
        return this.position;
    }

    public void setPosition() {

    }

    public boolean isHole() {
        return this.isHole;
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
        this.isCovered = true;
    }

    public boolean isCovered() {
        return this.isCovered;
    }

    public void translate(double deltaX, double deltaY) {
        this.translatePolygon(deltaX, deltaY);
    }

    /*
    public void translate(double deltaX, double deltaY, double pixelX, double pixelY) {
        this.translatePolygon(deltaX, deltaY);
        this.translateArea(pixelX, pixelY);
    }

     */

    private void translatePolygon(double deltaX, double deltaY) {
        if (this.area.getBounds2D().getX() + deltaX >= 0 && this.area.getBounds2D().getY() + deltaY >= 0) {
            //this.polygon.translate((int)deltaX, (int)deltaY);
            AffineTransform at = new AffineTransform(1, 0, 0, 1, deltaX, deltaY);

            for (ElementarySurface wholeSurface : wholeSurfaces) {
                wholeSurface.translate(deltaX, deltaY);
            }

            for (ElementarySurface holeSurface : holes) {
                holeSurface.translate(deltaX, deltaY);
            }

            for (Tile tile : getPattern().getVirtualTileList()) {
                tile.transform(at);
            }
            this.area.transform(at);
            this.position.setLocation(this.position.getX() + deltaX, this.position.getY() + deltaY);
            for (int i = 0; i < elementarySurface.size(); i++){
                if (i != 0) {
                    elementarySurface.get(i).translate(deltaX, deltaY);
                }
            }
            translateInitialPoints(deltaX, deltaY);
        }
    }

    public void translateInitialPoints(double deltaX, double deltaY) {
        double[] newXPosition = new double[this.nPoints];
        double[] newYPosition = new double[this.nPoints];
        for (int i = 0; i < nPoints; i++){
            newXPosition[i] = this.xPoints[i] + deltaX;
            newYPosition[i] = this.yPoints[i] + deltaY;
        }
        this.xPoints = newXPosition;
        this.yPoints = newYPosition;

    }

    /*
    private void translateArea(double deltaX, double deltaY) {
        AffineTransform at = new AffineTransform(1, 0, 0, 1, deltaX, deltaY);
        this.area.transform(at);
        this.position.translate((int)deltaX, (int)deltaY);
    }

     */

    public void setWidth(double enteredWidth) {
        double deltaX = enteredWidth / this.width;
        this.width = enteredWidth;

        AffineTransform at = new AffineTransform(deltaX, 0, 0, 1, 0, 0);
        double initialXPosition = this.area.getBounds().getX();
        this.area.transform(at);
        double newXPosition = this.area.getBounds().getX();
        double deltaPosition = (newXPosition - initialXPosition);

        AffineTransform atPosition = new AffineTransform(1, 0, 0, 1, -deltaPosition, 0);
        this.area.transform(atPosition);
    }

    public void setHeight(double height) {
        double deltaY = height / this.height;
        this.height = height;

        AffineTransform at = new AffineTransform(1, 0, 0, deltaY, 0, 0);
        double initialXPosition = this.area.getBounds().getY();
        this.area.transform(at);
        double newXPosition = this.area.getBounds().getY();
        double deltaPosition = (newXPosition - initialXPosition);

        AffineTransform atPosition = new AffineTransform(1, 0, 0, 1, 0, -deltaPosition);
        this.area.transform(atPosition);

    }

    public Dimension getDimensions() {
        Dimension dimension = new Dimension();
        dimension.setSize(this.getWidth(), this.getHeight());
        return dimension;
    }

    //Inspiré de: https://stackoverflow.com/questions/31209541/convert-from-java-awt-geom-area-to-java-awt-polygon
    public boolean intersect(Area otherArea) {
        Area testArea = new Area(this.area);
        testArea.intersect(otherArea);
        return !testArea.isEmpty();
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public int getNumberOfSummit() {
        int counter = 0;
        PathIterator pathIterator = this.area.getPathIterator(null);
        float[] floats = new float[6];
        while (!pathIterator.isDone()) {
            int type = pathIterator.currentSegment(floats);
            int x = (int) floats[0];
            int y = (int) floats[1];
            if (type != PathIterator.SEG_CLOSE) {
                counter += 1;
            }
            pathIterator.next();
        }
        return counter;
    }

    public void setIsHole() {
        this.isHole = true;
    }

    public void setIsHoleAsFalse() {
        this.isHole = false;
    }

    public void setHole(Surface surface) {
        this.wholeSurfaces.addAll(surface.getWholeSurfaces());
        this.holes.addAll(surface.getHoles());
        this.area.subtract(surface.getAreaTest());
    }

    public void setGroutWidth(double width) {
        this.groutWidth = width;
        //this.getPattern().setGroutWidth(width);
    }

    public double getGroutWidth() {
        return this.groutWidth;
    }

    public Point2D getBottomMostPoint() {
        Point2D[] points = this.getPoints();
        Point2D topMostPoint = new Point2D.Double();
        double maxHeight = 0.0;

        for (Point2D point : points) {
            double y = point.getY();
            if (y > maxHeight) {
                maxHeight = y;
                topMostPoint = point;
            }
        }
        return topMostPoint;
    }

    public Point2D getTopMostPoint() {
        Point2D[] points = this.getPoints();
        Point2D bottomMostPoint = new Point2D.Double();
        double minHeight = Double.POSITIVE_INFINITY;

        for (Point2D point : points) {
            double y = point.getY();
            if (y < minHeight) {
                minHeight = y;
                bottomMostPoint = point;
            }
        }
        return bottomMostPoint;
    }

    public Point2D getLeftMostPoint() {
        Point2D[] points = this.getPoints();
        Point2D leftMostPoint = new Point2D.Double();
        double minWidth = Double.POSITIVE_INFINITY;

        for (Point2D point : points) {
            double x = point.getX();
            if (x < minWidth) {
                minWidth = x;
                leftMostPoint = point;
            }
        }
        return leftMostPoint;
    }

    public Point2D getRightMostPoint() {
        Point2D[] points = this.getPoints();
        Point2D rightMostPoint = new Point2D.Double();
        double maxWidth = 0.0;

        for (Point2D point : points) {
            double x = point.getX();
            if (x > maxWidth) {
                maxWidth = x;
                rightMostPoint = point;
            }
        }
        return rightMostPoint;
    }

    public Point2D[] getPoints() {
        PathIterator iter = area.getPathIterator(null);
        float[] floats = new float[6];
        Point2D[] points = new Point2D[this.getNumberOfSummit()];
        int i = 0;

        while (!iter.isDone()) {
            int type = iter.currentSegment(floats);
            float x = floats[0];
            float y = floats[1];
            if (type != PathIterator.SEG_CLOSE) {
                points[i] = new Point2D.Float(x, y);
            }
            i++;
            iter.next();
        }
        return points;
    }

    public void snapToPoint(Point2D closestCorner) {
        double[] deltaArray = getDeltasFromPoint(closestCorner);
        this.translatePolygon(deltaArray[0], deltaArray[1]);
    }

    private double[] getDeltasFromPoint(Point2D closestCorner) {
        Point2D topLeftPoint = getTopLeftPoint();

        double deltaX = closestCorner.getX() - topLeftPoint.getX();
        double deltaY = closestCorner.getY() - topLeftPoint.getY();

        return new double[]{deltaX, deltaY};
    }

    public Point2D getTopLeftPoint() {
        PathIterator iter = area.getPathIterator(null);
        float[] floats = new float[6];

        int type = iter.currentSegment(floats);
        return new Point2D.Double(floats[0], floats[1]);
    }

    public double getDistanceFromPoint(Point2D point) {
        double[] deltas = getDeltasFromPoint(point);
        return Math.sqrt(Math.pow(deltas[0], 2) + Math.pow(deltas[1], 2));
    }

    public void addMergeSurfaceToList(Surface surfaceToAdd) {
        this.elementarySurface.add(surfaceToAdd);
    }

    public ArrayList<Surface> getElementarySurface() {
        return this.elementarySurface;
    }

    public boolean isToTheLeft(Surface otherSurface) {
        Point2D thisTopLeftPoint = this.getTopLeftPoint();
        Point2D otherTopLeftPoint = otherSurface.getTopLeftPoint();

        return (thisTopLeftPoint.getX() < otherTopLeftPoint.getX());
    }
    public boolean isBeneath(Surface otherSurface) {
        Point2D thisTopLeftPoint = this.getTopLeftPoint();
        Point2D otherTopLeftPoint = otherSurface.getTopLeftPoint();

        return (thisTopLeftPoint.getY() > otherTopLeftPoint.getY());
    }
}

