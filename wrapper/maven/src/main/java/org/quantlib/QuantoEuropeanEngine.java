/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class QuantoEuropeanEngine extends PricingEngine implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected QuantoEuropeanEngine(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.QuantoEuropeanEngine_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(QuantoEuropeanEngine obj) {
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
        QuantLibJNI.delete_QuantoEuropeanEngine(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public QuantoEuropeanEngine(GeneralizedBlackScholesProcess process, YieldTermStructureHandle foreignRiskFreeRate, BlackVolTermStructureHandle exchangeRateVolatility, QuoteHandle correlation) {
    this(QuantLibJNI.new_QuantoEuropeanEngine(GeneralizedBlackScholesProcess.getCPtr(process), process, YieldTermStructureHandle.getCPtr(foreignRiskFreeRate), foreignRiskFreeRate, BlackVolTermStructureHandle.getCPtr(exchangeRateVolatility), exchangeRateVolatility, QuoteHandle.getCPtr(correlation), correlation), true);
  }

}
