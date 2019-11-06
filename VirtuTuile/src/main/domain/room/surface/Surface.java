package domain.room.surface;

import domain.room.Cover;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public abstract class Surface {
    private Point position;
    private Color color;
    private boolean selectionStatus = false;
    private Cover cover;

    public Surface (Point point) {
        this.position = point;
    }

    public abstract boolean contains(Point2D.Double point);
    public abstract double getWidth();
    public abstract double getHeight();
    public abstract void setDimensions(double[] dimensions);
    public abstract int[] getxCoord();
    public abstract int[] getyCoord();
    public abstract void setxCoord(int x, int index);
    public abstract void setyCoord(int y, int index);
    public abstract Polygon getPolygon();

    public void setColor (Color color) {
        this.color = color;
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

    public Cover getCover() {
        return this.cover;
    }

    public void setCover(Cover cover){
        this.cover = cover;
    }

}

