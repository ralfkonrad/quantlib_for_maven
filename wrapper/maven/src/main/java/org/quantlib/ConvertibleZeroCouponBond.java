/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class ConvertibleZeroCouponBond extends Bond implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected ConvertibleZeroCouponBond(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.ConvertibleZeroCouponBond_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ConvertibleZeroCouponBond obj) {
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
        QuantLibJNI.delete_ConvertibleZeroCouponBond(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public ConvertibleZeroCouponBond(Exercise exercise, double conversionRatio, CallabilitySchedule callability, Date issueDate, int settlementDays, DayCounter dayCounter, Schedule schedule, double redemption) {
    this(QuantLibJNI.new_ConvertibleZeroCouponBond__SWIG_0(Exercise.getCPtr(exercise), exercise, conversionRatio, CallabilitySchedule.getCPtr(callability), callability, Date.getCPtr(issueDate), issueDate, settlementDays, DayCounter.getCPtr(dayCounter), dayCounter, Schedule.getCPtr(schedule), schedule, redemption), true);
  }

  public ConvertibleZeroCouponBond(Exercise exercise, double conversionRatio, CallabilitySchedule callability, Date issueDate, int settlementDays, DayCounter dayCounter, Schedule schedule) {
    this(QuantLibJNI.new_ConvertibleZeroCouponBond__SWIG_1(Exercise.getCPtr(exercise), exercise, conversionRatio, CallabilitySchedule.getCPtr(callability), callability, Date.getCPtr(issueDate), issueDate, settlementDays, DayCounter.getCPtr(dayCounter), dayCounter, Schedule.getCPtr(schedule), schedule), true);
  }

}
