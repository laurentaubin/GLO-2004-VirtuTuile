package domain.room;

import java.awt.*;
import java.util.List;

public class Surface {
    private String type;
    private List<Point> points;
    private int width;
    private int height;
    private boolean hole;
    private double zoom = 1d;
    private boolean selectionStatus;

    public Surface(String type, List<Point> points, boolean hole){
        this.type = type;
        this.points = points;
        this.hole = hole;
        this.selectionStatus = false;
    }

    public Surface(String type, int width, int height, List<Point> points, boolean hole){
        this.type = type;
        this.points = points;
        this.width = width;
        this.height = height;
        this.selectionStatus = false;
        this.hole = hole;
    }

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

    void switchSelectionStatus() {
        this.selectionStatus =  !this.selectionStatus;
    }

    public boolean isSelected() {
        return this.selectionStatus;
    }


}

