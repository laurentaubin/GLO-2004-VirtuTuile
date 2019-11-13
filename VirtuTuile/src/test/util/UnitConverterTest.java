/*
package util;

import gui.MainWindow;
import org.junit.jupiter.api.Test;
import util.UnitConverter;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class UnitConverterTest {

    @Test
    void pixelToInch() {
        assertEquals(1.8125, UnitConverter.pixelToInch(174d), 0.001);
    }

    @Test
    void pixelToMeter() {
        assertEquals(0.0635, UnitConverter.pixelToMeter(240d), 0.001);
    }

    @Test
    void meterToInch() {
        assertEquals(45d, UnitConverter.meterToInch(1.143), 0.001);
    }

    @Test
    void inchToMeter() {
        assertEquals(1.9812, UnitConverter.inchToMeter(78d), 0.001);
    }

    @Test
    void convertUnitToSelectedMode() {
        assertEquals(1.8125, UnitConverter.convertUnitToSelectedMode(174d, MainWindow.MeasurementUnitMode.IMPERIAL), 0.001);
        assertEquals(0.0635, UnitConverter.convertUnitToSelectedMode(240d, MainWindow.MeasurementUnitMode.METRIC), 0.001);
    }

    @Test
    void convertPointToSelectedMode() {
        Point2D pointToConvert = new Point2D.Double(167d, 249d);

        Point2D pointInInches = new Point2D.Double(1.739583, 2.59375);
        Point2D convertedPointInInches = UnitConverter.convertPointToSelectedMode(pointToConvert, MainWindow.MeasurementUnitMode.IMPERIAL);
        assertEquals(pointInInches.getX(), convertedPointInInches.getX(), 0.001);
        assertEquals(pointInInches.getY(), convertedPointInInches.getY(), 0.001);

        Point2D pointInMeters = new Point2D.Double(0.0441854, 0.065881);
        Point2D convertedPointInMeters = UnitConverter.convertPointToSelectedMode(pointToConvert, MainWindow.MeasurementUnitMode.METRIC);
        assertEquals(pointInMeters.getX(), convertedPointInMeters.getX(), 0.001);
        assertEquals(pointInMeters.getY(), convertedPointInMeters.getY(), 0.001);
    }
}

 */