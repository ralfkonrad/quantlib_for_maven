/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class IntervalPriceTimeSeries implements AutoCloseable {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected IntervalPriceTimeSeries(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(IntervalPriceTimeSeries obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(IntervalPriceTimeSeries obj) {
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
        QuantLibJNI.delete_IntervalPriceTimeSeries(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  @Override
  public void close() {
   this.delete();
  }

  public IntervalPriceTimeSeries() {
    this(QuantLibJNI.new_IntervalPriceTimeSeries__SWIG_0(), true);
  }

  public IntervalPriceTimeSeries(DateVector d, IntervalPriceVector v) {
    this(QuantLibJNI.new_IntervalPriceTimeSeries__SWIG_1(DateVector.getCPtr(d), d, IntervalPriceVector.getCPtr(v), v), true);
  }

  public DateVector dates() {
    return new DateVector(QuantLibJNI.IntervalPriceTimeSeries_dates(swigCPtr, this), true);
  }

  public IntervalPriceVector values() {
    return new IntervalPriceVector(QuantLibJNI.IntervalPriceTimeSeries_values(swigCPtr, this), true);
  }

  public long size() {
    return QuantLibJNI.IntervalPriceTimeSeries_size(swigCPtr, this);
  }

}
