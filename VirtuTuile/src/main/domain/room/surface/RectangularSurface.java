package domain.room.surface;

import java.awt.*;
import java.lang.annotation.Retention;

public class RectangularSurface extends ElementarySurface {
    private double width;
    private double height;

    public RectangularSurface(Point position, int[] xPoints, int[] yPoints, int number_of_edge) {
        super(position);
        Polygon polygon = new Polygon(xPoints, yPoints, number_of_edge);
        super.setPolygon(polygon);
        this.width = Math.abs(xPoints[1] - xPoints[0]);
        this.height = Math.abs(yPoints[0] - yPoints[3]);
    }

    public RectangularSurface(Point point, int width, int height) {
        super(point);
        this.width = width;
        this.height = height;

        int xPosition = (int)point.getX();
        int yPosition = (int)point.getY();
        int[] xPoints = new int[] {xPosition, xPosition + width, xPosition, xPosition + width};
        int[] yPoints = new int[] {yPosition, yPosition, yPosition - height, yPosition - height};

        Polygon polygon = new Polygon(xPoints, yPoints, 4);
        super.setPolygon(polygon);
    }

    public Polygon getPolygon(){
        return this.polygon;
    }

    public double getWidth(){
        if (this.width < 0) {
            return (this.width * -1);
        }
        return this.width;
    }

    public void setWidth(float width){
        if (this.width < 0) {
            this.width = -width;
        }
        else {
            this.width = width;
        }
    }

    public double getHeight(){
        if (this.height < 0) {
            return (-this.height);
        }
        return this.height;
    }

    public void setHeight(float height) {
        if (this.height < 0){
            this.height = -height;
        }
        else {
            this.height = height;
        }
    }

    public int getTilesAcross() {
        return (int) Math.ceil(this.width / this.getCover().getTile().getWidth());
    }

    public int getTilesDown() {
        return (int) Math.ceil(this.height / this.getCover().getTile().getHeight());
    }

    public void setDimensions(double[] dimensions) {
        double deltaW = dimensions[0] - this.width;
        double deltaH = dimensions[1] - this.height;
        this.width = dimensions[0];
        this.height = dimensions[1];

        this.polygon.xpoints[1] += deltaW;
        this.polygon.xpoints[2] += deltaW;
        this.polygon.ypoints[2] += deltaH;
        this.polygon.ypoints[3] += deltaH;
    }

    public int[] getxCoord(){
        return this.polygon.xpoints;
    }

    public void setxCoord(int x, int index) {
        this.polygon.xpoints[index] = x;
    }

    public int[] getyCoord(){
        return this.polygon.ypoints;
    }

    public void setyCoord(int y, int index){
        this.polygon.ypoints[index] = y;
    }
}
