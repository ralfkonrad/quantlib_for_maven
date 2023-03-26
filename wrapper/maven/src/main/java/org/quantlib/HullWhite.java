/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class HullWhite extends Vasicek implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected HullWhite(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.HullWhite_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(HullWhite obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void swigSetCMemOwn(boolean own) {
    swigCMemOwnDerived = own;
    super.swigSetCMemOwn(own);
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwnDerived) {
        swigCMemOwnDerived = false;
        QuantLibJNI.delete_HullWhite(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public HullWhite(YieldTermStructureHandle termStructure, double a, double sigma) {
    this(QuantLibJNI.new_HullWhite__SWIG_0(YieldTermStructureHandle.getCPtr(termStructure), termStructure, a, sigma), true);
  }

  public HullWhite(YieldTermStructureHandle termStructure, double a) {
    this(QuantLibJNI.new_HullWhite__SWIG_1(YieldTermStructureHandle.getCPtr(termStructure), termStructure, a), true);
  }

  public HullWhite(YieldTermStructureHandle termStructure) {
    this(QuantLibJNI.new_HullWhite__SWIG_2(YieldTermStructureHandle.getCPtr(termStructure), termStructure), true);
  }

  public static double convexityBias(double futurePrice, double t, double T, double sigma, double a) {
    return QuantLibJNI.HullWhite_convexityBias(futurePrice, t, T, sigma, a);
  }

  public YieldTermStructureHandle termStructure() {
    return new YieldTermStructureHandle(QuantLibJNI.HullWhite_termStructure(swigCPtr, this), false);
  }

}
