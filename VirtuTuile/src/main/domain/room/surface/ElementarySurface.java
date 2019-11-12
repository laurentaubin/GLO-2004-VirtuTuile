package domain.room.surface;

import domain.room.Cover;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public abstract class ElementarySurface extends Polygon {
    private boolean isHole = false;

    public ElementarySurface(int[] xPoints, int[] yPoints, int numberOfEdges) {
        super(xPoints, yPoints, numberOfEdges);
    }


    public boolean contains(Point2D.Double point) {
        return this.contains(point);
    }

    public int[] getxCoord(){
        return this.xpoints;
    }

    public int[] getyCoord(){
        return this.ypoints;
    }

    public void setxCoord(int x, int index){
        this.xpoints[index] = x;
    }

    public void setyCoord(int y, int index){
        this.ypoints[index] = y;

    }

    public void updateSurface(){
        this.reset();
        for(int i = 0; i < this.getxCoord().length; i++){
            this.addPoint(this.xpoints[i], this.ypoints[i]);
        }
    }

    public boolean isHole() {
        return this.isHole;
    }

    public void setHole(boolean hole) {
        this.isHole = hole;
    }

    public void updateElementarySurface() {
        this.reset();
        for(int i = 0; i < this.xpoints.length; i++){
            this.addPoint(this.xpoints[i], this.ypoints[i]);
        }
    }
}

