/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class FittingMethod implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwn;

  protected FittingMethod(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(FittingMethod obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void swigSetCMemOwn(boolean own) {
    swigCMemOwn = own;
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        QuantLibJNI.delete_FittingMethod(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  @Override
  public void close() {
   this.delete();
  }

  public long size() {
    return QuantLibJNI.FittingMethod_size(swigCPtr, this);
  }

  public Array solution() {
    return new Array(QuantLibJNI.FittingMethod_solution(swigCPtr, this), true);
  }

  public int numberOfIterations() {
    return QuantLibJNI.FittingMethod_numberOfIterations(swigCPtr, this);
  }

  public double minimumCostValue() {
    return QuantLibJNI.FittingMethod_minimumCostValue(swigCPtr, this);
  }

  public boolean constrainAtZero() {
    return QuantLibJNI.FittingMethod_constrainAtZero(swigCPtr, this);
  }

  public Array weights() {
    return new Array(QuantLibJNI.FittingMethod_weights(swigCPtr, this), true);
  }

}
