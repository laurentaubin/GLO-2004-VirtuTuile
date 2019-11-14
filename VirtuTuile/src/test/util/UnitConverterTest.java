package util;

import gui.MainWindow;
import org.junit.jupiter.api.Test;
import util.UnitConverter;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class UnitConverterTest {

    MainWindow.MeasurementUnitMode METRIC = MainWindow.MeasurementUnitMode.METRIC;
    MainWindow.MeasurementUnitMode IMPERIAL = MainWindow.MeasurementUnitMode.IMPERIAL;

    private void assertArrayEqualsWithDelta(int[] expectedArray, int[] actualArray, int delta) {
        for (int i = 0; i < expectedArray.length; i++) {
            assertTrue(actualArray[i] - delta <= expectedArray[i] && actualArray[i] + delta >= expectedArray[i]);
        }
    }

    @Test
    void convertUnitToSelectedMode() {
        assertEquals(1.8125, UnitConverter.convertPixelToSelectedUnit(174, IMPERIAL), 0.001);
        assertEquals(0.0635, UnitConverter.convertPixelToSelectedUnit(240, METRIC), 0.001);
    }

    @Test
    void convertPointToSelectedMode() {
        Point2D pointToConvert = new Point2D.Double(167d, 249d);

        Point2D pointInInches = new Point2D.Double(1.739583, 2.59375);
        Point2D convertedPointInInches = UnitConverter.convertPointToSelectedUnit(pointToConvert, IMPERIAL);
        assertEquals(pointInInches.getX(), convertedPointInInches.getX(), 0.001);
        assertEquals(pointInInches.getY(), convertedPointInInches.getY(), 0.001);

        Point2D pointInMeters = new Point2D.Double(0.0441854, 0.065881);
        Point2D convertedPointInMeters = UnitConverter.convertPointToSelectedUnit(pointToConvert, METRIC);
        assertEquals(pointInMeters.getX(), convertedPointInMeters.getX(), 0.001);
        assertEquals(pointInMeters.getY(), convertedPointInMeters.getY(), 0.001);
    }

    @Test
    void convertPixelListToSelectedUnit() {
        int[] pixelList = new int[]{385, 304, 353, 832, 811, 431, 389, 111};
        int[] meterList = new int[]{1018, 804, 933, 2201, 2145, 1140, 1029, 293};
        int[] inchList = new int[]{40104, 31666, 36770, 86666, 84479, 44895, 40520, 11562};
        assertArrayEquals(meterList, UnitConverter.convertPixelListToSelectedUnit(pixelList, METRIC));
        assertArrayEquals(inchList, UnitConverter.convertPixelListToSelectedUnit(pixelList, IMPERIAL));
    }

    @Test
    void convertSelectedUnitListToPixel() {
        int[] pixelList = new int[]{385, 304, 353, 832, 811, 431, 389, 111};
        int[] meterList = new int[]{1018, 804, 933, 2201, 2145, 1140, 1029, 293};
        int[] inchList = new int[]{40104, 31666, 36770, 86666, 84479, 44895, 40520, 11562};

        meterList = UnitConverter.convertSelectedUnitListToPixel(meterList, METRIC);
        inchList = UnitConverter.convertSelectedUnitListToPixel(inchList, IMPERIAL);
        for (int i = 0; i < pixelList.length; i++) {
            assertTrue(pixelList[i] - 1 <= meterList[i] && pixelList[i] + 1 >= meterList[i]);
            assertTrue(pixelList[i] - 1 <= inchList[i] && pixelList[i] + 1 >= inchList[i]);
        }
    }

    @Test
    void convertPolygonToSelectedUnit() {
        int[] pixelXPoints = new int[]{154, 311, 311, 684, 684, 154};
        int[] pixelYPoints = new int[]{234, 234, 680, 680, 234, 234};
        int nPoints = pixelXPoints.length;
        Polygon pixelPolygon = new Polygon(pixelXPoints, pixelYPoints, nPoints);

        int[] meterXPoints = new int[]{407, 822, 822, 1809, 1809, 407};
        int[] meterYPoints = new int[]{619, 619, 1799, 1799, 619, 619};

        int[] inchXPoints = new int[]{16041, 32395, 32395, 71250, 71250, 16041};
        int[] inchYPoints = new int[]{24375, 24375, 70833, 70833, 24375, 24375};

        Polygon expectedMeterPolygon = new Polygon(meterXPoints, meterYPoints, nPoints);
        Polygon expectedInchPolygon = new Polygon (inchXPoints, inchYPoints, nPoints);

        Polygon actualMeterPolygon = UnitConverter.convertPolygonToSelectedUnit(pixelPolygon, METRIC);
        Polygon actualInchPolygon = UnitConverter.convertPolygonToSelectedUnit(pixelPolygon, IMPERIAL);

        assertArrayEqualsWithDelta(expectedInchPolygon.xpoints, actualInchPolygon.xpoints, 1);
        assertArrayEqualsWithDelta(expectedInchPolygon.ypoints, actualInchPolygon.ypoints, 1);

        assertArrayEqualsWithDelta(expectedMeterPolygon.xpoints, actualMeterPolygon.xpoints, 1);
        assertArrayEqualsWithDelta(expectedMeterPolygon.ypoints, actualMeterPolygon.ypoints, 1);

    }



    @Test
    void convertPolygonToPixel() {
        int[] meterXPoints = new int[]{407, 822, 822, 1809, 1809, 407};
        int[] meterYPoints = new int[]{619, 619, 1799, 1799, 619, 619};

        int[] inchXPoints = new int[]{16041, 32395, 32395, 71250, 71250, 16041};
        int[] inchYPoints = new int[]{24375, 24375, 70833, 70833, 24375, 24375};
        int nPoints = meterXPoints.length;

        Polygon meterPolygon = new Polygon(meterXPoints, meterYPoints, nPoints);
        Polygon inchPolygon = new Polygon (inchXPoints, inchYPoints, nPoints);

        int[] pixelXPoints = new int[]{154, 311, 311, 684, 684, 154};
        int[] pixelYPoints = new int[]{234, 234, 680, 680, 234, 234};
        Polygon expectedPixelPolygon = new Polygon(pixelXPoints, pixelYPoints, nPoints);

        Polygon actualPixelPolygonFromMeter = UnitConverter.convertPolygonToPixel(meterPolygon, METRIC);
        Polygon actualPixelPolygonFromInch = UnitConverter.convertPolygonToPixel(inchPolygon, IMPERIAL);

        assertArrayEqualsWithDelta(expectedPixelPolygon.xpoints, actualPixelPolygonFromInch.xpoints, 1);
        assertArrayEqualsWithDelta(expectedPixelPolygon.ypoints, actualPixelPolygonFromInch.ypoints, 1);

        assertArrayEqualsWithDelta(expectedPixelPolygon.xpoints, actualPixelPolygonFromMeter.xpoints, 1);
        assertArrayEqualsWithDelta(expectedPixelPolygon.ypoints, actualPixelPolygonFromMeter.ypoints, 1);


    }

    @Test
    void convertPolygonFromMeterToInch() {
        int[] meterXPoints = new int[]{407, 822, 822, 1809, 1809, 407};
        int[] meterYPoints = new int[]{619, 619, 1799, 1799, 619, 619};

        int[] inchXPoints = new int[]{16041, 32395, 32395, 71250, 71250, 16041};
        int[] inchYPoints = new int[]{24375, 24375, 70833, 70833, 24375, 24375};
        int nPoints = meterXPoints.length;

        Polygon meterPolygon = new Polygon(meterXPoints, meterYPoints, nPoints);
        Polygon expectedInchPolygon = new Polygon (inchXPoints, inchYPoints, nPoints);
        Polygon actualInchPolygon = UnitConverter.convertPolygonFromMeterToInch(meterPolygon);

        assertArrayEqualsWithDelta(expectedInchPolygon.xpoints, actualInchPolygon.xpoints, 50);
        assertArrayEqualsWithDelta(expectedInchPolygon.ypoints, actualInchPolygon.ypoints, 50);
    }

    @Test
    void convertPolygonFromInchToMeter() {
        int[] meterXPoints = new int[]{407, 822, 822, 1809, 1809, 407};
        int[] meterYPoints = new int[]{619, 619, 1799, 1799, 619, 619};

        int[] inchXPoints = new int[]{16041, 32395, 32395, 71250, 71250, 16041};
        int[] inchYPoints = new int[]{24375, 24375, 70833, 70833, 24375, 24375};
        int nPoints = meterXPoints.length;

        Polygon inchPolygon = new Polygon (inchXPoints, inchYPoints, nPoints);
        Polygon expectedMeterPolygon = new Polygon(meterXPoints, meterYPoints, nPoints);
        Polygon actualMeterPolygon = UnitConverter.convertPolygonFromInchToMeter(inchPolygon);

        assertArrayEqualsWithDelta(expectedMeterPolygon.xpoints, actualMeterPolygon.xpoints, 50);
        assertArrayEqualsWithDelta(expectedMeterPolygon.ypoints, actualMeterPolygon.ypoints, 50);

    }
}