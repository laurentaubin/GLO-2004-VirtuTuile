package domain.room;

import java.awt.*;
import java.util.List;

public class Surface {
    private String type;
    private List<Point> points;
    private boolean hole;
    private double zoom = 1d;

    public Surface(String type, List<Point> points, boolean hole){
        this.type = type;
        this.points = points;
        this.hole = hole;
    }

    public String getType() {
        return type;
    }

    public List<Point> getPoints(){
        return points;
    }

    public boolean isHole() {
        return hole;
    }
}

