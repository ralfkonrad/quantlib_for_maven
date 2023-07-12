# Java language binding for QuantLib

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.ralfkonrad.quantlib_for_maven/quantlib/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.ralfkonrad.quantlib_for_maven/quantlib)

[![Java: &gt;= 11](https://oss.aoapps.com/ao-badges/java-11.svg)](https://docs.oracle.com/en/java/javase/11/)
[![Licensed under the BSD 3-Clause License](https://img.shields.io/badge/License-BSD--3--Clause-blue.svg)](https://github.com/ralfkonrad/quantlib_for_maven/blob/master/LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs%20-welcome-brightgreen.svg)](https://github.com/ralfkonrad/quantlib_for_maven/blob/master/CONTRIBUTING.md)

[![Build the QuantLib maven artefact](https://github.com/ralfkonrad/quantlib_for_maven/actions/workflows/build_maven_artefact.yml/badge.svg?branch=master)](https://github.com/ralfkonrad/quantlib_for_maven/actions/workflows/build_maven_artefact.yml)
[![Build for different runners](https://github.com/ralfkonrad/quantlib_for_maven/actions/workflows/build_native_libraries.yml/badge.svg?branch=master)](https://github.com/ralfkonrad/quantlib_for_maven/actions/workflows/build_native_libraries.yml)

---

This repository provides Java language binding
for the [QuantLib](https://github.com/lballabio/QuantLib) library, a powerful open-source library
for quantitative finance, using their [QuantLib-SWIG](https://github.com/lballabio/QuantLib-SWIG)
interface. The language binding allows you to seamlessly integrate QuantLib functionality into your
Java projects.

## Introduction

QuantLib is a widely-used library for quantitative finance that provides a comprehensive set of
tools for pricing, risk management, and modeling of financial derivatives. It is written in C++ and
offers an extensive collection of classes and functions for various financial instruments, market
data handling, and numerical methods.

QuantLib-SWIG provides the means to use QuantLib from a number of languages; currently their list
includes Python, C#, Java and R.

[SWIG](https://swig.org/), the Simplified Wrapper and Interface Generator, is a software development
tool that connects programs written in C and C++ with a variety of high-level programming languages.
It automates the process of creating language-specific interfaces, allowing you to seamlessly use
QuantLib from Java.

This repository provides a SWIG language binding for QuantLib specifically for Java. The binding
includes the necessary native libraries for Linux, macOS and Windows and is designed to be used with
Maven, a popular build automation and dependency management tool for Java projects and all other
build tools like Gradle, sbt which can include maven modules.

## Supported Platforms

The QuantLib SWIG Java binding supports the following platforms:

- Linux
- macOS
- Windows

The binding should work on these platforms as long as the required dependencies are available for
your specific operating system.

## Installation

To use the QuantLib SWIG Java binding in your Maven-based project, follow these steps:

1. Add the QuantLib SWIG Java binding as a dependency in your Maven project's `pom.xml` file:

```xml

<dependencies>
  <dependency>
    <groupId>io.github.ralfkonrad.quantlib_for_maven</groupId>
    <artifactId>quantlib</artifactId>
    <version>1.31.0-RC1</version>
  </dependency>
</dependencies>
```

2. Build your Maven project, and the QuantLib SWIG Java binding and its dependencies will be
   automatically downloaded and included.

## Usage

To use the QuantLib SWIG Java binding in your project, import the necessary classes and packages
from the QuantLib library. You can then use the QuantLib functionality in your Java code.

Here's an example of using the QuantLib SWIG Java binding to calculate the price of a European call
option:

```java
import org.quantlib.BlackScholesCalculator;
import org.quantlib.Date;
import org.quantlib.Option;
import org.quantlib.PlainVanillaPayoff;
import org.quantlib.SimpleQuote;
import org.quantlib.Settings;
import org.quantlib.YieldTermStructure;
import org.quantlib.european.Call;

public class Main {
    public static void main(String[] args) {
        // Set up the option parameters
        var spotPrice = 100.0;
        var strikePrice = 105.0;
        var riskFreeRate = 0.05;
        var volatility = 0.2;
        var maturityDate = new Date(2023, 12, 31);

        // Create the option objects
        var spot = new SimpleQuote(spotPrice);
        var rate = new SimpleQuote(riskFreeRate);
        var vol = new SimpleQuote(volatility);
        var yieldCurve = new FlatForward(0, new TARGET(), rate, new Actual365Fixed());

        var payoff = new PlainVanillaPayoff(Option.Type.Call, strikePrice);
        var europeanOption = new Call(payoff, maturityDate);

        // Calculate the option price
        var calculator = new BlackScholesCalculator(europeanOption, spot, yieldCurve, vol);
        var optionPrice = calculator.value();

        // Print the result
        System.out.println("Option price: " + optionPrice);
    }
}
```

And an example for bermudan swaptions:

```java
import org.quantlib.*;
import org.quantlib.euribor.Euribor;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // Set up the market parameters
        var notional = 1000000.0;
        var strike = 0.03;
        var volatility = 0.20;
        var riskFreeRate = 0.03;

        // Set up the calculation date and calendar
        var calculationDate = new Date(2023, Month.JUNE, 27);
        Settings.instance().setEvaluationDate(calculationDate);
        var calendar = new TARGET();

        // Set up the swap schedule
        var startDate = new Date(2024, Month.JUNE, 27);
        var maturityDate = new Date(2030, Month.JUNE, 27);
        var fixedTenor = new Period(1, TimeUnit.YEARS);
        var floatTenor = new Period(6, TimeUnit.MONTHS);
        var index = new Euribor(floatTenor, new YieldTermStructureHandle());
        var schedule = new Schedule(startDate, maturityDate, fixedTenor, calendar,
                BusinessDayConvention.MODIFIED_FOLLOWING, BusinessDayConvention.MODIFIED_FOLLOWING,
                DateGeneration.Rule.FORWARD, false);

        // Set up the fixed and floating rate legs of the swap
        var swapType = VanillaSwap.Type.RECEIVER;
        var fixedRate = 0.02;
        var fixedDayCount = new Actual365Fixed();
        var floatingDayCount = index.dayCounter();
        var fixedLeg = new VanillaSwap(swapType, notional, schedule, fixedRate, fixedDayCount);
        var floatingLeg = new VanillaSwap(swapType, notional, schedule, index, 0.0, floatingDayCount);

        // Create the Bermudan swaption with Bermudan exercise dates
        var exercise = new BermudanExercise(new DoubleVector(), false);
        var swaption = new Swaption(fixedLeg, exercise);

        // Set up the yield term structure
        var yieldCurve = new YieldTermStructureHandle(new FlatForward(calculationDate, riskFreeRate,
                new Actual365Fixed()));

        // Set up the pricing engine
        var engine = new BlackSwaptionEngine(yieldCurve, volatility);

        // Settle the swaption
        swaption.setPricingEngine(engine);

        // Calculate the swaption price
        var swaptionPrice = swaption.NPV();

        // Print the result
        System.out.println("Bermudan swaption price: " + swaptionPrice);
    }
}
```

Please refer to the QuantLib documentation and examples for further details on using the QuantLib
SWIG Java binding and its various functionalities.

## Contributing

Contributions to this repository are welcome! If you encounter any issues, have suggestions for
improvements, or would like to add support for additional platforms, please feel free to open an
issue or submit a pull request.

When contributing, please adhere to the following guidelines:

- Clearly describe the problem or improvement in the issue or pull request.
- Provide detailed steps to reproduce the problem or demonstrate the proposed enhancement.
- Follow the existing code style and structure.
- Write clear and concise commit messages.

## License

The QuantLib SWIG Java Binding is released under the [BSD 3-Clause License](LICENSE). You can use it
in both commercial and non-commercial projects. However, please note that the QuantLib library
itself has its own licensing terms, and you should consult the
official [QuantLib documentation](https://github.com/lballabio/QuantLib) for further information.
