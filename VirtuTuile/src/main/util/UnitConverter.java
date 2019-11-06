package util;

import java.util.ArrayList;
/*import javafx.beans.binding.DoubleExpression;
import javafx.util.Pair;*/

public class UnitConverter {

    private final float INCH_TO_METER = (float) 0.0254;
    private final float METER_TO_INCH = (float) 39.3701;
    private final float PIXEL_TO_METER = (float) 0.0002645833;
    private final float PIXEL_TO_INCH = (float) 0.0104166667;

    public ArrayList<Double> pixelToInch(double xCoord, double yCoord) {
        ArrayList<Double> list = new ArrayList<>();
            list.add(xCoord * PIXEL_TO_INCH);
            list.add(yCoord * PIXEL_TO_INCH);
    }

    public ArrayList<Double> pixelToMeter(double xCoord, double yCoord) {
        ArrayList<Double> list = new ArrayList<>();
        list.add(xCoord * PIXEL_TO_METER);
        list.add(yCoord * PIXEL_TO_METER);
    }

    public ArrayList<Double> meterToInch(double xCoord, double yCoord) {
        ArrayList<Double> list = new ArrayList<>();
        list.add(xCoord * METER_TO_INCH);
        list.add(yCoord * METER_TO_INCH)
    }

    public ArrayList<Double> inchToMeter(double xCoord, double yCoord) {
        ArrayList<Double> list = new ArrayList<>();
        list.add(xCoord * INCH_TO_METER);
        list.add(yCoord * INCH_TO_METER);
    }
}
