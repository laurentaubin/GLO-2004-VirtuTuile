package domain.room.surface;

import domain.room.Cover;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Surface extends Polygon {
    private String type;
    private boolean hole;
    private double zoom = 1d;
    private boolean selectionStatus = false;
    private Cover cover;

    public Surface() {
    }

    public Surface(int[] x, int[] y, int number_of_summit, String type) {
        super(x, y, number_of_summit);
        this.type = type;

        System.out.println("Type de la surface créée: " + this.type);
    }

    public boolean isHole() {
        return hole;
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

    public String getType() {
        return this.type;
    }

    public float getWidth() {
        return 0f;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }
}

