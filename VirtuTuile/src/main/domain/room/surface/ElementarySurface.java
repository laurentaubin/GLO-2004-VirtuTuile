package domain.room.surface;

import domain.room.Cover;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public abstract class ElementarySurface extends Surface {
    private boolean isHole = false;
    Polygon polygon;

    public ElementarySurface(Point point) {
        super(point);
    }

    public boolean contains(Point2D.Double point) {
        return polygon.contains(point);
    }

    public void setPolygon(Polygon polygon){
        this.polygon = polygon;
    }

    public int[] getxCoord(){
        return this.polygon.xpoints;
    }

    public int[] getyCoord(){
        return this.polygon.ypoints;
    }

    public void updateSurface(){
        this.polygon.reset();
        for(int i = 0; i < this.getxCoord().length; i++){
            this.polygon.addPoint(this.getxCoord()[i], this.getyCoord()[i]);
        }
    }

    public boolean isHole() {
        return this.isHole;
    }

    public void setHole(boolean hole) {
        this.isHole = hole;
    }
}

