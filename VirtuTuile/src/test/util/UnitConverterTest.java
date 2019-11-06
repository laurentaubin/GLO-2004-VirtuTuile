package util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UnitConverterTest {

    @Test
    void pixelToInch() {
        assertEquals(1.8125, UnitConverter.pixelToInch(174d), 0.001);
    }

    @Test
    void pixelToMeter() {
        assertEquals(0.0635, UnitConverter.pixelToMeter(240f), 0.001);
    }

    @Test
    void meterToInch() {
        assertEquals(45d, UnitConverter.meterToInch(1.143), 0.001);
    }

    @Test
    void inchToMeter() {
        assertEquals(1.9812, UnitConverter.inchToMeter(78d), 0.001);
    }
}