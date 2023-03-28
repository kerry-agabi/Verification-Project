package cme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RateTest {
    private ArrayList<Period> reducedPeriods;
    private ArrayList<Period> normalPeriods;

    @BeforeEach
    void setUp() {
        reducedPeriods = new ArrayList<>();
        normalPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(6, 9));
        reducedPeriods.add(new Period(18, 21));
        normalPeriods.add(new Period(9, 18));
    }

    @Test
    void testConstructor_validInputs() {
        assertDoesNotThrow(() -> new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.STAFF, reducedPeriods, normalPeriods));
    }

    @Test
    void testConstructor_nullReducedPeriods() {
        assertThrows(IllegalArgumentException.class, () -> new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.STAFF, null, normalPeriods));
    }

    @Test
    void testConstructor_nullNormalPeriods() {
        assertThrows(IllegalArgumentException.class, () -> new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.STAFF, reducedPeriods, null));
    }

    @Test
    void testConstructor_nullNormalRate() {
        assertThrows(IllegalArgumentException.class, () -> new Rate(null, new BigDecimal("1.50"), CarParkKind.STAFF, reducedPeriods, normalPeriods));
    }

    @Test
    void testConstructor_nullReducedRate() {
        assertThrows(IllegalArgumentException.class, () -> new Rate(new BigDecimal("2.50"), null, CarParkKind.STAFF, reducedPeriods, normalPeriods));
    }

    @Test
    void testConstructor_negativeNormalRate() {
        assertThrows(IllegalArgumentException.class, () -> new Rate(new BigDecimal("-2.50"), new BigDecimal("1.50"), CarParkKind.STAFF, reducedPeriods, normalPeriods));
    }

    @Test
    void testConstructor_negativeReducedRate() {
        assertThrows(IllegalArgumentException.class, () -> new Rate(new BigDecimal("2.50"), new BigDecimal("-1.50"), CarParkKind.STAFF, reducedPeriods, normalPeriods));
    }

    @Test
    void testConstructor_normalRateLessThanOrEqualToReducedRate() {
        assertThrows(IllegalArgumentException.class, () -> new Rate(new BigDecimal("1.50"), new BigDecimal("1.50"), CarParkKind.STAFF, reducedPeriods, normalPeriods));
    }

    @Test
    void testConstructor_invalidReducedPeriods() {
        reducedPeriods.add(new Period(8, 10));
        assertThrows(IllegalArgumentException.class, () -> new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.STAFF, reducedPeriods, normalPeriods));
    }

    @Test
    void testConstructor_invalidNormalPeriods() {
        normalPeriods.add(new Period(17, 19));
        assertThrows(IllegalArgumentException.class, () -> new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.STAFF, reducedPeriods, normalPeriods));
    }

    @Test
    void testConstructor_overlappingPeriods() {
        reducedPeriods.add(new Period(9, 12));
        assertThrows(IllegalArgumentException.class, () -> new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.STAFF, reducedPeriods, normalPeriods));
    }


//Calculate

    @Test
    void testCalculate_noOverlap() {
        Rate rate = new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.STAFF, reducedPeriods, normalPeriods);
        Period periodStay = new Period(1, 5);
        BigDecimal expected = BigDecimal.ZERO;
        assertEquals(expected, rate.calculate(periodStay));
    }

    @Test
    void testCalculate_normalRateOnly() {
        Rate rate = new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.STAFF, reducedPeriods, normalPeriods);
        Period periodStay = new Period(9, 15);
        BigDecimal expected = new BigDecimal("15.00");
        assertEquals(expected, rate.calculate(periodStay));
    }

    @Test
    void testCalculate_reducedRateOnly() {
        Rate rate = new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.STAFF, reducedPeriods, normalPeriods);
        Period periodStay = new Period(6, 8);
        BigDecimal expected = new BigDecimal("3.00");
        assertEquals(expected, rate.calculate(periodStay));
    }

    @Test
    void testCalculate_bothRates() {
        Rate rate = new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.STAFF, reducedPeriods, normalPeriods);
        Period periodStay = new Period(7, 20);
        BigDecimal expected = new BigDecimal("25.50");
        assertEquals(expected, rate.calculate(periodStay));
    }

    @Test
    void testCalculate_overlapMultiplePeriods() {
        Rate rate = new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.STAFF, reducedPeriods, normalPeriods);
        Period periodStay = new Period(5, 23);
        BigDecimal expected = new BigDecimal("34.50");
        assertEquals(expected, rate.calculate(periodStay));
    }

    @Test
    void testCalculate_sameStartAndEndTime() {
        Rate rate = new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.STAFF, reducedPeriods, normalPeriods);
        Period periodStay = new Period(12, 12);
        BigDecimal expected = BigDecimal.ZERO;
        assertEquals(expected, rate.calculate(periodStay));
    }
}
