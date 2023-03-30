package cme;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class Main {

    private ArrayList<Period> reducedPeriods;
    private ArrayList<Period> normalPeriods;
// Calculate Method First

    //1
    @Test
    void testValidInputRates() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        reducedPeriods.add(new Period(14, 18));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(10, 14));
        normalPeriods.add(new Period(18, 22));
        Rate rate = new Rate(BigDecimal.valueOf(10), BigDecimal.valueOf(8), CarParkKind.STAFF, reducedPeriods, normalPeriods);
        BigDecimal actual = rate.calculate(new Period(11, 13));
        BigDecimal expected = BigDecimal.valueOf(10);
        assertEquals(expected, actual);
    }
    //2
    @Test
    void testPeriodStay_reducedPeriodStart() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        reducedPeriods.add(new Period(14, 18));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(10, 14));
        normalPeriods.add(new Period(18, 22));
        Rate rate = new Rate(BigDecimal.valueOf(10), BigDecimal.valueOf(8), CarParkKind.MANAGEMENT, reducedPeriods, normalPeriods);
        BigDecimal actual = rate.calculate(new Period(7, 10));
        BigDecimal expected = BigDecimal.valueOf(24);
        assertEquals(expected, actual);

    }
    //3
    @Test
    void testCalculatePeriodStay_reducedPeriodEnd() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        reducedPeriods.add(new Period(14, 18));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(10, 14));
        normalPeriods.add(new Period(18, 22));
        Rate rate = new Rate(BigDecimal.valueOf(10), BigDecimal.valueOf(8), CarParkKind.VISITOR, reducedPeriods, normalPeriods);
        BigDecimal actual = rate.calculate(new Period(10, 14));
        BigDecimal expected = BigDecimal.valueOf(15.0);
        assertEquals(expected, actual);

    }
    //4
    @Test
    void testCalculatePeriodStay_normalPeriodStart() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        reducedPeriods.add(new Period(14, 18));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(10, 14));
        normalPeriods.add(new Period(18, 22));
        Rate rate = new Rate(BigDecimal.valueOf(10), BigDecimal.valueOf(8), CarParkKind.STAFF, reducedPeriods, normalPeriods);
        BigDecimal actual = rate.calculate(new Period(18, 22));
        BigDecimal expected = BigDecimal.valueOf(10);
        assertEquals(expected, actual);

    }


    //5
    @Test
    void testCalculateEnd_NormalPeriodEnd() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        reducedPeriods.add(new Period(14, 18));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(10, 14));
        normalPeriods.add(new Period(18, 22));
        Rate rate = new Rate(BigDecimal.valueOf(10), BigDecimal.valueOf(8), CarParkKind.STAFF, reducedPeriods, normalPeriods);
        BigDecimal actual = rate.calculate(new Period(18, 22));
        BigDecimal expected = BigDecimal.valueOf(10);
        assertEquals(expected, actual);

    }
    //6
    @Test
    void testCalculateInvalidPeriod() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(10, 13));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(7, 10));
        normalPeriods.add(new Period(13, 16));
        Rate rate = new Rate(BigDecimal.valueOf(10), BigDecimal.valueOf(7), CarParkKind.VISITOR, reducedPeriods, normalPeriods);
        assertThrows(IllegalArgumentException.class, () -> rate.calculate(new Period(10, 7)));
    }
    //7
    @Test
    void testCalculatePeriodNotAllowed() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(10, 13));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(7, 10));
        normalPeriods.add(new Period(13, 16));
        Rate rate = new Rate(BigDecimal.valueOf(10), BigDecimal.valueOf(8), CarParkKind.MANAGEMENT, reducedPeriods, normalPeriods);
        assertThrows(IllegalArgumentException.class, () -> rate.calculate(new Period(6, 7)));
    } // Bug it checks whether the calculate () method throws an IllegalArgumentException when provided with a period that is not allowed. The expected behavior is to throw an exception
    // Fixed!
    //8
    @Test
    void testCalculateNullPeriod() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(10, 13));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(7, 10));
        normalPeriods.add(new Period(13, 16));
        Rate rate = new Rate(BigDecimal.valueOf(10), BigDecimal.valueOf(8), CarParkKind.STAFF, reducedPeriods, normalPeriods);
        assertThrows(NullPointerException.class, () -> rate.calculate(null));
    }
    //9
    @Test
    void testCalculatePeriodWithinNormalPeriod() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(3, 9));
        reducedPeriods.add(new Period(12, 15));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(9, 12));
        normalPeriods.add(new Period(15, 18));
        Rate rate = new Rate(BigDecimal.valueOf(10), BigDecimal.valueOf(5), CarParkKind.VISITOR, reducedPeriods, normalPeriods);
        BigDecimal actual = rate.calculate(new Period(10, 11));
        BigDecimal expected = BigDecimal.valueOf(0);
        assertEquals(expected, actual);
    }
    //10
    @Test
    void testCalculatePeriodWithinReducedPeriod() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(10, 13));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(7, 10));
        Rate rate = new Rate(BigDecimal.valueOf(10), BigDecimal.valueOf(5), CarParkKind.MANAGEMENT, reducedPeriods, normalPeriods);
        BigDecimal actual = rate.calculate(new Period(10,11));
        BigDecimal expected = BigDecimal.valueOf(5);
        assertEquals(expected, actual);
    }

    //11
    @Test
    void testCalculatePeriodOverlappingBothPeriods() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(9, 12));
        reducedPeriods.add(new Period(15, 18));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(3, 9));
        normalPeriods.add(new Period(13, 15));
        Rate rate = new Rate(BigDecimal.valueOf(10), BigDecimal.valueOf(5), CarParkKind.STAFF, reducedPeriods, normalPeriods);
        assertThrows(IllegalArgumentException.class, () -> rate.calculate(new Period(12, 23)));
    } //Bug The test checks whether the calculate() method throws an IllegalArgumentException when provided with a period that overlaps both normal and reduced periods.

    //12
    @Test
    void testCalculateSingleValueForPeriodStay() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(10, 14));
        reducedPeriods.add(new Period(18, 22));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(7, 10));
        normalPeriods.add(new Period(14, 18));
        Rate rate = new Rate(BigDecimal.valueOf(10), BigDecimal.valueOf(8), CarParkKind.STUDENT, reducedPeriods, normalPeriods);
        assertThrows(IllegalArgumentException.class, () -> rate.calculate(new Period(0, 11)));
    } // Bug The test checks whether the calculate() method throws an IllegalArgumentException when provided with a period that has a duration of 0

    //13
    @Test
    void testCalculateWithBoundaryPeriod() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(10, 14));
        reducedPeriods.add(new Period(18, 22));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(7, 10));
        normalPeriods.add(new Period(14, 18));
        Rate rate = new Rate(BigDecimal.valueOf(1), BigDecimal.valueOf(0), CarParkKind.STAFF, reducedPeriods, normalPeriods);
        BigDecimal actual = rate.calculate(new Period(9, 11));
        BigDecimal expected = BigDecimal.valueOf(1);
        assertEquals(expected, actual);
    }

    //14
    @Test
    void testCalculateWithNullReducedPeriod() {
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(7, 10));
        normalPeriods.add(new Period(13, 16));
        Rate rate = new Rate(BigDecimal.valueOf(10), BigDecimal.valueOf(8), CarParkKind.STUDENT, null, normalPeriods);
        assertThrows(IllegalArgumentException.class, () -> rate.calculate(new Period(11, 12)));
    }




    // Rate Constructor
    // 1
    @Test
    void testRateConstructor() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7,10));
        reducedPeriods.add(new Period(13,16));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(10,13));
        BigDecimal normalRate = BigDecimal.valueOf(10);
        BigDecimal reducedRate = BigDecimal.valueOf(8);
        CarParkKind carParkKind = CarParkKind.STAFF;
        Rate rate = new Rate(normalRate, reducedRate, carParkKind, reducedPeriods, normalPeriods);


    }

    //2
    @Test
    void testNegativeNumber() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(10, 18));
        reducedPeriods.add(new Period(21, 23));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(7, 10));
        normalPeriods.add(new Period(19, 21));
        BigDecimal normalRate = BigDecimal.valueOf(-15);
        BigDecimal reducedRate = BigDecimal.valueOf(-13);
        CarParkKind carParkKind = CarParkKind.MANAGEMENT;
        assertThrows(IllegalArgumentException.class, () -> new Rate(normalRate, reducedRate, carParkKind, reducedPeriods, normalPeriods));
    }

    //3
    @Test
    void testNormalAndReducedRatesSame() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(10, 14));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(7, 10));
        BigDecimal rate = BigDecimal.valueOf(10);
        CarParkKind carParkKind = CarParkKind.VISITOR;
        assertThrows(IllegalArgumentException.class, () -> new Rate(rate, rate, carParkKind, reducedPeriods, normalPeriods));
    }

    //4
    @Test
    void testRateConstructorInvalidInput() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(10, 14));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(7, 10));
        BigDecimal normalRate = BigDecimal.valueOf(8);
        BigDecimal reducedRate = BigDecimal.valueOf(10);
        CarParkKind carParkKind = CarParkKind.STAFF;
        assertThrows(IllegalArgumentException.class, () -> new Rate(normalRate, reducedRate, carParkKind, reducedPeriods, normalPeriods));
    }

    //5
    @Test
    void testRateConstructor_InvalidInput_NegativeNormalRate() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(10, 14));
        reducedPeriods.add(new Period(14, 18));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(7, 10));
        BigDecimal normalRate = BigDecimal.valueOf(-10);
        BigDecimal reducedRate = BigDecimal.valueOf(8);
        CarParkKind carParkKind = CarParkKind.STAFF;
        assertThrows(IllegalArgumentException.class, () -> new Rate(normalRate, reducedRate, carParkKind, reducedPeriods, normalPeriods));
    }
    //6
    @Test
    void testOverlappingReducedPeriods() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        reducedPeriods.add(new Period(8, 12));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(12, 14));
        assertThrows(IllegalArgumentException.class, () -> {
            BigDecimal normalRate = BigDecimal.TEN;
            BigDecimal reducedRate = BigDecimal.valueOf(8);
            CarParkKind carParkKind = CarParkKind.VISITOR;
            new Rate(normalRate, reducedRate, carParkKind, reducedPeriods, normalPeriods);
        });
    }

    //7
    @Test
    void testConstructorEmptyReducedPeriods() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(7, 10));
        normalPeriods.add(new Period(14, 18));
        assertThrows(IllegalArgumentException.class, () -> {
            BigDecimal normalRate = BigDecimal.valueOf(10);
            BigDecimal reducedRate = BigDecimal.valueOf(8);
            CarParkKind carParkKind = CarParkKind.STAFF;
            new Rate(normalRate, reducedRate, carParkKind, reducedPeriods, normalPeriods);
        });
    } // Bug

    // Fixed !

    //8
    @Test
    void testMultipleValues() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(10, 12));
        reducedPeriods.add(new Period(14, 16));
        reducedPeriods.add(new Period(18, 20));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(7, 10));
        normalPeriods.add(new Period(12, 14));
        normalPeriods.add(new Period(16, 18));
        BigDecimal normalRate = BigDecimal.valueOf(10);
        BigDecimal reducedRate = BigDecimal.valueOf(8);
        CarParkKind carParkKind = CarParkKind.STUDENT;
        Rate rate = new Rate(normalRate, reducedRate, carParkKind, reducedPeriods, normalPeriods);
    }

    //9
    @Test
    void testOverlappingPeriods() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(9, 12));
        reducedPeriods.add(new Period(14, 16));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(10, 14));
        normalPeriods.add(new Period(16, 18));
        assertThrows(IllegalArgumentException.class, () -> {
            BigDecimal normalRate = BigDecimal.valueOf(8);
            BigDecimal reducedRate = BigDecimal.valueOf(5);
            CarParkKind carParkKind = CarParkKind.STAFF;
            new Rate(normalRate, reducedRate, carParkKind, reducedPeriods, normalPeriods);
        });
    }

    //10
    @Test
    void testNegativeReducedRate() {
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(10, 14));
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        normalPeriods.add(new Period(14, 18));
        assertThrows(IllegalArgumentException.class, () -> {
            new Rate(BigDecimal.valueOf(10), BigDecimal.valueOf(-8), CarParkKind.STAFF, reducedPeriods, normalPeriods);
        });
    }

    //11
    @Test
    void testRateConstructorWithOneReducedAndNormalPeriod() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(10, 12));
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(7, 10));
        Rate rate = new Rate(BigDecimal.valueOf(10), BigDecimal.valueOf(8), CarParkKind.STUDENT, reducedPeriods, normalPeriods);
        assertNotNull(rate);

    }


    //12
    @Test
    void testRateConstructor_InvalidInput_NullNormalPeriod() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        reducedPeriods.add(new Period(13, 16));
        assertThrows(IllegalArgumentException.class, () -> {
            new Rate(BigDecimal.valueOf(10), BigDecimal.valueOf(8), CarParkKind.STAFF, reducedPeriods, null);
        });
    }
    // 13
    @Test
    void testConstructor_NullReducedPeriods_IllegalArgumentException() {
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(7, 10));
        normalPeriods.add(new Period(13, 16));
        assertThrows(IllegalArgumentException.class, () -> {
            new Rate(BigDecimal.valueOf(10), BigDecimal.valueOf(8), CarParkKind.STAFF, null, normalPeriods);
        });
    }
    // 14
    @Test
    void testRateConstructor_NormalRatesNull() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        ArrayList<Period> normalPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        reducedPeriods.add(new Period(13, 16));
        normalPeriods.add(new Period(13, 16));
        assertThrows(IllegalArgumentException.class, () -> {
            new Rate(null, BigDecimal.valueOf(8), CarParkKind.STAFF, reducedPeriods, normalPeriods);
        });
    }


    // 14
    @Test
    void testRateConstructor_ReducedRatesNull() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        ArrayList<Period> normalPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(7, 10));
        reducedPeriods.add(new Period(13, 16));
        normalPeriods.add(new Period(13, 16));
        assertThrows(IllegalArgumentException.class, () -> {
            new Rate(BigDecimal.valueOf(10), null,  CarParkKind.STAFF, reducedPeriods, normalPeriods);
        });
    }
    //15
    @Test
    void testRateConstructor_PeriodOverlap() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        ArrayList<Period> normalPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(13, 16));
        normalPeriods.add(new Period(13, 16));
        assertThrows(IllegalArgumentException.class, () -> {
            new Rate(null, BigDecimal.valueOf(8), CarParkKind.STAFF, reducedPeriods, normalPeriods);
        });
    }

    @Test
    void testConstructor_invalidReducedPeriods() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        ArrayList<Period> normalPeriods = new ArrayList<>();

        reducedPeriods.add(new Period(6, 9));
        reducedPeriods.add(new Period(8, 10)); // Overlapping reduced periods
        normalPeriods.add(new Period(9, 18));

        assertThrows(IllegalArgumentException.class, () -> new Rate( BigDecimal.valueOf(10),  BigDecimal.valueOf(8), CarParkKind.STAFF, reducedPeriods, normalPeriods));
    }

    @Test
    void testConstructor_invalidNormalPeriods() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        ArrayList<Period> normalPeriods = new ArrayList<>();

        reducedPeriods.add(new Period(6, 9));
        normalPeriods.add(new Period(9, 18));
        normalPeriods.add(new Period(16, 19)); // Overlapping normal periods

        assertThrows(IllegalArgumentException.class, () -> new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.STAFF, reducedPeriods, normalPeriods));
    }
    @Test
    void testConstructor_invalidReducedAndNormalPeriods() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        ArrayList<Period> normalPeriods = new ArrayList<>();

        reducedPeriods.add(new Period(6, 9));
        reducedPeriods.add(new Period(8, 10)); // Overlapping reduced periods
        normalPeriods.add(new Period(9, 18));
        normalPeriods.add(new Period(16, 19)); // Overlapping normal periods

        assertThrows(IllegalArgumentException.class, () -> new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.STAFF, reducedPeriods, normalPeriods));
    }



    @Test
    void testConstructor_invalid() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        ArrayList<Period> normalPeriods = new ArrayList<>();

        reducedPeriods.add(new Period(6, 9));
        reducedPeriods.add(new Period(7, 8));
        reducedPeriods.add(new Period(8, 10)); // Overlapping reduced periods
        normalPeriods.add(new Period(9, 18));
        normalPeriods.add(new Period(10, 12));
        normalPeriods.add(new Period(16, 19)); // Overlapping normal periods

        assertThrows(IllegalArgumentException.class, () -> new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.STAFF, reducedPeriods, normalPeriods));
    }

    // white Box test cases
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
        assertThrows(IllegalArgumentException.class, () -> new Rate(new BigDecimal("1.50"), new BigDecimal("1.50"), CarParkKind.VISITOR, reducedPeriods, normalPeriods));
    }

    @Test
    void testConstructor_invalidReducedPeriodswhite() {
        reducedPeriods.add(new Period(8, 10));
        assertThrows(IllegalArgumentException.class, () -> new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.MANAGEMENT, reducedPeriods, normalPeriods));
    }

    @Test
    void testConstructor_invalidNormalPeriodswhite() {
        normalPeriods.add(new Period(17, 19));
        assertThrows(IllegalArgumentException.class, () -> new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.STAFF, reducedPeriods, normalPeriods));
    }

    @Test
    void testConstructor_overlappingPeriods() {
        reducedPeriods.add(new Period(9, 12));
        assertThrows(IllegalArgumentException.class, () -> new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.STUDENT, reducedPeriods, normalPeriods));
    }

    @Test
    void testConstructor_nullCarParkKind() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        ArrayList<Period> normalPeriods = new ArrayList<>();

        reducedPeriods.add(new Period(6, 9));
        normalPeriods.add(new Period(9, 18));

        assertThrows(NullPointerException.class, () -> new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), null, reducedPeriods, normalPeriods));
    } // Bug Carpark parameter cannot be null
    // Fixed!


//Calculate

    @Test
    void testCalculate_noOverlap() {
        Rate rate = new Rate(new BigDecimal("2"), new BigDecimal("1"), CarParkKind.STAFF, reducedPeriods, normalPeriods);
        Period periodStay = new Period(1, 5);
        BigDecimal expected = BigDecimal.ZERO;
        assertEquals(expected, rate.calculate(periodStay));
    }

    @Test
    void testCalculate_normalRateOnly() {
        Rate rate = new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.STAFF, reducedPeriods, normalPeriods);
        Period periodStay = new Period(9, 15);
        BigDecimal expected = new BigDecimal("10");
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
        Rate rate = new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.STUDENT, reducedPeriods, normalPeriods);
        Period periodStay = new Period(7, 20);
        BigDecimal expected = new BigDecimal("28.50");
        assertEquals(expected, rate.calculate(periodStay));
    }

    @Test
    void testCalculate_overlapMultiplePeriods() {
        Rate rate = new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.VISITOR, reducedPeriods, normalPeriods);
        Period periodStay = new Period(5, 23);
        BigDecimal expected = new BigDecimal("31.50");
        assertEquals(expected, rate.calculate(periodStay));
    }

    @Test
    void testCalculate_sameStartAndEndTime() {
        Rate rate = new Rate(new BigDecimal("2.50"), new BigDecimal("1.50"), CarParkKind.MANAGEMENT, reducedPeriods, normalPeriods);
        Period periodStay = new Period(12, 12);
        BigDecimal expected = BigDecimal.ZERO;
        assertEquals(expected, rate.calculate(periodStay));
    }





}









