package org.quantlib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class DatesTest {

    private static void assertDatesEqual(Date expected, Date actual, String message) {
        Assertions.assertTrue(actual.equals(expected),
                message + " expected=" + expected + " actual=" + actual);
    }

    @Test
    public void testAsxDatesSpecific() {
        try (var date = new Date(12, Month.January, 2024)) {
            Assertions.assertEquals(Weekday.Friday, date.weekday());
            Assertions.assertTrue(ASX.isASXdate(date, false));
            Assertions.assertFalse(ASX.isASXdate(date, true));
        }

        try (var ref = new Date(1, Month.January, 2000);
                var expected = new Date(8, Month.February, 2002);
                var asx = ASX.nextDate("F2", false, ref)) {
            assertDatesEqual(expected, asx, "Unexpected ASX date for F2");
        }

        try (var ref = new Date(1, Month.January, 2014);
                var expected = new Date(9, Month.June, 2023);
                var asx = ASX.nextDate("K3", true, ref)) {
            assertDatesEqual(expected, asx, "Unexpected ASX date for K3");
        }

        try (var date = new Date(1, Month.January, 2024)) {
            Assertions.assertEquals("F4", ASX.nextCode(date, false));
        }

        try (var date = new Date(15, Month.January, 2024)) {
            Assertions.assertEquals("G4", ASX.nextCode(date, false));
            Assertions.assertEquals("H4", ASX.nextCode(date, true));
        }

        try (var reference = new Date(1, Month.January, 2020)) {
            Assertions.assertEquals("G4", ASX.nextCode("F4", false, reference));
            Assertions.assertEquals("H5", ASX.nextCode("Z4", true, reference));
        }
    }

    @Test
    public void testConsistency() {
        try (var minDate = Date.minDate();
                var maxDate = Date.maxDate();
                var previousDate = new Date(minDate.serialNumber())) {
            int minSerial = minDate.serialNumber() + 1;
            int maxSerial = maxDate.serialNumber();

            int dyOld = previousDate.dayOfYear();
            int dOld = previousDate.dayOfMonth();
            int mOld = previousDate.month().swigValue();
            int yOld = previousDate.year();
            int wdOld = previousDate.weekday().swigValue();

            for (int serial = minSerial; serial <= maxSerial; serial++) {
                try (var date = new Date(serial)) {
                    int actualSerial = date.serialNumber();
                    Assertions.assertEquals(serial, actualSerial,
                            "inconsistent serial number: original=" + serial
                                    + " date=" + date + " serial=" + actualSerial);

                    int dayOfYear = date.dayOfYear();
                    int dayOfMonth = date.dayOfMonth();
                    int month = date.month().swigValue();
                    int year = date.year();
                    int weekday = date.weekday().swigValue();

                    boolean dayOfYearOk = dayOfYear == dyOld + 1
                            || (dayOfYear == 1 && dyOld == 365 && !Date.isLeap(yOld))
                            || (dayOfYear == 1 && dyOld == 366 && Date.isLeap(yOld));
                    if (!dayOfYearOk) {
                        Assertions.fail("wrong day of year increment: date=" + date
                                + " dayOfYear=" + dayOfYear + " previous=" + dyOld);
                    }
                    dyOld = dayOfYear;

                    boolean dayMonthYearOk = (dayOfMonth == dOld + 1 && month == mOld && year == yOld)
                            || (dayOfMonth == 1 && month == mOld + 1 && year == yOld)
                            || (dayOfMonth == 1 && month == 1 && year == yOld + 1);
                    if (!dayMonthYearOk) {
                        Assertions.fail("wrong day,month,year increment: date=" + date
                                + " day,month,year=" + dayOfMonth + "," + month + "," + year
                                + " previous=" + dOld + "," + mOld + "," + yOld);
                    }
                    dOld = dayOfMonth;
                    mOld = month;
                    yOld = year;

                    if (month < 1 || month > 12) {
                        Assertions.fail("invalid month: date=" + date + " month=" + month);
                    }

                    if (dayOfMonth < 1) {
                        Assertions.fail("invalid day of month: date=" + date
                                + " day=" + dayOfMonth);
                    }

                    boolean dayOk = (month == 1 && dayOfMonth <= 31)
                            || (month == 2 && dayOfMonth <= 28)
                            || (month == 2 && dayOfMonth == 29 && Date.isLeap(year))
                            || (month == 3 && dayOfMonth <= 31)
                            || (month == 4 && dayOfMonth <= 30)
                            || (month == 5 && dayOfMonth <= 31)
                            || (month == 6 && dayOfMonth <= 30)
                            || (month == 7 && dayOfMonth <= 31)
                            || (month == 8 && dayOfMonth <= 31)
                            || (month == 9 && dayOfMonth <= 30)
                            || (month == 10 && dayOfMonth <= 31)
                            || (month == 11 && dayOfMonth <= 30)
                            || (month == 12 && dayOfMonth <= 31);
                    if (!dayOk) {
                        Assertions.fail("invalid day of month: date=" + date
                                + " day=" + dayOfMonth);
                    }

                    boolean weekdayOk = weekday == wdOld + 1 || (weekday == 1 && wdOld == 7);
                    if (!weekdayOk) {
                        Assertions.fail("invalid weekday: date=" + date
                                + " weekday=" + weekday + " previous=" + wdOld);
                    }
                    wdOld = weekday;

                    try (var cloned = new Date(dayOfMonth, Month.swigToEnum(month), year)) {
                        int clonedSerial = cloned.serialNumber();
                        Assertions.assertEquals(serial, clonedSerial,
                                "inconsistent serial number: date=" + date
                                        + " serial=" + serial
                                        + " cloned=" + cloned
                                        + " clonedSerial=" + clonedSerial);
                    }
                }
            }
        }
    }

    @Test
    public void testIsoDates() {
        String inputDate = "2006-01-15";
        try (var date = DateParser.parseISO(inputDate)) {
            Assertions.assertEquals(15, date.dayOfMonth(), "Iso date failed: day");
            Assertions.assertEquals(Month.January, date.month(), "Iso date failed: month");
            Assertions.assertEquals(2006, date.year(), "Iso date failed: year");
        }
    }

    @Test
    public void testParseDates() {
        String inputDate = "2006-01-15";
        try (var parsed = DateParser.parseFormatted(inputDate, "%Y-%m-%d");
                var expected = new Date(15, Month.January, 2006)) {
            assertDatesEqual(expected, parsed, "Date parsing failed for ISO format");
        }

        inputDate = "12/02/2012";
        try (var parsed = DateParser.parseFormatted(inputDate, "%m/%d/%Y");
                var expected = new Date(2, Month.December, 2012)) {
            assertDatesEqual(expected, parsed, "Date parsing failed for US format");
        }

        try (var parsed = DateParser.parseFormatted(inputDate, "%d/%m/%Y");
                var expected = new Date(12, Month.February, 2012)) {
            assertDatesEqual(expected, parsed, "Date parsing failed for EU format");
        }

        inputDate = "20011002";
        try (var parsed = DateParser.parseFormatted(inputDate, "%Y%m%d");
                var expected = new Date(2, Month.October, 2001)) {
            assertDatesEqual(expected, parsed, "Date parsing failed for compact format");
        }
    }

    @Test
    public void testCanHash() {
        try (var startDate = new Date(1, Month.January, 2020)) {
            int nbTests = 500;

            for (int i = 0; i < nbTests; i++) {
                for (int j = 0; j < nbTests; j++) {
                    try (var lhs = startDate.add(i);
                            var rhs = startDate.add(j)) {
                        int lhsHash = lhs.hashCode();
                        int rhsHash = rhs.hashCode();

                        if (lhs.equals(rhs) && lhsHash != rhsHash) {
                            Assertions.fail("Equal dates are expected to have same hash value: lhs="
                                    + lhs + " rhs=" + rhs + " lhsHash=" + lhsHash
                                    + " rhsHash=" + rhsHash);
                        }

                        if (!lhs.equals(rhs) && lhsHash == rhsHash) {
                            Assertions.fail("Different dates are expected to have different hash value: lhs="
                                    + lhs + " rhs=" + rhs + " lhsHash=" + lhsHash
                                    + " rhsHash=" + rhsHash);
                        }
                    }
                }
            }

            HashSet<Date> set = new HashSet<>();
            set.add(startDate);
            Assertions.assertTrue(set.contains(startDate),
                    "Expected to find date " + startDate + " in HashSet");
        }
    }

    @Test
    public void testNullDate() {
        try (var nullDate = new Date()) {
            Assertions.assertDoesNotThrow(nullDate::serialNumber);
            Assertions.assertDoesNotThrow(nullDate::hashCode);
        }
    }
}
