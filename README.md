# Java language binding for QuantLib

[![Maven Central](https://img.shields.io/maven-central/v/io.github.ralfkonrad.quantlib_for_maven/quantlib.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.ralfkonrad.quantlib_for_maven%22%20AND%20a:%22quantlib%22)

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

1. Add the QuantLib SWIG Java binding as a dependency in your Maven project's `pom.xml` file
   or add the [dependency to your favorite build system](https://search.maven.org/artifact/io.github.ralfkonrad.quantlib_for_maven/quantlib):

```xml
<dependencies>
  <dependency>
    <groupId>io.github.ralfkonrad.quantlib_for_maven</groupId>
    <artifactId>quantlib</artifactId>
    <version>1.31.0</version>
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
import org.quantlib.*;

public class Main {
    public static void main(String[] args) {
        Settings.instance().setEvaluationDate(new Date(14, Month.July, 2023));
        var today = Settings.instance().getEvaluationDate();

        var dayCounter = new Actual360();
        var calendar = new NullCalendar();

        // Set up the option parameters
        var spotPrice = 100.0;
        var strikePrice = 105.0;
        var riskFreeRate = 0.05;
        var volatility = 0.2;
        var maturityDate = new Date(31, Month.December, 2023);

        // Create the option objects
        var spot = new QuoteHandle(new SimpleQuote(spotPrice));
        var rate = new QuoteHandle(new SimpleQuote(riskFreeRate));
        var vol = new QuoteHandle(new SimpleQuote(volatility));
        var yieldCurve = new YieldTermStructureHandle(new FlatForward(today, rate, dayCounter));
        var vola = new BlackVolTermStructureHandle(new BlackConstantVol(today, calendar, vol, dayCounter));

        var payoff = new PlainVanillaPayoff(Option.Type.Call, strikePrice);
        var exercise = new EuropeanExercise(maturityDate);
        var europeanOption = new EuropeanOption(payoff, exercise);

        // Calculate the option price
        var process = new BlackScholesProcess(spot, yieldCurve, vola);
        var engine = new AnalyticEuropeanEngine(process);
        europeanOption.setPricingEngine(engine);

        var npv = europeanOption.NPV();

        // Print the result
        System.out.println("Option price: " + npv);
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
