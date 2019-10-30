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
    private boolean selectionStatus = false;
    private Cover cover;

    public Surface(){}

    public Surface(int[] x, int[] y, int number_of_summit){
        super(x, y, number_of_summit);
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

    void switchSelectionStatus() {
        this.selectionStatus =  !this.selectionStatus;
    }

    public boolean isSelected() {
        return this.selectionStatus;
    }

    public void deseleted() {
        this.selectionStatus= false;
    }


}

