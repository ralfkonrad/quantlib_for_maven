/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class CappedFlooredYoYInflationCoupon extends YoYInflationCoupon implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected CappedFlooredYoYInflationCoupon(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.CappedFlooredYoYInflationCoupon_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CappedFlooredYoYInflationCoupon obj) {
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
        QuantLibJNI.delete_CappedFlooredYoYInflationCoupon(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public CappedFlooredYoYInflationCoupon(Date paymentDate, double nominal, Date startDate, Date endDate, long fixingDays, YoYInflationIndex index, Period observationLag, DayCounter dayCounter, double gearing, double spread, double cap, double floor, Date refPeriodStart, Date refPeriodEnd) {
    this(QuantLibJNI.new_CappedFlooredYoYInflationCoupon__SWIG_0(Date.getCPtr(paymentDate), paymentDate, nominal, Date.getCPtr(startDate), startDate, Date.getCPtr(endDate), endDate, fixingDays, YoYInflationIndex.getCPtr(index), index, Period.getCPtr(observationLag), observationLag, DayCounter.getCPtr(dayCounter), dayCounter, gearing, spread, cap, floor, Date.getCPtr(refPeriodStart), refPeriodStart, Date.getCPtr(refPeriodEnd), refPeriodEnd), true);
  }

  public CappedFlooredYoYInflationCoupon(Date paymentDate, double nominal, Date startDate, Date endDate, long fixingDays, YoYInflationIndex index, Period observationLag, DayCounter dayCounter, double gearing, double spread, double cap, double floor, Date refPeriodStart) {
    this(QuantLibJNI.new_CappedFlooredYoYInflationCoupon__SWIG_1(Date.getCPtr(paymentDate), paymentDate, nominal, Date.getCPtr(startDate), startDate, Date.getCPtr(endDate), endDate, fixingDays, YoYInflationIndex.getCPtr(index), index, Period.getCPtr(observationLag), observationLag, DayCounter.getCPtr(dayCounter), dayCounter, gearing, spread, cap, floor, Date.getCPtr(refPeriodStart), refPeriodStart), true);
  }

  public CappedFlooredYoYInflationCoupon(Date paymentDate, double nominal, Date startDate, Date endDate, long fixingDays, YoYInflationIndex index, Period observationLag, DayCounter dayCounter, double gearing, double spread, double cap, double floor) {
    this(QuantLibJNI.new_CappedFlooredYoYInflationCoupon__SWIG_2(Date.getCPtr(paymentDate), paymentDate, nominal, Date.getCPtr(startDate), startDate, Date.getCPtr(endDate), endDate, fixingDays, YoYInflationIndex.getCPtr(index), index, Period.getCPtr(observationLag), observationLag, DayCounter.getCPtr(dayCounter), dayCounter, gearing, spread, cap, floor), true);
  }

  public CappedFlooredYoYInflationCoupon(Date paymentDate, double nominal, Date startDate, Date endDate, long fixingDays, YoYInflationIndex index, Period observationLag, DayCounter dayCounter, double gearing, double spread, double cap) {
    this(QuantLibJNI.new_CappedFlooredYoYInflationCoupon__SWIG_3(Date.getCPtr(paymentDate), paymentDate, nominal, Date.getCPtr(startDate), startDate, Date.getCPtr(endDate), endDate, fixingDays, YoYInflationIndex.getCPtr(index), index, Period.getCPtr(observationLag), observationLag, DayCounter.getCPtr(dayCounter), dayCounter, gearing, spread, cap), true);
  }

  public CappedFlooredYoYInflationCoupon(Date paymentDate, double nominal, Date startDate, Date endDate, long fixingDays, YoYInflationIndex index, Period observationLag, DayCounter dayCounter, double gearing, double spread) {
    this(QuantLibJNI.new_CappedFlooredYoYInflationCoupon__SWIG_4(Date.getCPtr(paymentDate), paymentDate, nominal, Date.getCPtr(startDate), startDate, Date.getCPtr(endDate), endDate, fixingDays, YoYInflationIndex.getCPtr(index), index, Period.getCPtr(observationLag), observationLag, DayCounter.getCPtr(dayCounter), dayCounter, gearing, spread), true);
  }

  public CappedFlooredYoYInflationCoupon(Date paymentDate, double nominal, Date startDate, Date endDate, long fixingDays, YoYInflationIndex index, Period observationLag, DayCounter dayCounter, double gearing) {
    this(QuantLibJNI.new_CappedFlooredYoYInflationCoupon__SWIG_5(Date.getCPtr(paymentDate), paymentDate, nominal, Date.getCPtr(startDate), startDate, Date.getCPtr(endDate), endDate, fixingDays, YoYInflationIndex.getCPtr(index), index, Period.getCPtr(observationLag), observationLag, DayCounter.getCPtr(dayCounter), dayCounter, gearing), true);
  }

  public CappedFlooredYoYInflationCoupon(Date paymentDate, double nominal, Date startDate, Date endDate, long fixingDays, YoYInflationIndex index, Period observationLag, DayCounter dayCounter) {
    this(QuantLibJNI.new_CappedFlooredYoYInflationCoupon__SWIG_6(Date.getCPtr(paymentDate), paymentDate, nominal, Date.getCPtr(startDate), startDate, Date.getCPtr(endDate), endDate, fixingDays, YoYInflationIndex.getCPtr(index), index, Period.getCPtr(observationLag), observationLag, DayCounter.getCPtr(dayCounter), dayCounter), true);
  }

  public double rate() {
    return QuantLibJNI.CappedFlooredYoYInflationCoupon_rate(swigCPtr, this);
  }

  public double cap() {
    return QuantLibJNI.CappedFlooredYoYInflationCoupon_cap(swigCPtr, this);
  }

  public double floor() {
    return QuantLibJNI.CappedFlooredYoYInflationCoupon_floor(swigCPtr, this);
  }

  public double effectiveCap() {
    return QuantLibJNI.CappedFlooredYoYInflationCoupon_effectiveCap(swigCPtr, this);
  }

  public double effectiveFloor() {
    return QuantLibJNI.CappedFlooredYoYInflationCoupon_effectiveFloor(swigCPtr, this);
  }

  public double underlyingRate() {
    return QuantLibJNI.CappedFlooredYoYInflationCoupon_underlyingRate(swigCPtr, this);
  }

  public boolean isCapped() {
    return QuantLibJNI.CappedFlooredYoYInflationCoupon_isCapped(swigCPtr, this);
  }

  public boolean isFloored() {
    return QuantLibJNI.CappedFlooredYoYInflationCoupon_isFloored(swigCPtr, this);
  }

}
