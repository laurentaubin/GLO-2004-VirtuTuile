package util;

//import gui.MainWindow;


import gui.MainWindow;

import java.awt.geom.Point2D;
import java.util.ArrayList;
/*import javafx.beans.binding.DoubleExpression;
import javafx.util.Pair;*/

public class UnitConverter {

    private static final float INCH_TO_METER = (float) 0.0254;
    private static final float METER_TO_INCH = (float) 39.3701;
    private static final float PIXEL_TO_METER = (float) 0.0002645833;
    private static final float PIXEL_TO_INCH = (float) 0.0104166667;

    public static double pixelToInch(double pixel) {
        return pixel * PIXEL_TO_INCH;
    }

    public static double pixelToMeter(double pixel) {
        ArrayList<Double> list = new ArrayList<>();
        return pixel * PIXEL_TO_METER;
    }

    public static double meterToInch(double meter) {
        return meter * METER_TO_INCH;
    }

    public static double inchToMeter(double inch) {
        return inch * INCH_TO_METER;
    }

    public static double convertUnitToSelectedMode(double unit, MainWindow.MeasurementUnitMode mode) {
        switch (mode) {
            case METRIC:
                return UnitConverter.pixelToMeter(unit);
            case IMPERIAL:
                return UnitConverter.pixelToInch(unit);
        }
        return unit;
    }

    public static Point2D convertPointToSelectedMode(Point2D point, MainWindow.MeasurementUnitMode mode) {
        double xPos = UnitConverter.convertUnitToSelectedMode(point.getX(), mode);
        double yPos = UnitConverter.convertUnitToSelectedMode(point.getY(), mode);
        return new Point2D.Double(xPos, yPos);

    }
}
