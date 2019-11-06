package domain.room.surface;

public class RectangularSurface extends Surface{
    private float width;
    private float height;

    // Rectangular devrait avoir toujours number_of_edge = 4 non ?
    // Même chose pour type = "RECTANGULAR"
    public RectangularSurface(int[] xPoints, int[] yPoints, int number_of_edge, String surfaceType) {
        super(xPoints, yPoints, number_of_edge, surfaceType);
        this.width = Math.abs(xPoints[1] - xPoints[0]);
        this.height = Math.abs(yPoints[0] - yPoints[3]);
    }

    public float getWidth(){
        return this.width;
    }

    public void setWidth(float width){
        this.width = width;
    }

    public float getHeight(){
        return this.height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getTilesAcross() {
        return (int) Math.ceil(this.width / this.getCover().getTile().getWidth());
    }

    public int getTilesDown() {
        return (int) Math.ceil(this.height / this.getCover().getTile().getHeight());
    }

    public void setDimensions(float[] dimensions) {
        float deltaW = dimensions[0] - this.width;
        float deltaH = dimensions[1] - this.height;
        this.width = dimensions[0];
        this.height = dimensions[1];

        this.xpoints[1] += deltaW;
        this.xpoints[2] += deltaW;
        this.ypoints[2] += deltaH;
        this.ypoints[3] += deltaH;
    }
}
