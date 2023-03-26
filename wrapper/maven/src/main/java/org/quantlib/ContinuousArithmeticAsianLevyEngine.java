/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class ContinuousArithmeticAsianLevyEngine extends PricingEngine implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected ContinuousArithmeticAsianLevyEngine(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.ContinuousArithmeticAsianLevyEngine_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ContinuousArithmeticAsianLevyEngine obj) {
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
        QuantLibJNI.delete_ContinuousArithmeticAsianLevyEngine(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public ContinuousArithmeticAsianLevyEngine(GeneralizedBlackScholesProcess process, QuoteHandle runningAverage, Date startDate) {
    this(QuantLibJNI.new_ContinuousArithmeticAsianLevyEngine(GeneralizedBlackScholesProcess.getCPtr(process), process, QuoteHandle.getCPtr(runningAverage), runningAverage, Date.getCPtr(startDate), startDate), true);
  }

}
