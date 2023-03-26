/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class CappedFlooredIborCoupon extends CappedFlooredCoupon implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected CappedFlooredIborCoupon(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.CappedFlooredIborCoupon_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CappedFlooredIborCoupon obj) {
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
        QuantLibJNI.delete_CappedFlooredIborCoupon(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public CappedFlooredIborCoupon(Date paymentDate, double nominal, Date startDate, Date endDate, int fixingDays, IborIndex index, double gearing, double spread, double cap, double floor, Date refPeriodStart, Date refPeriodEnd, DayCounter dayCounter, boolean isInArrears, Date exCouponDate) {
    this(QuantLibJNI.new_CappedFlooredIborCoupon__SWIG_0(Date.getCPtr(paymentDate), paymentDate, nominal, Date.getCPtr(startDate), startDate, Date.getCPtr(endDate), endDate, fixingDays, IborIndex.getCPtr(index), index, gearing, spread, cap, floor, Date.getCPtr(refPeriodStart), refPeriodStart, Date.getCPtr(refPeriodEnd), refPeriodEnd, DayCounter.getCPtr(dayCounter), dayCounter, isInArrears, Date.getCPtr(exCouponDate), exCouponDate), true);
  }

  public CappedFlooredIborCoupon(Date paymentDate, double nominal, Date startDate, Date endDate, int fixingDays, IborIndex index, double gearing, double spread, double cap, double floor, Date refPeriodStart, Date refPeriodEnd, DayCounter dayCounter, boolean isInArrears) {
    this(QuantLibJNI.new_CappedFlooredIborCoupon__SWIG_1(Date.getCPtr(paymentDate), paymentDate, nominal, Date.getCPtr(startDate), startDate, Date.getCPtr(endDate), endDate, fixingDays, IborIndex.getCPtr(index), index, gearing, spread, cap, floor, Date.getCPtr(refPeriodStart), refPeriodStart, Date.getCPtr(refPeriodEnd), refPeriodEnd, DayCounter.getCPtr(dayCounter), dayCounter, isInArrears), true);
  }

  public CappedFlooredIborCoupon(Date paymentDate, double nominal, Date startDate, Date endDate, int fixingDays, IborIndex index, double gearing, double spread, double cap, double floor, Date refPeriodStart, Date refPeriodEnd, DayCounter dayCounter) {
    this(QuantLibJNI.new_CappedFlooredIborCoupon__SWIG_2(Date.getCPtr(paymentDate), paymentDate, nominal, Date.getCPtr(startDate), startDate, Date.getCPtr(endDate), endDate, fixingDays, IborIndex.getCPtr(index), index, gearing, spread, cap, floor, Date.getCPtr(refPeriodStart), refPeriodStart, Date.getCPtr(refPeriodEnd), refPeriodEnd, DayCounter.getCPtr(dayCounter), dayCounter), true);
  }

  public CappedFlooredIborCoupon(Date paymentDate, double nominal, Date startDate, Date endDate, int fixingDays, IborIndex index, double gearing, double spread, double cap, double floor, Date refPeriodStart, Date refPeriodEnd) {
    this(QuantLibJNI.new_CappedFlooredIborCoupon__SWIG_3(Date.getCPtr(paymentDate), paymentDate, nominal, Date.getCPtr(startDate), startDate, Date.getCPtr(endDate), endDate, fixingDays, IborIndex.getCPtr(index), index, gearing, spread, cap, floor, Date.getCPtr(refPeriodStart), refPeriodStart, Date.getCPtr(refPeriodEnd), refPeriodEnd), true);
  }

  public CappedFlooredIborCoupon(Date paymentDate, double nominal, Date startDate, Date endDate, int fixingDays, IborIndex index, double gearing, double spread, double cap, double floor, Date refPeriodStart) {
    this(QuantLibJNI.new_CappedFlooredIborCoupon__SWIG_4(Date.getCPtr(paymentDate), paymentDate, nominal, Date.getCPtr(startDate), startDate, Date.getCPtr(endDate), endDate, fixingDays, IborIndex.getCPtr(index), index, gearing, spread, cap, floor, Date.getCPtr(refPeriodStart), refPeriodStart), true);
  }

  public CappedFlooredIborCoupon(Date paymentDate, double nominal, Date startDate, Date endDate, int fixingDays, IborIndex index, double gearing, double spread, double cap, double floor) {
    this(QuantLibJNI.new_CappedFlooredIborCoupon__SWIG_5(Date.getCPtr(paymentDate), paymentDate, nominal, Date.getCPtr(startDate), startDate, Date.getCPtr(endDate), endDate, fixingDays, IborIndex.getCPtr(index), index, gearing, spread, cap, floor), true);
  }

  public CappedFlooredIborCoupon(Date paymentDate, double nominal, Date startDate, Date endDate, int fixingDays, IborIndex index, double gearing, double spread, double cap) {
    this(QuantLibJNI.new_CappedFlooredIborCoupon__SWIG_6(Date.getCPtr(paymentDate), paymentDate, nominal, Date.getCPtr(startDate), startDate, Date.getCPtr(endDate), endDate, fixingDays, IborIndex.getCPtr(index), index, gearing, spread, cap), true);
  }

  public CappedFlooredIborCoupon(Date paymentDate, double nominal, Date startDate, Date endDate, int fixingDays, IborIndex index, double gearing, double spread) {
    this(QuantLibJNI.new_CappedFlooredIborCoupon__SWIG_7(Date.getCPtr(paymentDate), paymentDate, nominal, Date.getCPtr(startDate), startDate, Date.getCPtr(endDate), endDate, fixingDays, IborIndex.getCPtr(index), index, gearing, spread), true);
  }

  public CappedFlooredIborCoupon(Date paymentDate, double nominal, Date startDate, Date endDate, int fixingDays, IborIndex index, double gearing) {
    this(QuantLibJNI.new_CappedFlooredIborCoupon__SWIG_8(Date.getCPtr(paymentDate), paymentDate, nominal, Date.getCPtr(startDate), startDate, Date.getCPtr(endDate), endDate, fixingDays, IborIndex.getCPtr(index), index, gearing), true);
  }

  public CappedFlooredIborCoupon(Date paymentDate, double nominal, Date startDate, Date endDate, int fixingDays, IborIndex index) {
    this(QuantLibJNI.new_CappedFlooredIborCoupon__SWIG_9(Date.getCPtr(paymentDate), paymentDate, nominal, Date.getCPtr(startDate), startDate, Date.getCPtr(endDate), endDate, fixingDays, IborIndex.getCPtr(index), index), true);
  }

}
