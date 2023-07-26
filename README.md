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

This project and its maven module `io.github.ralfkonrad.quantlib_for_maven:quantlib` does not add
any functionality to the existing ones in QuantLib and QuantLib-SWIG but makes them available
including the native libraries for Linux, macOS and Windows.

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

## Releases

The maven module tries to follow the release cycle of QuantLib (approx. every three to four month)
and uses the same semantic versioning starting with `v1.31.0`. Therefore, a new release of QuantLib
will mean a new version of this maven module.

Also, there will be regular `SNAPSHOT`-builds[^1] reflecting the current development of QuantLib and
QuantLib-SWIG.

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
   or add
   the [dependency to your favorite build system](https://search.maven.org/artifact/io.github.ralfkonrad.quantlib_for_maven/quantlib):

```xml
<dependencies>
  <dependency>
    <groupId>io.github.ralfkonrad.quantlib_for_maven</groupId>
    <artifactId>quantlib</artifactId>
    <version>1.31.1</version>
  </dependency>
</dependencies>
```

2. Build your Maven project, and the QuantLib SWIG Java binding and its dependencies will be
   automatically downloaded and included.

## Usage

To use the QuantLib SWIG Java binding in your project, import the necessary classes and packages
from the QuantLib library. You can then use the QuantLib functionality in your Java code.

Here's an example of an unit test using the QuantLib SWIG Java binding to calculate the price of an
European call option:

```java
package io.github.ralfkonrad;

import org.junit.jupiter.api.Test;
import org.quantlib.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class EuropeanOptionTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(EuropeanOptionTest.class);

    @Test
    public void testEuropeanOption() {
        var localDate = LocalDate.of(2023, 7, 28);
        var today = Date.of(localDate);
        Settings.instance().setEvaluationDate(today);

        // Set up the option parameters
        var strikePrice = 105.0;
        var maturityDate = LocalDate.of(2023, 12, 31);

        // Create the option objects
        var payoff = new PlainVanillaPayoff(Option.Type.Call, strikePrice);
        var exercise = new EuropeanExercise(Date.of(maturityDate));
        var europeanOption = new EuropeanOption(payoff, exercise);

        // Create the market data object
        var dayCounter = new Actual360();
        var calendar = new NullCalendar();

        var spotPrice = 100.0;
        var spotPriceHandle = new QuoteHandle(new SimpleQuote(spotPrice));

        var riskFreeRate = 0.05;
        var riskFreeRateHandle = new QuoteHandle(new SimpleQuote(riskFreeRate));

        var volatility = 0.2;
        var volatilityHandle = new QuoteHandle(new SimpleQuote(volatility));

        var yieldTermStructure = new YieldTermStructureHandle(new FlatForward(today, riskFreeRateHandle, dayCounter));
        var blackVolTermStructure = new BlackVolTermStructureHandle(new BlackConstantVol(today, calendar, volatilityHandle, dayCounter));

        // Create the AnalyticEuropeanEngine
        var process = new BlackScholesProcess(spotPriceHandle, yieldTermStructure, blackVolTermStructure);
        var engine = new AnalyticEuropeanEngine(process);

        // Calculate the NPV for the European option
        europeanOption.setPricingEngine(engine);
        var npv = europeanOption.NPV();

        // Print the result
        LOGGER.info("Option price: {}", npv);
    }
}
```

Taken from [quantlib_for_maven_test](https://github.com/ralfkonrad/quantlib_for_maven_test).

Please refer to the QuantLib documentation and examples for further details on using the QuantLib
SWIG Java binding and its various functionalities.

## Contributing

Contributions to this repository are welcome! If you encounter any issues, have suggestions for
improvements, or would like to add support for additional platforms, please feel free to open an
issue or submit a pull request.

Please keep in mind: This maven module does not add any new functionality to QuantLib and
QuantLib-SWIG. It just uses their official releases[^2] to build a maven module from it. If e.g. the
above European option unit tests runs on your machine you probably do not want to open an issue or
pull request here but more likely in QuantLib or QuantLib-SWIG itself.

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

Also, the module uses [functionality](java/src/main/java/cz/adamh/utils/NativeUtils.java) by Adam
Heinrich which is provided under the MIT License.



[^1]: You find SNAPSHOT builds
at https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/ralfkonrad/quantlib_for_maven/quantlib/

[^2]: Look at https://github.com/lballabio/QuantLib/releases
and https://github.com/lballabio/QuantLib-SWIG/releases
