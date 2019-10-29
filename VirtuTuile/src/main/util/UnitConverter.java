package util;

import gui.MainWindow;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;

import gui.MainWindow.MeasurementUnitMode;
import javafx.beans.binding.DoubleExpression;
import javafx.util.Pair;

public class UnitConverter {

    private static final MeasurementUnitMode METRIC = MeasurementUnitMode.METRIC;
    private static final MeasurementUnitMode IMPERIAL = MeasurementUnitMode.IMPERIAL;

    final float METER_RATIO = 204f;


    public ArrayList convertUnit(double xCoord, double yCoord, MainWindow.MeasurementUnitMode measurementUnitMode) {
        ArrayList list = new ArrayList<Double>();
        list.add(xCoord / METER_RATIO);
        list.add(yCoord / METER_RATIO);
        return list;
        }
}
