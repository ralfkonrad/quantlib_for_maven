/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class SequenceStatistics implements AutoCloseable {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected SequenceStatistics(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(SequenceStatistics obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(SequenceStatistics obj) {
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
        QuantLibJNI.delete_SequenceStatistics(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  @Override
  public void close() {
   this.delete();
  }

  public SequenceStatistics(long dimension) {
    this(QuantLibJNI.new_SequenceStatistics(dimension), true);
  }

  public long size() {
    return QuantLibJNI.SequenceStatistics_size(swigCPtr, this);
  }

  public long samples() {
    return QuantLibJNI.SequenceStatistics_samples(swigCPtr, this);
  }

  public double weightSum() {
    return QuantLibJNI.SequenceStatistics_weightSum(swigCPtr, this);
  }

  public DoubleVector mean() {
    return new DoubleVector(QuantLibJNI.SequenceStatistics_mean(swigCPtr, this), true);
  }

  public DoubleVector variance() {
    return new DoubleVector(QuantLibJNI.SequenceStatistics_variance(swigCPtr, this), true);
  }

  public DoubleVector standardDeviation() {
    return new DoubleVector(QuantLibJNI.SequenceStatistics_standardDeviation(swigCPtr, this), true);
  }

  public DoubleVector errorEstimate() {
    return new DoubleVector(QuantLibJNI.SequenceStatistics_errorEstimate(swigCPtr, this), true);
  }

  public DoubleVector skewness() {
    return new DoubleVector(QuantLibJNI.SequenceStatistics_skewness(swigCPtr, this), true);
  }

  public DoubleVector kurtosis() {
    return new DoubleVector(QuantLibJNI.SequenceStatistics_kurtosis(swigCPtr, this), true);
  }

  public DoubleVector min() {
    return new DoubleVector(QuantLibJNI.SequenceStatistics_min(swigCPtr, this), true);
  }

  public DoubleVector max() {
    return new DoubleVector(QuantLibJNI.SequenceStatistics_max(swigCPtr, this), true);
  }

  public Matrix covariance() {
    return new Matrix(QuantLibJNI.SequenceStatistics_covariance(swigCPtr, this), true);
  }

  public Matrix correlation() {
    return new Matrix(QuantLibJNI.SequenceStatistics_correlation(swigCPtr, this), true);
  }

  public void reset() {
    QuantLibJNI.SequenceStatistics_reset(swigCPtr, this);
  }

  public void add(DoubleVector value, double weight) {
    QuantLibJNI.SequenceStatistics_add__SWIG_0(swigCPtr, this, DoubleVector.getCPtr(value), value, weight);
  }

  public void add(DoubleVector value) {
    QuantLibJNI.SequenceStatistics_add__SWIG_1(swigCPtr, this, DoubleVector.getCPtr(value), value);
  }

  public void add(Array value, double weight) {
    QuantLibJNI.SequenceStatistics_add__SWIG_2(swigCPtr, this, Array.getCPtr(value), value, weight);
  }

  public void add(Array value) {
    QuantLibJNI.SequenceStatistics_add__SWIG_3(swigCPtr, this, Array.getCPtr(value), value);
  }

}
