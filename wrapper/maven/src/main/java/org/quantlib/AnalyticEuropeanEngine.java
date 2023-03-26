/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class AnalyticEuropeanEngine extends PricingEngine implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected AnalyticEuropeanEngine(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.AnalyticEuropeanEngine_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(AnalyticEuropeanEngine obj) {
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
        QuantLibJNI.delete_AnalyticEuropeanEngine(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public AnalyticEuropeanEngine(GeneralizedBlackScholesProcess process) {
    this(QuantLibJNI.new_AnalyticEuropeanEngine__SWIG_0(GeneralizedBlackScholesProcess.getCPtr(process), process), true);
  }

  public AnalyticEuropeanEngine(GeneralizedBlackScholesProcess process, YieldTermStructureHandle discountCurve) {
    this(QuantLibJNI.new_AnalyticEuropeanEngine__SWIG_1(GeneralizedBlackScholesProcess.getCPtr(process), process, YieldTermStructureHandle.getCPtr(discountCurve), discountCurve), true);
  }

}
