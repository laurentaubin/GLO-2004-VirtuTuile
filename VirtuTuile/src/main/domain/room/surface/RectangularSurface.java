package domain.room.surface;

public class RectangularSurface extends Surface{
    private float width;
    private float height;

    public RectangularSurface(int[] xPoints, int[] yPoints, int number_of_edge, String surfaceType) {
        super(xPoints, yPoints, number_of_edge, surfaceType);
        this.width = Math.abs(xPoints[1] - xPoints[0]);
        this.height = Math.abs(yPoints[0] - yPoints[3]);
    }

    public float getWidth(){
        return this.width;
    }

    public float getHeight(){
        return this.height;
    }

    public void setWidth(float width){
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
