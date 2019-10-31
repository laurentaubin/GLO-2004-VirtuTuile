package domain.room.surface;

import domain.room.Cover;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Surface extends Polygon {
    private String type;
    private boolean hole;
    private double zoom = 1d;
    private boolean selectionStatus = false;
    private Cover cover;

    public Surface(){}

    public Surface(int[] x, int[] y, int number_of_summit, String type){
        super(x, y, number_of_summit);
        this.type = type;

        System.out.println("Type de la surface créée: " + this.type);
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

