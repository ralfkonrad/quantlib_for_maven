package org.quantlib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EuropeanOptionTest {
    private static final double FD_TOLERANCE = 1.0e-3;

    private static int timeToDays(double t) {
        return (int) Math.round(t * 360);
    }

    private static YieldTermStructureHandle flatRate(
            Date today,
            SimpleQuote quote,
            DayCounter dayCounter) {
        var forwardHandle = new QuoteHandle(quote);
        var flatForward = new FlatForward(today, forwardHandle, dayCounter);
        return new YieldTermStructureHandle(flatForward);
    }

    private static YieldTermStructureHandle flatRate(
            SimpleQuote quote,
            DayCounter dayCounter) {
        var forwardHandle = new QuoteHandle(quote);
        var calendar = new NullCalendar();
        var flatForward = new FlatForward(0, calendar, forwardHandle, dayCounter);
        return new YieldTermStructureHandle(flatForward);
    }

    private static BlackVolTermStructureHandle flatVol(
            Date today,
            SimpleQuote quote,
            DayCounter dayCounter) {
        var volHandle = new QuoteHandle(quote);
        var calendar = new NullCalendar();
        var constantVol = new BlackConstantVol(today, calendar, volHandle, dayCounter);
        return new BlackVolTermStructureHandle(constantVol);
    }

    private static BlackVolTermStructureHandle flatVol(
            SimpleQuote quote,
            DayCounter dayCounter) {
        var volHandle = new QuoteHandle(quote);
        var calendar = new NullCalendar();
        var constantVol = new BlackConstantVol(0, calendar, volHandle, dayCounter);
        return new BlackVolTermStructureHandle(constantVol);
    }

    private static double relativeError(double x1, double x2, double reference) {
        if (reference != 0.0) {
            return Math.abs(x1 - x2) / reference;
        } else {
            return Math.abs(x1 - x2);
        }
    }

    @FunctionalInterface
    private interface EngineFactory {
        PricingEngine create(BlackScholesMertonProcess process);
    }

    private void testEngineConsistency(
            EngineFactory factory,
            java.util.Map<String, Double> tolerance,
            boolean testGreeks) {
        var today = Date.todaysDate();
        var dc = new Actual360();

        var types = new Option.Type[]{Option.Type.Call, Option.Type.Put};
        var strikes = new double[]{75.0, 100.0, 125.0};
        var lengths = new int[]{1};
        var underlyings = new double[]{100.0};
        var qRates = new double[]{0.00, 0.05};
        var rRates = new double[]{0.01, 0.05, 0.15};
        var vols = new double[]{0.11, 0.50, 1.20};

        for (var type : types) {
            for (var strike : strikes) {
                for (var length : lengths) {
                    try (var exDate = today.add(length * 360);
                         var exercise = new EuropeanExercise(exDate);
                         var payoff = new PlainVanillaPayoff(type, strike)) {

                        for (var u : underlyings) {
                            for (var q : qRates) {
                                for (var r : rRates) {
                                    for (var v : vols) {
                                        try (var spot = new SimpleQuote(u);
                                             var qRate = new SimpleQuote(
                                                     q);
                                             var rRate = new SimpleQuote(
                                                     r);
                                             var vol = new SimpleQuote(
                                                     v);
                                             var qTS = flatRate(
                                                     today,
                                                     qRate,
                                                     dc);
                                             var rTS = flatRate(
                                                     today,
                                                     rRate,
                                                     dc);
                                             var volTS = flatVol(
                                                     today,
                                                     vol,
                                                     dc)) {

                                            try (var spotHandle1 = new QuoteHandle(
                                                    spot);
                                                 var process1 = new BlackScholesMertonProcess(
                                                         spotHandle1,
                                                         qTS,
                                                         rTS,
                                                         volTS);
                                                 var refEngine = new AnalyticEuropeanEngine(
                                                         process1);
                                                 var refOption = new EuropeanOption(
                                                         payoff,
                                                         exercise)) {

                                                refOption.setPricingEngine(
                                                        refEngine);

                                                try (var spotHandle2 = new QuoteHandle(
                                                        spot);
                                                     var process2 = new BlackScholesMertonProcess(
                                                             spotHandle2,
                                                             qTS,
                                                             rTS,
                                                             volTS);
                                                     var testEngine = factory
                                                             .create(
                                                                     process2);
                                                     var testOption = new EuropeanOption(
                                                             payoff,
                                                             exercise)) {

                                                    testOption.setPricingEngine(
                                                            testEngine);

                                                    checkGreek("value",
                                                            refOption.NPV(),
                                                            testOption.NPV(),
                                                            u,
                                                            tolerance,
                                                            type,
                                                            strike,
                                                            q,
                                                            r,
                                                            v);

                                                    if (testGreeks
                                                            && testOption.NPV() > spot
                                                            .value()
                                                            * 1e-5) {
                                                        checkGreek("delta",
                                                                refOption.delta(),
                                                                testOption.delta(),
                                                                u,
                                                                tolerance,
                                                                type,
                                                                strike,
                                                                q,
                                                                r,
                                                                v);
                                                        checkGreek("gamma",
                                                                refOption.gamma(),
                                                                testOption.gamma(),
                                                                u,
                                                                tolerance,
                                                                type,
                                                                strike,
                                                                q,
                                                                r,
                                                                v);
                                                        checkGreek("theta",
                                                                refOption.theta(),
                                                                testOption.theta(),
                                                                u,
                                                                tolerance,
                                                                type,
                                                                strike,
                                                                q,
                                                                r,
                                                                v);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static void checkGreek(
            String greek,
            double expected,
            double calculated,
            double u,
            java.util.Map<String, Double> tolerance,
            Option.Type type,
            double strike,
            double q, double r, double v) {
        var error = relativeError(expected, calculated, u);
        Assertions.assertTrue(
                error <= tolerance.get(greek),
                String.format(
                        "%s mismatch:%n"
                                + "    type: %s%n"
                                + "    strike: %.2f%n"
                                + "    spot: %.2f%n"
                                + "    q: %.4f%n"
                                + "    r: %.4f%n"
                                + "    v: %.4f%n"
                                + "    expected: %.10f%n"
                                + "    calculated: %.10f%n"
                                + "    error: %.4e%n"
                                + "    tolerance: %.4e",
                        greek, type, strike, u, q, r, v,
                        expected, calculated, error,
                        tolerance.get(greek)));
    }

    record EuropeanOptionData(
            Option.Type type,
            double strike,
            double s,
            double q,
            double r,
            double t,
            double v,
            double result,
            double tol) {
    }

    private static final EuropeanOptionData[] TEST_DATA = {
            new EuropeanOptionData(Option.Type.Call, 65.00, 60.00, 0.00, 0.08, 0.25, 0.30,
                    2.1334, 1.0e-4),
            new EuropeanOptionData(Option.Type.Put, 95.00, 100.00, 0.05, 0.10, 0.50, 0.20,
                    2.4648, 1.0e-4),
            new EuropeanOptionData(Option.Type.Put, 19.00, 19.00, 0.10, 0.10, 0.75, 0.28,
                    1.7011, 1.0e-4),
            new EuropeanOptionData(Option.Type.Call, 19.00, 19.00, 0.10, 0.10, 0.75, 0.28,
                    1.7011, 1.0e-4),
            new EuropeanOptionData(Option.Type.Call, 1.60, 1.56, 0.08, 0.06, 0.50, 0.12,
                    0.0291, 1.0e-4),
            new EuropeanOptionData(Option.Type.Put, 70.00, 75.00, 0.05, 0.10, 0.50, 0.35,
                    4.0870, 1.0e-4),
            new EuropeanOptionData(Option.Type.Call, 100.00, 90.00, 0.10, 0.10, 0.10, 0.15,
                    0.0205, 1.0e-4),
            new EuropeanOptionData(Option.Type.Call, 100.00, 100.00, 0.10, 0.10, 0.10, 0.15,
                    1.8734, 1.0e-4),
            new EuropeanOptionData(Option.Type.Call, 100.00, 110.00, 0.10, 0.10, 0.10, 0.15,
                    9.9413, 1.0e-4),
            new EuropeanOptionData(Option.Type.Call, 100.00, 90.00, 0.10, 0.10, 0.10, 0.25,
                    0.3150, 1.0e-4),
            new EuropeanOptionData(Option.Type.Call, 100.00, 100.00, 0.10, 0.10, 0.10, 0.25,
                    3.1217, 1.0e-4),
            new EuropeanOptionData(Option.Type.Call, 100.00, 110.00, 0.10, 0.10, 0.10, 0.25,
                    10.3556, 1.0e-4),
            new EuropeanOptionData(Option.Type.Call, 100.00, 90.00, 0.10, 0.10, 0.10, 0.35,
                    0.9474, 1.0e-4),
            new EuropeanOptionData(Option.Type.Call, 100.00, 100.00, 0.10, 0.10, 0.10, 0.35,
                    4.3693, 1.0e-4),
            new EuropeanOptionData(Option.Type.Call, 100.00, 110.00, 0.10, 0.10, 0.10, 0.35,
                    11.1381, 1.0e-4),
            new EuropeanOptionData(Option.Type.Call, 100.00, 90.00, 0.10, 0.10, 0.50, 0.15,
                    0.8069, 1.0e-4),
            new EuropeanOptionData(Option.Type.Call, 100.00, 100.00, 0.10, 0.10, 0.50, 0.15,
                    4.0232, 1.0e-4),
            new EuropeanOptionData(Option.Type.Call, 100.00, 110.00, 0.10, 0.10, 0.50, 0.15,
                    10.5769, 1.0e-4),
            new EuropeanOptionData(Option.Type.Call, 100.00, 90.00, 0.10, 0.10, 0.50, 0.25,
                    2.7026, 1.0e-4),
            new EuropeanOptionData(Option.Type.Call, 100.00, 100.00, 0.10, 0.10, 0.50, 0.25,
                    6.6997, 1.0e-4),
            new EuropeanOptionData(Option.Type.Call, 100.00, 110.00, 0.10, 0.10, 0.50, 0.25,
                    12.7857, 1.0e-4),
            new EuropeanOptionData(Option.Type.Call, 100.00, 90.00, 0.10, 0.10, 0.50, 0.35,
                    4.9329, 1.0e-4),
            new EuropeanOptionData(Option.Type.Call, 100.00, 100.00, 0.10, 0.10, 0.50, 0.35,
                    9.3679, 1.0e-4),
            new EuropeanOptionData(Option.Type.Call, 100.00, 110.00, 0.10, 0.10, 0.50, 0.35,
                    15.3086, 1.0e-4),
            new EuropeanOptionData(Option.Type.Put, 100.00, 90.00, 0.10, 0.10, 0.10, 0.15,
                    9.9210, 1.0e-4),
            new EuropeanOptionData(Option.Type.Put, 100.00, 100.00, 0.10, 0.10, 0.10, 0.15,
                    1.8734, 1.0e-4),
            new EuropeanOptionData(Option.Type.Put, 100.00, 110.00, 0.10, 0.10, 0.10, 0.15,
                    0.0408, 1.0e-4),
            new EuropeanOptionData(Option.Type.Put, 100.00, 90.00, 0.10, 0.10, 0.10, 0.25,
                    10.2155, 1.0e-4),
            new EuropeanOptionData(Option.Type.Put, 100.00, 100.00, 0.10, 0.10, 0.10, 0.25,
                    3.1217, 1.0e-4),
            new EuropeanOptionData(Option.Type.Put, 100.00, 110.00, 0.10, 0.10, 0.10, 0.25,
                    0.4551, 1.0e-4),
            new EuropeanOptionData(Option.Type.Put, 100.00, 90.00, 0.10, 0.10, 0.10, 0.35,
                    10.8479, 1.0e-4),
            new EuropeanOptionData(Option.Type.Put, 100.00, 100.00, 0.10, 0.10, 0.10, 0.35,
                    4.3693, 1.0e-4),
            new EuropeanOptionData(Option.Type.Put, 100.00, 110.00, 0.10, 0.10, 0.10, 0.35,
                    1.2376, 1.0e-4),
            new EuropeanOptionData(Option.Type.Put, 100.00, 90.00, 0.10, 0.10, 0.50, 0.15,
                    10.3192, 1.0e-4),
            new EuropeanOptionData(Option.Type.Put, 100.00, 100.00, 0.10, 0.10, 0.50, 0.15,
                    4.0232, 1.0e-4),
            new EuropeanOptionData(Option.Type.Put, 100.00, 110.00, 0.10, 0.10, 0.50, 0.15,
                    1.0646, 1.0e-4),
            new EuropeanOptionData(Option.Type.Put, 100.00, 90.00, 0.10, 0.10, 0.50, 0.25,
                    12.2149, 1.0e-4),
            new EuropeanOptionData(Option.Type.Put, 100.00, 100.00, 0.10, 0.10, 0.50, 0.25,
                    6.6997, 1.0e-4),
            new EuropeanOptionData(Option.Type.Put, 100.00, 110.00, 0.10, 0.10, 0.50, 0.25,
                    3.2734, 1.0e-4),
            new EuropeanOptionData(Option.Type.Put, 100.00, 90.00, 0.10, 0.10, 0.50, 0.35,
                    14.4452, 1.0e-4),
            new EuropeanOptionData(Option.Type.Put, 100.00, 100.00, 0.10, 0.10, 0.50, 0.35,
                    9.3679, 1.0e-4),
            new EuropeanOptionData(Option.Type.Put, 100.00, 110.00, 0.10, 0.10, 0.50, 0.35,
                    5.7963, 1.0e-4),
            new EuropeanOptionData(Option.Type.Call, 40.00, 42.00, 0.08, 0.04, 0.75, 0.35,
                    5.0975, 1.0e-4)
    };

    @Test
    public void testValues() {
        var dayCounter = new Actual360();
        var today = Date.todaysDate();

        var spot = new SimpleQuote(0.0);
        var qRate = new SimpleQuote(0.0);
        var qTS = flatRate(today, qRate, dayCounter);
        var rRate = new SimpleQuote(0.0);
        var rTS = flatRate(today, rRate, dayCounter);
        var vol = new SimpleQuote(0.0);
        var volTS = flatVol(today, vol, dayCounter);

        for (var value : TEST_DATA) {
            try (var payoff = new PlainVanillaPayoff(value.type, value.strike);
                 var exDate = today.add(timeToDays(value.t));
                 var exercise = new EuropeanExercise(exDate)) {

                spot.setValue(value.s);
                qRate.setValue(value.q);
                rRate.setValue(value.r);
                vol.setValue(value.v);

                try (var spotHandle = new QuoteHandle(spot);
                     var stochProcess = new BlackScholesMertonProcess(
                             spotHandle, qTS, rTS, volTS);
                     var engine = new AnalyticEuropeanEngine(stochProcess);
                     var option = new EuropeanOption(payoff, exercise)) {

                    option.setPricingEngine(engine);

                    var calculated = option.NPV();
                    var error = Math.abs(calculated - value.result);
                    var tolerance = value.tol;

                    Assertions.assertTrue(error <= tolerance,
                            String.format(
                                    "European option value test failed:%n" +
                                            "    type: %s%n" +
                                            "    strike: %.2f%n" +
                                            "    spot: %.2f%n" +
                                            "    dividend yield: %.4f%n" +
                                            "    risk-free rate: %.4f%n" +
                                            "    maturity: %s%n" +
                                            "    volatility: %.4f%n" +
                                            "    expected: %.4f%n" +
                                            "    calculated: %.4f%n" +
                                            "    error: %.4e%n" +
                                            "    tolerance: %.4e",
                                    value.type, value.strike, value.s, value.q,
                                    value.r,
                                    exDate, value.v, value.result, calculated,
                                    error,
                                    tolerance));
                }

                try (var spotHandle = new QuoteHandle(spot);
                     var stochProcess = new BlackScholesMertonProcess(
                             spotHandle, qTS, rTS, volTS);
                     var engine = new FdBlackScholesVanillaEngine(
                             stochProcess, 200, 400);
                     var option = new EuropeanOption(payoff, exercise)) {

                    option.setPricingEngine(engine);

                    var calculated = option.NPV();
                    var error = Math.abs(calculated - value.result);
                    var tolerance = FD_TOLERANCE;

                    Assertions.assertTrue(error <= tolerance,
                            String.format(
                                    "European option FD value test failed:%n" +
                                            "    type: %s%n" +
                                            "    strike: %.2f%n" +
                                            "    spot: %.2f%n" +
                                            "    dividend yield: %.4f%n" +
                                            "    risk-free rate: %.4f%n" +
                                            "    maturity: %s%n" +
                                            "    volatility: %.4f%n" +
                                            "    expected: %.4f%n" +
                                            "    calculated: %.4f%n" +
                                            "    error: %.4e%n" +
                                            "    tolerance: %.4e",
                                    value.type, value.strike, value.s, value.q,
                                    value.r,
                                    exDate, value.v, value.result, calculated,
                                    error,
                                    tolerance));
                }
            }
        }
    }

    @Test
    public void testGreekValues() {
        record GreekTestCase(EuropeanOptionData data, String greek) {
        }

        var testCases = new GreekTestCase[]{
                new GreekTestCase(new EuropeanOptionData(Option.Type.Call, 100.00, 105.00,
                        0.10, 0.10, 0.500000, 0.36, 0.5946, 0), "delta"),
                new GreekTestCase(new EuropeanOptionData(Option.Type.Put, 100.00, 105.00,
                        0.10, 0.10, 0.500000, 0.36, -0.3566, 0), "delta"),
                new GreekTestCase(new EuropeanOptionData(Option.Type.Put, 100.00, 105.00,
                        0.10, 0.10, 0.500000, 0.36, -4.8775, 0), "elasticity"),
                new GreekTestCase(new EuropeanOptionData(Option.Type.Call, 60.00, 55.00,
                        0.00, 0.10, 0.750000, 0.30, 0.0278, 0), "gamma"),
                new GreekTestCase(new EuropeanOptionData(Option.Type.Put, 60.00, 55.00,
                        0.00, 0.10, 0.750000, 0.30, 0.0278, 0), "gamma"),
                new GreekTestCase(new EuropeanOptionData(Option.Type.Call, 60.00, 55.00,
                        0.00, 0.10, 0.750000, 0.30, 18.9358, 0), "vega"),
                new GreekTestCase(new EuropeanOptionData(Option.Type.Put, 60.00, 55.00,
                        0.00, 0.10, 0.750000, 0.30, 18.9358, 0), "vega"),
                new GreekTestCase(new EuropeanOptionData(Option.Type.Put, 405.00, 430.00,
                        0.05, 0.07, 1.0 / 12.0, 0.20, -31.1924, 0), "theta"),
                new GreekTestCase(new EuropeanOptionData(Option.Type.Put, 405.00, 430.00,
                        0.05, 0.07, 1.0 / 12.0, 0.20, -0.0855, 0), "thetaPerDay"),
                new GreekTestCase(new EuropeanOptionData(Option.Type.Call, 75.00, 72.00,
                        0.00, 0.09, 1.000000, 0.19, 38.7325, 0), "rho"),
                new GreekTestCase(new EuropeanOptionData(Option.Type.Put, 490.00, 500.00,
                        0.05, 0.08, 0.250000, 0.15, 42.2254, 0), "dividendRho")
        };

        var dayCounter = new Actual360();
        var today = Date.todaysDate();

        var spot = new SimpleQuote(0.0);
        var qRate = new SimpleQuote(0.0);
        var qTS = flatRate(today, qRate, dayCounter);
        var rRate = new SimpleQuote(0.0);
        var rTS = flatRate(today, rRate, dayCounter);
        var vol = new SimpleQuote(0.0);
        var volTS = flatVol(today, vol, dayCounter);

        for (var testCase : testCases) {
            var value = testCase.data;
            try (var payoff = new PlainVanillaPayoff(value.type, value.strike);
                 var exDate = today.add(timeToDays(value.t));
                 var exercise = new EuropeanExercise(exDate)) {

                spot.setValue(value.s);
                qRate.setValue(value.q);
                rRate.setValue(value.r);
                vol.setValue(value.v);

                try (var spotHandle = new QuoteHandle(spot);
                     var stochProcess = new BlackScholesMertonProcess(
                             spotHandle, qTS, rTS, volTS);
                     var engine = new AnalyticEuropeanEngine(stochProcess);
                     var option = new EuropeanOption(payoff, exercise)) {

                    option.setPricingEngine(engine);

                    var calculated = switch (testCase.greek) {
                        case "delta" -> option.delta();
                        case "elasticity" -> option.elasticity();
                        case "gamma" -> option.gamma();
                        case "vega" -> option.vega();
                        case "theta" -> option.theta();
                        case "thetaPerDay" -> option.thetaPerDay();
                        case "rho" -> option.rho();
                        case "dividendRho" -> option.dividendRho();
                        default -> throw new IllegalArgumentException(
                                "Unknown greek: " + testCase.greek);
                    };

                    var error = Math.abs(calculated - value.result);
                    var tolerance = 1.0e-4;

                    Assertions.assertTrue(error <= tolerance,
                            String.format(
                                    "European option %s test failed:%n" +
                                            "    type: %s%n" +
                                            "    strike: %.2f%n" +
                                            "    spot: %.2f%n" +
                                            "    dividend yield: %.4f%n" +
                                            "    risk-free rate: %.4f%n" +
                                            "    maturity: %s%n" +
                                            "    volatility: %.4f%n" +
                                            "    expected: %.4f%n" +
                                            "    calculated: %.4f%n" +
                                            "    error: %.4e%n" +
                                            "    tolerance: %.4e",
                                    testCase.greek, value.type, value.strike,
                                    value.s,
                                    value.q, value.r, exDate, value.v, value.result,
                                    calculated, error, tolerance));
                }
            }
        }
    }

    @Test
    public void testImpliedVol() {
        var maxEvaluations = 100;
        var tolerance = 1.0e-6;

        var types = new Option.Type[]{Option.Type.Call, Option.Type.Put};
        var strikes = new double[]{90.0, 99.5, 100.0, 100.5, 110.0};
        var lengths = new int[]{36, 180, 360, 1080};

        var underlyings = new double[]{90.0, 95.0, 99.9, 100.0, 100.1, 105.0, 110.0};
        var qRates = new double[]{0.01, 0.05, 0.10};
        var rRates = new double[]{0.01, 0.05, 0.10};
        var vols = new double[]{0.01, 0.20, 0.30, 0.70, 0.90};

        var dayCounter = new Actual360();
        var today = Date.todaysDate();

        var spot = new SimpleQuote(0.0);
        var qRate = new SimpleQuote(0.0);
        var qTS = flatRate(today, qRate, dayCounter);
        var rRate = new SimpleQuote(0.0);
        var rTS = flatRate(today, rRate, dayCounter);
        var vol = new SimpleQuote(0.0);
        var volTS = flatVol(today, vol, dayCounter);

        for (var type : types) {
            for (var strike : strikes) {
                for (var length : lengths) {
                    var exDate = today.add(length);
                    try (var exercise = new EuropeanExercise(exDate);
                         var payoff = new PlainVanillaPayoff(type, strike)) {

                        for (var u : underlyings) {
                            for (var q : qRates) {
                                for (var r : rRates) {
                                    for (var v : vols) {
                                        spot.setValue(u);
                                        qRate.setValue(q);
                                        rRate.setValue(r);
                                        vol.setValue(v);

                                        try (var spotHandle = new QuoteHandle(
                                                spot);
                                             var process = new BlackScholesMertonProcess(
                                                     spotHandle,
                                                     qTS,
                                                     rTS,
                                                     volTS);
                                             var engine = new AnalyticEuropeanEngine(
                                                     process);
                                             var option = new EuropeanOption(
                                                     payoff,
                                                     exercise)) {

                                            option.setPricingEngine(engine);

                                            var value = option.NPV();
                                            if (value == 0.0) {
                                                continue;
                                            }

                                            vol.setValue(v * 0.5);
                                            var value2 = option.NPV();
                                            if (Math.abs(value
                                                    - value2) <= 1.0e-12) {
                                                continue;
                                            }

                                            vol.setValue(v);

                                            try {
                                                var implVol = option
                                                        .impliedVolatility(
                                                                value,
                                                                process,
                                                                tolerance,
                                                                maxEvaluations);

                                                if (Math.abs(implVol
                                                        - v) > tolerance) {
                                                    vol.setValue(implVol);
                                                    var npv2 = option
                                                            .NPV();
                                                    var error = relativeError(
                                                            value,
                                                            npv2,
                                                            value);

                                                    Assertions.assertTrue(
                                                            error <= tolerance,
                                                            String.format(
                                                                    "Failed to reproduce "
                                                                            + "option value:%n"
                                                                            + "    type: %s%n"
                                                                            + "    strike: %.2f%n"
                                                                            + "    spot: %.2f%n"
                                                                            + "    dividend: %.4f%n"
                                                                            + "    rate: %.4f%n"
                                                                            + "    days: %d%n"
                                                                            + "    volatility: %.4f%n"
                                                                            + "    expected: %.10f%n"
                                                                            + "    calculated: %.10f%n"
                                                                            + "    error: %.4e%n"
                                                                            + "    tolerance: %.4e",
                                                                    type,
                                                                    strike,
                                                                    u,
                                                                    q,
                                                                    r,
                                                                    length,
                                                                    v,
                                                                    value,
                                                                    npv2,
                                                                    error,
                                                                    tolerance));
                                                }
                                            } catch (RuntimeException e) {
                                                Assertions.fail(String
                                                        .format(
                                                                "Failed to calculate implied vol:%n"
                                                                        + "    type: %s%n"
                                                                        + "    strike: %.2f%n"
                                                                        + "    spot: %.2f%n"
                                                                        + "    dividend: %.4f%n"
                                                                        + "    rate: %.4f%n"
                                                                        + "    days: %d%n"
                                                                        + "    volatility: %.4f%n"
                                                                        + "    value: %.10f%n"
                                                                        + "    error: %s",
                                                                type,
                                                                strike,
                                                                u,
                                                                q,
                                                                r,
                                                                length,
                                                                v,
                                                                value,
                                                                e.getMessage()));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testJRBinomialEngines() {
        var tolerance = java.util.Map.of(
                "value", 0.002,
                "delta", 1.0e-3,
                "gamma", 1.0e-4,
                "theta", 0.03);
        testEngineConsistency(
                process -> new BinomialJRVanillaEngine(
                        process, 251L),
                tolerance, true);
    }

    @Test
    public void testCRRBinomialEngines() {
        var tolerance = java.util.Map.of(
                "value", 0.02,
                "delta", 1.0e-3,
                "gamma", 1.0e-4,
                "theta", 0.03);
        testEngineConsistency(
                process -> new BinomialCRRVanillaEngine(
                        process, 501L),
                tolerance, true);
    }

    @Test
    public void testEQPBinomialEngines() {
        var tolerance = java.util.Map.of(
                "value", 0.02,
                "delta", 1.0e-3,
                "gamma", 1.0e-4,
                "theta", 0.03);
        testEngineConsistency(
                process -> new BinomialEQPVanillaEngine(
                        process, 501L),
                tolerance, true);
    }

    @Test
    public void testTGEOBinomialEngines() {
        var tolerance = java.util.Map.of(
                "value", 0.002,
                "delta", 1.0e-3,
                "gamma", 1.0e-4,
                "theta", 0.03);
        testEngineConsistency(
                process -> new BinomialTrigeorgisVanillaEngine(
                        process, 251L),
                tolerance, true);
    }

    @Test
    public void testTIANBinomialEngines() {
        var tolerance = java.util.Map.of(
                "value", 0.002,
                "delta", 1.0e-3,
                "gamma", 1.0e-4,
                "theta", 0.03);
        testEngineConsistency(
                process -> new BinomialTianVanillaEngine(
                        process, 251L),
                tolerance, true);
    }

    @Test
    public void testLRBinomialEngines() {
        var tolerance = java.util.Map.of(
                "value", 1.0e-6,
                "delta", 1.0e-3,
                "gamma", 1.0e-4,
                "theta", 0.03);
        testEngineConsistency(
                process -> new BinomialLRVanillaEngine(
                        process, 251L),
                tolerance, true);
    }

    @Test
    public void testJOSHIBinomialEngines() {
        var tolerance = java.util.Map.of(
                "value", 1.0e-7,
                "delta", 1.0e-3,
                "gamma", 1.0e-4,
                "theta", 0.03);
        testEngineConsistency(
                process -> new BinomialJ4VanillaEngine(
                        process, 251L),
                tolerance, true);
    }

    @Test
    public void testFdEngines() {
        var tolerance = java.util.Map.of(
                "value", 1.0e-4,
                "delta", 1.0e-6,
                "gamma", 1.0e-6,
                "theta", 1.0e-3);
        testEngineConsistency(
                process -> new FdBlackScholesVanillaEngine(
                        process, 500, 500),
                tolerance, true);
    }

    @Test
    public void testIntegralEngines() {
        var tolerance = java.util.Map.of("value", 0.0001);
        testEngineConsistency(
                process -> new IntegralEngine(process),
                tolerance, false);
    }

    @Test
    public void testMcEngines() {
        var tolerance = java.util.Map.of("value", 0.01);
        testEngineConsistency(
                process -> new MCPREuropeanEngine(
                        process, 1, QuantLib.nullInt(),
                        false, false,
                        40000, QuantLib.nullDouble(),
                        QuantLib.nullInt(), 42),
                tolerance, false);
    }

    @Test
    public void testQmcEngines() {
        var tolerance = java.util.Map.of("value", 0.01);
        testEngineConsistency(
                process -> new MCLDEuropeanEngine(
                        process, 1, QuantLib.nullInt(),
                        false, false, 4095),
                tolerance, false);
    }

    // testFFTEngines omitted: FFTVanillaEngine not available
    // in SWIG bindings

    @Test
    public void testGreeks() {
        var calculated = new java.util.HashMap<String, Double>();
        var expected = new java.util.HashMap<String, Double>();
        var tolerance = new java.util.HashMap<String, Double>();
        tolerance.put("delta", 1.0e-5);
        tolerance.put("gamma", 1.0e-5);
        tolerance.put("theta", 1.0e-5);
        tolerance.put("rho", 1.0e-5);
        tolerance.put("divRho", 1.0e-5);
        tolerance.put("vega", 1.0e-5);

        var types = new Option.Type[]{Option.Type.Call, Option.Type.Put};
        var strikes = new double[]{50.0, 99.5, 100.0, 100.5, 150.0};
        var underlyings = new double[]{100.0};
        var qRates = new double[]{0.04, 0.05, 0.06};
        var rRates = new double[]{0.01, 0.05, 0.15};
        var residualTimes = new double[]{1.0, 2.0};
        var vols = new double[]{0.11, 0.50, 1.20};

        var dc = new Actual360();
        var today = Date.todaysDate();
        Settings.instance().setEvaluationDate(today);

        var spot = new SimpleQuote(0.0);
        var qRate = new SimpleQuote(0.0);
        var qTS = flatRate(qRate, dc);
        var rRate = new SimpleQuote(0.0);
        var rTS = flatRate(rRate, dc);
        var vol = new SimpleQuote(0.0);
        var volTS = flatVol(vol, dc);

        for (var type : types) {
            for (var strike : strikes) {
                for (var residualTime : residualTimes) {
                    var exDate = today.add((int) Math.round(residualTime * 360));
                    try (var exercise = new EuropeanExercise(exDate)) {

                        for (var kk = 0; kk < 4; kk++) {
                            StrikedTypePayoff payoff;
                            if (kk == 0) {
                                payoff = new PlainVanillaPayoff(type, strike);
                            } else if (kk == 1) {
                                payoff = new CashOrNothingPayoff(type, strike, 100.0);
                            } else if (kk == 2) {
                                payoff = new AssetOrNothingPayoff(type, strike);
                            } else {
                                payoff = new GapPayoff(type, strike, 100.0);
                            }

                            try (payoff) {
                                for (var u : underlyings) {
                                    for (var q : qRates) {
                                        for (var r : rRates) {
                                            for (var v : vols) {
                                                spot.setValue(u);
                                                qRate.setValue(q);
                                                rRate.setValue(r);
                                                vol.setValue(v);

                                                try (var spotHandle = new QuoteHandle(
                                                        spot);
                                                     var stochProcess = new BlackScholesMertonProcess(
                                                             spotHandle,
                                                             qTS,
                                                             rTS,
                                                             volTS);
                                                     var engine = new AnalyticEuropeanEngine(
                                                             stochProcess);
                                                     var option = new EuropeanOption(
                                                             payoff,
                                                             exercise)) {

                                                    option.setPricingEngine(
                                                            engine);

                                                    var value = option
                                                            .NPV();
                                                    calculated.put("delta",
                                                            option.delta());
                                                    calculated.put("gamma",
                                                            option.gamma());
                                                    calculated.put("theta",
                                                            option.theta());
                                                    calculated.put("rho",
                                                            option.rho());
                                                    calculated.put("divRho",
                                                            option.dividendRho());
                                                    calculated.put("vega",
                                                            option.vega());

                                                    if (value > spot.value()
                                                            * 1.0e-5) {
                                                        var du = u * 1.0e-4;
                                                        spot.setValue(u + du);
                                                        var value_p = option
                                                                .NPV();
                                                        var delta_p = option
                                                                .delta();
                                                        spot.setValue(u - du);
                                                        var value_m = option
                                                                .NPV();
                                                        var delta_m = option
                                                                .delta();
                                                        spot.setValue(u);
                                                        expected.put("delta",
                                                                (value_p - value_m)
                                                                        / (2 * du));
                                                        expected.put("gamma",
                                                                (delta_p - delta_m)
                                                                        / (2 * du));

                                                        var dr = r * 1.0e-4;
                                                        rRate.setValue(r + dr);
                                                        value_p = option.NPV();
                                                        rRate.setValue(r - dr);
                                                        value_m = option.NPV();
                                                        rRate.setValue(r);
                                                        expected.put("rho",
                                                                (value_p - value_m)
                                                                        / (2 * dr));

                                                        var dq = q * 1.0e-4;
                                                        qRate.setValue(q + dq);
                                                        value_p = option.NPV();
                                                        qRate.setValue(q - dq);
                                                        value_m = option.NPV();
                                                        qRate.setValue(q);
                                                        expected.put("divRho",
                                                                (value_p - value_m)
                                                                        / (2 * dq));

                                                        var dv = v * 1.0e-4;
                                                        vol.setValue(v + dv);
                                                        value_p = option.NPV();
                                                        vol.setValue(v - dv);
                                                        value_m = option.NPV();
                                                        vol.setValue(v);
                                                        expected.put("vega",
                                                                (value_p - value_m)
                                                                        / (2 * dv));

                                                        var yesterday = today
                                                                .subtract(1);
                                                        var tomorrow = today
                                                                .add(1);
                                                        var dT = dc.yearFraction(
                                                                yesterday,
                                                                tomorrow);
                                                        Settings.instance()
                                                                .setEvaluationDate(
                                                                        yesterday);
                                                        value_m = option.NPV();
                                                        Settings.instance()
                                                                .setEvaluationDate(
                                                                        tomorrow);
                                                        value_p = option.NPV();
                                                        Settings.instance()
                                                                .setEvaluationDate(
                                                                        today);
                                                        expected.put("theta",
                                                                (value_p - value_m)
                                                                        / dT);
                                                        yesterday.delete();
                                                        tomorrow.delete();

                                                        for (var it : calculated
                                                                .entrySet()) {
                                                            var greek = it.getKey();
                                                            var expct = expected
                                                                    .get(greek);
                                                            var calcl = it.getValue();
                                                            var error = relativeError(
                                                                    expct,
                                                                    calcl,
                                                                    u);
                                                            Assertions.assertTrue(
                                                                    error <= tolerance
                                                                            .get(greek),
                                                                    String.format(
                                                                            "Greeks calculation "
                                                                                    + "failed for "
                                                                                    + "%s:%n"
                                                                                    + "    type: %s%n"
                                                                                    + "    strike: "
                                                                                    + "%.2f%n"
                                                                                    + "    spot: "
                                                                                    + "%.2f%n"
                                                                                    + "    dividend "
                                                                                    + "yield: %.4f%n"
                                                                                    + "    risk-free "
                                                                                    + "rate: %.4f%n"
                                                                                    + "    residual "
                                                                                    + "time: %.2f%n"
                                                                                    + "    volatility"
                                                                                    + ": %.4f%n"
                                                                                    + "    expected: "
                                                                                    + "%.10f%n"
                                                                                    + "    "
                                                                                    + "calculated: "
                                                                                    + "%.10f%n"
                                                                                    + "    error: "
                                                                                    + "%.4e%n"
                                                                                    + "    "
                                                                                    + "tolerance: "
                                                                                    + "%.4e",
                                                                            greek,
                                                                            type,
                                                                            strike,
                                                                            u,
                                                                            q,
                                                                            r,
                                                                            residualTime,
                                                                            v,
                                                                            expct,
                                                                            calcl,
                                                                            error,
                                                                            tolerance.get(greek)));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testAnalyticEngineDiscountCurve() {
        var dc = new Actual360();
        var today = Date.todaysDate();

        var spot = new SimpleQuote(1000.0);
        var qRate = new SimpleQuote(0.01);
        var qTS = flatRate(today, qRate, dc);
        var rRate = new SimpleQuote(0.015);
        var rTS = flatRate(today, rRate, dc);
        var vol = new SimpleQuote(0.02);
        var volTS = flatVol(today, vol, dc);

        var discRate = new SimpleQuote(0.015);
        var discTS = flatRate(today, discRate, dc);

        try (var spotHandle = new QuoteHandle(spot);
             var stochProcess = new BlackScholesMertonProcess(
                     spotHandle, qTS, rTS, volTS);
             var engineSingleCurve = new AnalyticEuropeanEngine(stochProcess);
             var engineMultiCurve = new AnalyticEuropeanEngine(stochProcess, discTS);
             var payoff = new PlainVanillaPayoff(Option.Type.Call, 1025.0);
             var exDate = today.add(new Period(1, TimeUnit.Years));
             var exercise = new EuropeanExercise(exDate);
             var optionSingleCurve = new EuropeanOption(payoff, exercise);
             var optionMultiCurve = new EuropeanOption(payoff, exercise)) {

            optionSingleCurve.setPricingEngine(engineSingleCurve);
            optionMultiCurve.setPricingEngine(engineMultiCurve);

            var npvSingleCurve = optionSingleCurve.NPV();
            var npvMultiCurve = optionMultiCurve.NPV();

            Assertions.assertEquals(npvSingleCurve, npvMultiCurve, 1.0e-10,
                    "NPV should match when discount rate equals risk-free rate");

            discRate.setValue(0.023);
            var npvSingleCurve2 = optionSingleCurve.NPV();
            var npvMultiCurve2 = optionMultiCurve.NPV();

            Assertions.assertNotEquals(npvSingleCurve2, npvMultiCurve2,
                    "NPV should differ when discount rate differs from risk-free rate");
        }
    }
}
