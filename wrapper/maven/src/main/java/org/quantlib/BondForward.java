/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class BondForward extends Forward implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected BondForward(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.BondForward_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(BondForward obj) {
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
        QuantLibJNI.delete_BondForward(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public BondForward(Date valueDate, Date maturityDate, Position.Type type, double strike, long settlementDays, DayCounter dayCounter, Calendar calendar, BusinessDayConvention businessDayConvention, Bond bond, YieldTermStructureHandle discountCurve, YieldTermStructureHandle incomeDiscountCurve) {
    this(QuantLibJNI.new_BondForward__SWIG_0(Date.getCPtr(valueDate), valueDate, Date.getCPtr(maturityDate), maturityDate, type.swigValue(), strike, settlementDays, DayCounter.getCPtr(dayCounter), dayCounter, Calendar.getCPtr(calendar), calendar, businessDayConvention.swigValue(), Bond.getCPtr(bond), bond, YieldTermStructureHandle.getCPtr(discountCurve), discountCurve, YieldTermStructureHandle.getCPtr(incomeDiscountCurve), incomeDiscountCurve), true);
  }

  public BondForward(Date valueDate, Date maturityDate, Position.Type type, double strike, long settlementDays, DayCounter dayCounter, Calendar calendar, BusinessDayConvention businessDayConvention, Bond bond, YieldTermStructureHandle discountCurve) {
    this(QuantLibJNI.new_BondForward__SWIG_1(Date.getCPtr(valueDate), valueDate, Date.getCPtr(maturityDate), maturityDate, type.swigValue(), strike, settlementDays, DayCounter.getCPtr(dayCounter), dayCounter, Calendar.getCPtr(calendar), calendar, businessDayConvention.swigValue(), Bond.getCPtr(bond), bond, YieldTermStructureHandle.getCPtr(discountCurve), discountCurve), true);
  }

  public BondForward(Date valueDate, Date maturityDate, Position.Type type, double strike, long settlementDays, DayCounter dayCounter, Calendar calendar, BusinessDayConvention businessDayConvention, Bond bond) {
    this(QuantLibJNI.new_BondForward__SWIG_2(Date.getCPtr(valueDate), valueDate, Date.getCPtr(maturityDate), maturityDate, type.swigValue(), strike, settlementDays, DayCounter.getCPtr(dayCounter), dayCounter, Calendar.getCPtr(calendar), calendar, businessDayConvention.swigValue(), Bond.getCPtr(bond), bond), true);
  }

  public double forwardPrice() {
    return QuantLibJNI.BondForward_forwardPrice(swigCPtr, this);
  }

  public double cleanForwardPrice() {
    return QuantLibJNI.BondForward_cleanForwardPrice(swigCPtr, this);
  }

}
