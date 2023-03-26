/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class DividendBarrierOption extends BarrierOption implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected DividendBarrierOption(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.DividendBarrierOption_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(DividendBarrierOption obj) {
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
        QuantLibJNI.delete_DividendBarrierOption(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public DividendBarrierOption(Barrier.Type barrierType, double barrier, double rebate, StrikedTypePayoff payoff, Exercise exercise, DateVector dividendDates, DoubleVector dividends) {
    this(QuantLibJNI.new_DividendBarrierOption(barrierType.swigValue(), barrier, rebate, StrikedTypePayoff.getCPtr(payoff), payoff, Exercise.getCPtr(exercise), exercise, DateVector.getCPtr(dividendDates), dividendDates, DoubleVector.getCPtr(dividends), dividends), true);
  }

}
