/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class BivariateCumulativeNormalDistribution implements AutoCloseable {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected BivariateCumulativeNormalDistribution(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(BivariateCumulativeNormalDistribution obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(BivariateCumulativeNormalDistribution obj) {
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
        QuantLibJNI.delete_BivariateCumulativeNormalDistribution(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  @Override
  public void close() {
   this.delete();
  }

  public BivariateCumulativeNormalDistribution(double rho) {
    this(QuantLibJNI.new_BivariateCumulativeNormalDistribution(rho), true);
  }

  public double getValue(double x, double y) {
    return QuantLibJNI.BivariateCumulativeNormalDistribution_getValue(swigCPtr, this, x, y);
  }

}
