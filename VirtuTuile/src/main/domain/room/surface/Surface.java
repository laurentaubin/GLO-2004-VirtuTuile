package domain.room.surface;

import domain.room.Cover;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Surface extends Polygon {
    private boolean hole;
    private double zoom = 1d;
    private boolean selectionStatus = false;
    private Cover cover;

    public Surface(){}

    public Surface(int[] x, int[] y, int number_of_summit){
        super(x, y, number_of_summit);
    }

    public boolean isHole() {
        return hole;
    }

    public void switchSelectionStatus() {
        this.selectionStatus =  !this.selectionStatus;
    }

    public boolean isSelected() {
        return this.selectionStatus;
    }

    public void deseleted() {
        this.selectionStatus= false;
    }


}

