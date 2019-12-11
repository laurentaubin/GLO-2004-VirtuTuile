package domain.room;

import java.awt.*;

public class
Grout {
    private Color color;
    private float width;

    public Grout(Color color, float width)
    {
        this.color = color;
        this.width = width;
    }

    static Grout createGroutWithDefaultParameters() {
        return new Grout(Color.WHITE, 1);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

}
