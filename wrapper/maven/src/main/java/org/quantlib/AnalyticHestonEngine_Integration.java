/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class AnalyticHestonEngine_Integration implements AutoCloseable {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected AnalyticHestonEngine_Integration(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(AnalyticHestonEngine_Integration obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(AnalyticHestonEngine_Integration obj) {
    long ptr = 0;
    if (obj != null) {
      if (!obj.swigCMemOwn)
        throw new RuntimeException("Cannot release ownership as memory is not owned");
      ptr = obj.swigCPtr;
      obj.swigCMemOwn = false;
      obj.delete();
    }
    return ptr;
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        QuantLibJNI.delete_AnalyticHestonEngine_Integration(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  @Override
  public void close() {
   this.delete();
  }

  public static AnalyticHestonEngine_Integration gaussLaguerre(long integrationOrder) {
    return new AnalyticHestonEngine_Integration(QuantLibJNI.AnalyticHestonEngine_Integration_gaussLaguerre__SWIG_0(integrationOrder), true);
  }

  public static AnalyticHestonEngine_Integration gaussLaguerre() {
    return new AnalyticHestonEngine_Integration(QuantLibJNI.AnalyticHestonEngine_Integration_gaussLaguerre__SWIG_1(), true);
  }

  public static AnalyticHestonEngine_Integration gaussLegendre(long integrationOrder) {
    return new AnalyticHestonEngine_Integration(QuantLibJNI.AnalyticHestonEngine_Integration_gaussLegendre__SWIG_0(integrationOrder), true);
  }

  public static AnalyticHestonEngine_Integration gaussLegendre() {
    return new AnalyticHestonEngine_Integration(QuantLibJNI.AnalyticHestonEngine_Integration_gaussLegendre__SWIG_1(), true);
  }

  public static AnalyticHestonEngine_Integration gaussChebyshev(long integrationOrder) {
    return new AnalyticHestonEngine_Integration(QuantLibJNI.AnalyticHestonEngine_Integration_gaussChebyshev__SWIG_0(integrationOrder), true);
  }

  public static AnalyticHestonEngine_Integration gaussChebyshev() {
    return new AnalyticHestonEngine_Integration(QuantLibJNI.AnalyticHestonEngine_Integration_gaussChebyshev__SWIG_1(), true);
  }

  public static AnalyticHestonEngine_Integration gaussChebyshev2nd(long integrationOrder) {
    return new AnalyticHestonEngine_Integration(QuantLibJNI.AnalyticHestonEngine_Integration_gaussChebyshev2nd__SWIG_0(integrationOrder), true);
  }

  public static AnalyticHestonEngine_Integration gaussChebyshev2nd() {
    return new AnalyticHestonEngine_Integration(QuantLibJNI.AnalyticHestonEngine_Integration_gaussChebyshev2nd__SWIG_1(), true);
  }

  public static AnalyticHestonEngine_Integration gaussLobatto(double relTolerance, double absTolerance, long maxEvaluations) {
    return new AnalyticHestonEngine_Integration(QuantLibJNI.AnalyticHestonEngine_Integration_gaussLobatto__SWIG_0(relTolerance, absTolerance, maxEvaluations), true);
  }

  public static AnalyticHestonEngine_Integration gaussLobatto(double relTolerance, double absTolerance) {
    return new AnalyticHestonEngine_Integration(QuantLibJNI.AnalyticHestonEngine_Integration_gaussLobatto__SWIG_1(relTolerance, absTolerance), true);
  }

  public static AnalyticHestonEngine_Integration gaussKronrod(double absTolerance, long maxEvaluations) {
    return new AnalyticHestonEngine_Integration(QuantLibJNI.AnalyticHestonEngine_Integration_gaussKronrod__SWIG_0(absTolerance, maxEvaluations), true);
  }

  public static AnalyticHestonEngine_Integration gaussKronrod(double absTolerance) {
    return new AnalyticHestonEngine_Integration(QuantLibJNI.AnalyticHestonEngine_Integration_gaussKronrod__SWIG_1(absTolerance), true);
  }

  public static AnalyticHestonEngine_Integration simpson(double absTolerance, long maxEvaluations) {
    return new AnalyticHestonEngine_Integration(QuantLibJNI.AnalyticHestonEngine_Integration_simpson__SWIG_0(absTolerance, maxEvaluations), true);
  }

  public static AnalyticHestonEngine_Integration simpson(double absTolerance) {
    return new AnalyticHestonEngine_Integration(QuantLibJNI.AnalyticHestonEngine_Integration_simpson__SWIG_1(absTolerance), true);
  }

  public static AnalyticHestonEngine_Integration trapezoid(double absTolerance, long maxEvaluations) {
    return new AnalyticHestonEngine_Integration(QuantLibJNI.AnalyticHestonEngine_Integration_trapezoid__SWIG_0(absTolerance, maxEvaluations), true);
  }

  public static AnalyticHestonEngine_Integration trapezoid(double absTolerance) {
    return new AnalyticHestonEngine_Integration(QuantLibJNI.AnalyticHestonEngine_Integration_trapezoid__SWIG_1(absTolerance), true);
  }

  public static AnalyticHestonEngine_Integration discreteSimpson(long evaluation) {
    return new AnalyticHestonEngine_Integration(QuantLibJNI.AnalyticHestonEngine_Integration_discreteSimpson__SWIG_0(evaluation), true);
  }

  public static AnalyticHestonEngine_Integration discreteSimpson() {
    return new AnalyticHestonEngine_Integration(QuantLibJNI.AnalyticHestonEngine_Integration_discreteSimpson__SWIG_1(), true);
  }

  public static AnalyticHestonEngine_Integration discreteTrapezoid(long evaluation) {
    return new AnalyticHestonEngine_Integration(QuantLibJNI.AnalyticHestonEngine_Integration_discreteTrapezoid__SWIG_0(evaluation), true);
  }

  public static AnalyticHestonEngine_Integration discreteTrapezoid() {
    return new AnalyticHestonEngine_Integration(QuantLibJNI.AnalyticHestonEngine_Integration_discreteTrapezoid__SWIG_1(), true);
  }

  public static double andersenPiterbargIntegrationLimit(double c_inf, double epsilon, double v0, double t) {
    return QuantLibJNI.AnalyticHestonEngine_Integration_andersenPiterbargIntegrationLimit(c_inf, epsilon, v0, t);
  }

  public double calculate(double c_inf, SWIGTYPE_p_ext__functionT_double_fdoubleF_t f, double maxBound) {
    return QuantLibJNI.AnalyticHestonEngine_Integration_calculate__SWIG_0(swigCPtr, this, c_inf, SWIGTYPE_p_ext__functionT_double_fdoubleF_t.getCPtr(f), maxBound);
  }

  public double calculate(double c_inf, SWIGTYPE_p_ext__functionT_double_fdoubleF_t f) {
    return QuantLibJNI.AnalyticHestonEngine_Integration_calculate__SWIG_1(swigCPtr, this, c_inf, SWIGTYPE_p_ext__functionT_double_fdoubleF_t.getCPtr(f));
  }

  public long numberOfEvaluations() {
    return QuantLibJNI.AnalyticHestonEngine_Integration_numberOfEvaluations(swigCPtr, this);
  }

  public boolean isAdaptiveIntegration() {
    return QuantLibJNI.AnalyticHestonEngine_Integration_isAdaptiveIntegration(swigCPtr, this);
  }

}
