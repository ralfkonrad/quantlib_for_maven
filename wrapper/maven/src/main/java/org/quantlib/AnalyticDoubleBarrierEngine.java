/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class AnalyticDoubleBarrierEngine extends PricingEngine implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected AnalyticDoubleBarrierEngine(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.AnalyticDoubleBarrierEngine_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(AnalyticDoubleBarrierEngine obj) {
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
        QuantLibJNI.delete_AnalyticDoubleBarrierEngine(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public AnalyticDoubleBarrierEngine(GeneralizedBlackScholesProcess process, int series) {
    this(QuantLibJNI.new_AnalyticDoubleBarrierEngine__SWIG_0(GeneralizedBlackScholesProcess.getCPtr(process), process, series), true);
  }

  public AnalyticDoubleBarrierEngine(GeneralizedBlackScholesProcess process) {
    this(QuantLibJNI.new_AnalyticDoubleBarrierEngine__SWIG_1(GeneralizedBlackScholesProcess.getCPtr(process), process), true);
  }

}
