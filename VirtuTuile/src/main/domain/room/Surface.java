package domain.room;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Surface extends Polygon {
    private String type;
    private List<Point> points;
    private int width;
    private int height;
    private boolean hole;
    private double zoom = 1d;
    private boolean selectionStatus;
    private Cover cover;

    public Surface(){}

    public Surface(int[] x, int[] y, int number_of_summit){
        super(x, y, number_of_summit);
        this.cover = Cover.createCoverWithDefaultParameters();
    }

    /*
    //Constructeur surface rectangulaire
    public Surface(String type, List<Point> points, boolean hole){
        this.type = type;
        this.points = points;
        this.hole = hole;
        this.selectionStatus = false;
    }

    //Constructeur surface irrégulière
    public Surface(String type, int width, int height, List<Point> points, boolean hole){
        this.type = type;
        this.points = points;
        this.width = width;
        this.height = height;
        this.selectionStatus = false;
        this.hole = hole;
    }
     */

    public String getType() {
        return type;
    }

    public List<Point> getPoints(){
        return points;
    }

    public boolean isHole() {
        return hole;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    /*
    boolean contains(double x, double y){
        return (xIsInsideSurfaceWidth(x) && yIsInsideSurfaceHeight(y));
    }

    boolean xIsInsideSurfaceWidth(double x) {
        boolean xIsInside = true;
        for (Point item : this.points) {
            if ((x > (item.getX() + this.width)) && ((x < item.getX()))) {
                xIsInside = false;
            }
        }
        return xIsInside;
    }

    boolean yIsInsideSurfaceHeight(double y) {
        boolean yIsInside = true;
        for (Point item : this.points){
            if ((y > item.getY() + this.height) && (y < item.getY())) {
                yIsInside = false;
            }
        }
        return yIsInside;
    }
     */

    void switchSelectionStatus() {
        this.selectionStatus =  !this.selectionStatus;
    }

    public boolean isSelected() {
        return this.selectionStatus;
    }

    public void setSelectionStatus(boolean selectionStatus) {
        this.selectionStatus = selectionStatus;
    }

    public void unselect() {
        this.selectionStatus = false;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }
}

