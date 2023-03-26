/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class RebatedExercise extends Exercise implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected RebatedExercise(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.RebatedExercise_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(RebatedExercise obj) {
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
        QuantLibJNI.delete_RebatedExercise(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public RebatedExercise(Exercise exercise, DoubleVector rebates, long rebateSettlementDays, Calendar rebatePaymentCalendar, BusinessDayConvention rebatePaymentConvention) {
    this(QuantLibJNI.new_RebatedExercise__SWIG_0(Exercise.getCPtr(exercise), exercise, DoubleVector.getCPtr(rebates), rebates, rebateSettlementDays, Calendar.getCPtr(rebatePaymentCalendar), rebatePaymentCalendar, rebatePaymentConvention.swigValue()), true);
  }

  public RebatedExercise(Exercise exercise, DoubleVector rebates, long rebateSettlementDays, Calendar rebatePaymentCalendar) {
    this(QuantLibJNI.new_RebatedExercise__SWIG_1(Exercise.getCPtr(exercise), exercise, DoubleVector.getCPtr(rebates), rebates, rebateSettlementDays, Calendar.getCPtr(rebatePaymentCalendar), rebatePaymentCalendar), true);
  }

  public RebatedExercise(Exercise exercise, DoubleVector rebates, long rebateSettlementDays) {
    this(QuantLibJNI.new_RebatedExercise__SWIG_2(Exercise.getCPtr(exercise), exercise, DoubleVector.getCPtr(rebates), rebates, rebateSettlementDays), true);
  }

  public RebatedExercise(Exercise exercise, DoubleVector rebates) {
    this(QuantLibJNI.new_RebatedExercise__SWIG_3(Exercise.getCPtr(exercise), exercise, DoubleVector.getCPtr(rebates), rebates), true);
  }

}
