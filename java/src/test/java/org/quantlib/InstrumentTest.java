package org.quantlib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InstrumentTest {

    private static YieldTermStructureHandle flatRate(
            double forward, DayCounter dayCounter) {
        var quote = new SimpleQuote(forward);
        var quoteHandle = new QuoteHandle(quote);
        var calendar = new NullCalendar();
        var flatForward = new FlatForward(
                0, calendar, quoteHandle, dayCounter);
        return new YieldTermStructureHandle(flatForward);
    }

    private static BlackVolTermStructureHandle flatVol(
            double volatility, DayCounter dayCounter) {
        var quote = new SimpleQuote(volatility);
        var volHandle = new QuoteHandle(quote);
        var calendar = new NullCalendar();
        var constantVol = new BlackConstantVol(
                0, calendar, volHandle, dayCounter);
        return new BlackVolTermStructureHandle(constantVol);
    }

    @Test
    public void testCompositeWhenShiftingDates() {
        var today = Date.todaysDate();
        var dc = new Actual360();

        try (var payoff = new PlainVanillaPayoff(
                     Option.Type.Call, 100.0);
             var exDate = today.add(30);
             var exercise = new EuropeanExercise(exDate)) {

            try (var option = new EuropeanOption(payoff, exercise)) {

                var spot = new SimpleQuote(100.0);
                var qTS = flatRate(0.0, dc);
                var rTS = flatRate(0.01, dc);
                var volTS = flatVol(0.1, dc);

                try (var spotHandle = new QuoteHandle(spot);
                     var process = new BlackScholesMertonProcess(
                             spotHandle, qTS, rTS, volTS);
                     var engine = new AnalyticEuropeanEngine(
                             process)) {

                    option.setPricingEngine(engine);

                    try (var composite =
                                 new CompositeInstrument()) {
                        composite.add(option);

                        try (var futureDate = today.add(45)) {
                            Settings.instance()
                                    .setEvaluationDate(
                                            futureDate);

                            Assertions.assertTrue(
                                    composite.isExpired(),
                                    "Composite didn't"
                                            + " detect"
                                            + " expiration");
                            Assertions.assertEquals(
                                    0.0,
                                    composite.NPV(),
                                    "Composite didn't"
                                            + " return a"
                                            + " null NPV");
                        }

                        Settings.instance()
                                .setEvaluationDate(today);

                        Assertions.assertFalse(
                                composite.isExpired(),
                                "Composite didn't"
                                        + " detect"
                                        + " aliveness");
                        Assertions.assertNotEquals(
                                0.0,
                                composite.NPV(),
                                "Composite didn't"
                                        + " recalculate");
                    }
                }
            }
        }
    }
}
