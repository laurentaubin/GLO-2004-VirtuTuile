package domain.room.surface;

import java.awt.*;
import java.lang.annotation.Retention;

public class RectangularSurface extends ElementarySurface {
    private Point position;
    private double width;
    private double height;

    public RectangularSurface(Point position, int[] xPoints, int[] yPoints) {
        super(xPoints, yPoints, 4);
        this.position = position;
        this.width = Math.abs(xPoints[1] - xPoints[0]);
        this.height = Math.abs(yPoints[0] - yPoints[3]);
    }

    public RectangularSurface(Point point, int width, int height) {
        super(  new int[] {(int)point.getX(), (int)point.getX() + width, (int)point.getX(),(int)point.getX() + width},
                new int[] {(int) point.getY(), (int) point.getY(), (int)point.getY() - height, (int)point.getY() - height},
                4);
        this.width = width;
        this.height = height;
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

    public void setDimensions(double[] dimensions) {
        double deltaW = dimensions[0] - this.width;
        double deltaH = dimensions[1] - this.height;
        this.width = dimensions[0];
        this.height = dimensions[1];

        this.xpoints[1] += deltaW;
        this.xpoints[2] += deltaW;
        this.ypoints[2] += deltaH;
        this.ypoints[3] += deltaH;
    }

    @Override
    public double getArea() {
        return this.getHeight() * this.getWidth();
    }
}
