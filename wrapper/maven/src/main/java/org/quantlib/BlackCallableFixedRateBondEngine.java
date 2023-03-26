/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class BlackCallableFixedRateBondEngine extends PricingEngine implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected BlackCallableFixedRateBondEngine(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.BlackCallableFixedRateBondEngine_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(BlackCallableFixedRateBondEngine obj) {
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
        QuantLibJNI.delete_BlackCallableFixedRateBondEngine(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public BlackCallableFixedRateBondEngine(QuoteHandle fwdYieldVol, YieldTermStructureHandle discountCurve) {
    this(QuantLibJNI.new_BlackCallableFixedRateBondEngine(QuoteHandle.getCPtr(fwdYieldVol), fwdYieldVol, YieldTermStructureHandle.getCPtr(discountCurve), discountCurve), true);
  }

}
