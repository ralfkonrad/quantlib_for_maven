/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class AmericanExercise extends Exercise implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected AmericanExercise(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.AmericanExercise_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(AmericanExercise obj) {
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
        QuantLibJNI.delete_AmericanExercise(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public AmericanExercise(Date earliestDate, Date latestDate, boolean payoffAtExpiry) {
    this(QuantLibJNI.new_AmericanExercise__SWIG_0(Date.getCPtr(earliestDate), earliestDate, Date.getCPtr(latestDate), latestDate, payoffAtExpiry), true);
  }

  public AmericanExercise(Date earliestDate, Date latestDate) {
    this(QuantLibJNI.new_AmericanExercise__SWIG_1(Date.getCPtr(earliestDate), earliestDate, Date.getCPtr(latestDate), latestDate), true);
  }

}
