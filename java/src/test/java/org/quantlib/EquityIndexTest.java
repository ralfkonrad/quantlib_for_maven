package org.quantlib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class EquityIndexTest {

    private static final double TOLERANCE = 1.0e-8;

    private record CommonVars(
            Date today,
            Calendar calendar,
            DayCounter dayCount,
            EquityIndex equityIndex,
            RelinkableYieldTermStructureHandle interestHandle,
            RelinkableYieldTermStructureHandle dividendHandle,
            SimpleQuote spot,
            RelinkableQuoteHandle spotHandle) implements AutoCloseable {

        @Override
        public void close() {
            IndexManager.instance().clearHistories();
            equityIndex.delete();
            interestHandle.delete();
            dividendHandle.delete();
            spot.delete();
            spotHandle.delete();
            dayCount.delete();
            calendar.delete();
            today.delete();
        }
    }

    private static CommonVars createCommonVars(boolean addTodaysFixing) {
        var calendar = new TARGET();
        var dayCount = new Actual365Fixed();

        var interestHandle = new RelinkableYieldTermStructureHandle();
        var dividendHandle = new RelinkableYieldTermStructureHandle();
        var spotHandle = new RelinkableQuoteHandle();

        var equityIndex = new EquityIndex(
                "eqIndex", calendar, new EURCurrency(),
                interestHandle, dividendHandle, spotHandle);

        var todayUnadjusted = Date.of(LocalDate.of(2023, 1, 27));
        var today = calendar.adjust(todayUnadjusted);
        todayUnadjusted.delete();

        if (addTodaysFixing) {
            equityIndex.addFixing(today, 8690.0);
        }

        Settings.instance().setEvaluationDate(today);

        try (var interestCurve = new FlatForward(today, 0.03, dayCount);
             var dividendCurve = new FlatForward(today, 0.01, dayCount)) {
            interestHandle.linkTo(interestCurve);
            dividendHandle.linkTo(dividendCurve);
        }

        var spot = new SimpleQuote(8700.0);
        spotHandle.linkTo(spot);

        return new CommonVars(
                today, calendar, dayCount, equityIndex,
                interestHandle, dividendHandle, spot, spotHandle);
    }

    @Test
    public void testTodaysFixing() {
        try (var vars = createCommonVars(true)) {
            var historicalIndex = 8690.0;
            var todaysFixing = vars.equityIndex().fixing(vars.today());

            Assertions.assertEquals(historicalIndex, todaysFixing, TOLERANCE,
                    "today's fixing should be equal to historical index");

            var spotValue = 8700.0;
            var forecastedFixing = vars.equityIndex()
                    .fixing(vars.today(), true);

            Assertions.assertEquals(spotValue, forecastedFixing, TOLERANCE,
                    "today's fixing forecast should be equal to spot");
        }
    }

    @Test
    public void testTodaysFixingWithSpotAsProxy() {
        try (var vars = createCommonVars(false)) {
            var spotValue = 8700.0;
            var fixing = vars.equityIndex().fixing(vars.today());

            Assertions.assertEquals(spotValue, fixing, TOLERANCE,
                    "today's fixing should be equal to spot "
                            + "when historical index not added");
        }
    }

    @Test
    public void testFixingForecast() {
        try (var vars = createCommonVars(true);
             var forecastedDate = Date.of(LocalDate.of(2030, 5, 20))) {
            var forecast = vars.equityIndex().fixing(forecastedDate);
            var expectedForecast = vars.spotHandle().value()
                    * vars.dividendHandle().discount(forecastedDate)
                    / vars.interestHandle().discount(forecastedDate);

            Assertions.assertEquals(expectedForecast, forecast, TOLERANCE,
                    "could not replicate index forecast");
        }
    }

    @Test
    public void testFixingForecastWithoutDividend() {
        try (var vars = createCommonVars(true);
             var forecastedDate = Date.of(LocalDate.of(2030, 5, 20));
             var emptyDividendHandle = new YieldTermStructureHandle();
             var equityIndexExDiv = vars.equityIndex().clone(
                     vars.interestHandle(), emptyDividendHandle,
                     vars.spotHandle())) {
            var forecast = equityIndexExDiv.fixing(forecastedDate);
            var expectedForecast = vars.spotHandle().value()
                    / vars.interestHandle().discount(forecastedDate);

            Assertions.assertEquals(expectedForecast, forecast, TOLERANCE,
                    "could not replicate index forecast without dividend");
        }
    }

    @Test
    public void testFixingForecastWithoutSpot() {
        try (var vars = createCommonVars(true);
             var forecastedDate = Date.of(LocalDate.of(2030, 5, 20));
             var emptySpotHandle = new QuoteHandle();
             var equityIndexExSpot = vars.equityIndex().clone(
                     vars.interestHandle(), vars.dividendHandle(),
                     emptySpotHandle)) {
            var forecast = equityIndexExSpot.fixing(forecastedDate);
            var expectedForecast = equityIndexExSpot
                    .pastFixing(vars.today())
                    * vars.dividendHandle().discount(forecastedDate)
                    / vars.interestHandle().discount(forecastedDate);

            Assertions.assertEquals(expectedForecast, forecast, TOLERANCE,
                    "could not replicate index forecast without spot handle");
        }
    }

    @Test
    public void testFixingForecastWithoutSpotAndHistoricalFixing() {
        try (var vars = createCommonVars(false);
             var forecastedDate = Date.of(LocalDate.of(2030, 5, 20));
             var emptySpotHandle = new QuoteHandle();
             var equityIndexExSpot = vars.equityIndex().clone(
                     vars.interestHandle(), vars.dividendHandle(),
                     emptySpotHandle)) {
            var exception = Assertions.assertThrows(RuntimeException.class,
                    () -> equityIndexExSpot.fixing(forecastedDate));
            Assertions.assertTrue(
                    exception.getMessage().contains(
                            "Cannot forecast equity index, "
                                    + "missing both spot and historical index"),
                    "Expected error about missing spot and historical index, "
                            + "got: " + exception.getMessage());
        }
    }

    @Test
    public void testSpotChange() {
        try (var vars = createCommonVars(true);
             var newSpot = new SimpleQuote(9000.0)) {
            vars.spotHandle().linkTo(newSpot);

            Assertions.assertEquals(
                    newSpot.value(), vars.equityIndex().spot().value(),
                    TOLERANCE,
                    "could not re-link spot quote to new value");

            vars.spotHandle().linkTo(vars.spot());

            Assertions.assertEquals(
                    vars.spot().value(), vars.equityIndex().spot().value(),
                    TOLERANCE,
                    "could not re-link spot quote back to old value");
        }
    }

    @Test
    public void testErrorWhenInvalidFixingDate() {
        try (var vars = createCommonVars(true);
             var invalidDate = Date.of(LocalDate.of(2023, 1, 1))) {
            var exception = Assertions.assertThrows(RuntimeException.class,
                    () -> vars.equityIndex().fixing(invalidDate));
            Assertions.assertTrue(
                    exception.getMessage().contains(
                            "Fixing date January 1st, 2023 is not valid"),
                    "Expected error about invalid fixing date, got: "
                            + exception.getMessage());
        }
    }

    @Test
    public void testErrorWhenFixingMissing() {
        try (var vars = createCommonVars(true);
             var missingDate = Date.of(LocalDate.of(2023, 1, 2))) {
            var exception = Assertions.assertThrows(RuntimeException.class,
                    () -> vars.equityIndex().fixing(missingDate));
            Assertions.assertTrue(
                    exception.getMessage().contains(
                            "Missing eqIndex fixing for January 2nd, 2023"),
                    "Expected error about missing fixing, got: "
                            + exception.getMessage());
        }
    }

    @Test
    public void testErrorWhenInterestHandleMissing() {
        try (var vars = createCommonVars(true);
             var forecastedDate = Date.of(LocalDate.of(2030, 5, 20));
             var emptyInterestHandle = new YieldTermStructureHandle();
             var emptyDividendHandle = new YieldTermStructureHandle();
             var emptySpotHandle = new QuoteHandle();
             var equityIndexExDiv = vars.equityIndex().clone(
                     emptyInterestHandle, emptyDividendHandle,
                     emptySpotHandle)) {
            var exception = Assertions.assertThrows(RuntimeException.class,
                    () -> equityIndexExDiv.fixing(forecastedDate));
            Assertions.assertTrue(
                    exception.getMessage().contains(
                            "null interest rate term structure "
                                    + "set to this instance of eqIndex"),
                    "Expected error about null interest rate term structure, "
                            + "got: " + exception.getMessage());
        }
    }

    @Test
    public void testNoErrorIfTodayIsNotBusinessDay() {
        try (var vars = createCommonVars(true);
             var today = Date.of(LocalDate.of(2023, 1, 28));
             var forecastedDate = Date.of(LocalDate.of(2030, 5, 20));
             var emptySpotHandle = new QuoteHandle();
             var equityIndex = vars.equityIndex().clone(
                     vars.interestHandle(), vars.dividendHandle(),
                     emptySpotHandle)) {
            Settings.instance().setEvaluationDate(today);
            Assertions.assertDoesNotThrow(
                    () -> vars.equityIndex().fixing(forecastedDate));
        }
    }
}
