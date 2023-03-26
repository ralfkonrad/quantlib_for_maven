/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class ContinuousPartialFloatingLookbackOption extends ContinuousFloatingLookbackOption implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected ContinuousPartialFloatingLookbackOption(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.ContinuousPartialFloatingLookbackOption_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ContinuousPartialFloatingLookbackOption obj) {
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
        QuantLibJNI.delete_ContinuousPartialFloatingLookbackOption(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public ContinuousPartialFloatingLookbackOption(double currentMinmax, double lambda, Date lookbackPeriodEnd, TypePayoff payoff, Exercise exercise) {
    this(QuantLibJNI.new_ContinuousPartialFloatingLookbackOption(currentMinmax, lambda, Date.getCPtr(lookbackPeriodEnd), lookbackPeriodEnd, TypePayoff.getCPtr(payoff), payoff, Exercise.getCPtr(exercise), exercise), true);
  }

}
