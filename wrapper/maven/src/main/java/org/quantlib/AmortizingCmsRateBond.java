/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class AmortizingCmsRateBond extends Bond implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected AmortizingCmsRateBond(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.AmortizingCmsRateBond_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(AmortizingCmsRateBond obj) {
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
        QuantLibJNI.delete_AmortizingCmsRateBond(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public AmortizingCmsRateBond(long settlementDays, DoubleVector notionals, Schedule schedule, SwapIndex index, DayCounter paymentDayCounter, BusinessDayConvention paymentConvention, long fixingDays, DoubleVector gearings, DoubleVector spreads, DoubleVector caps, DoubleVector floors, boolean inArrears, Date issueDate) {
    this(QuantLibJNI.new_AmortizingCmsRateBond__SWIG_0(settlementDays, DoubleVector.getCPtr(notionals), notionals, Schedule.getCPtr(schedule), schedule, SwapIndex.getCPtr(index), index, DayCounter.getCPtr(paymentDayCounter), paymentDayCounter, paymentConvention.swigValue(), fixingDays, DoubleVector.getCPtr(gearings), gearings, DoubleVector.getCPtr(spreads), spreads, DoubleVector.getCPtr(caps), caps, DoubleVector.getCPtr(floors), floors, inArrears, Date.getCPtr(issueDate), issueDate), true);
  }

  public AmortizingCmsRateBond(long settlementDays, DoubleVector notionals, Schedule schedule, SwapIndex index, DayCounter paymentDayCounter, BusinessDayConvention paymentConvention, long fixingDays, DoubleVector gearings, DoubleVector spreads, DoubleVector caps, DoubleVector floors, boolean inArrears) {
    this(QuantLibJNI.new_AmortizingCmsRateBond__SWIG_1(settlementDays, DoubleVector.getCPtr(notionals), notionals, Schedule.getCPtr(schedule), schedule, SwapIndex.getCPtr(index), index, DayCounter.getCPtr(paymentDayCounter), paymentDayCounter, paymentConvention.swigValue(), fixingDays, DoubleVector.getCPtr(gearings), gearings, DoubleVector.getCPtr(spreads), spreads, DoubleVector.getCPtr(caps), caps, DoubleVector.getCPtr(floors), floors, inArrears), true);
  }

  public AmortizingCmsRateBond(long settlementDays, DoubleVector notionals, Schedule schedule, SwapIndex index, DayCounter paymentDayCounter, BusinessDayConvention paymentConvention, long fixingDays, DoubleVector gearings, DoubleVector spreads, DoubleVector caps, DoubleVector floors) {
    this(QuantLibJNI.new_AmortizingCmsRateBond__SWIG_2(settlementDays, DoubleVector.getCPtr(notionals), notionals, Schedule.getCPtr(schedule), schedule, SwapIndex.getCPtr(index), index, DayCounter.getCPtr(paymentDayCounter), paymentDayCounter, paymentConvention.swigValue(), fixingDays, DoubleVector.getCPtr(gearings), gearings, DoubleVector.getCPtr(spreads), spreads, DoubleVector.getCPtr(caps), caps, DoubleVector.getCPtr(floors), floors), true);
  }

  public AmortizingCmsRateBond(long settlementDays, DoubleVector notionals, Schedule schedule, SwapIndex index, DayCounter paymentDayCounter, BusinessDayConvention paymentConvention, long fixingDays, DoubleVector gearings, DoubleVector spreads, DoubleVector caps) {
    this(QuantLibJNI.new_AmortizingCmsRateBond__SWIG_3(settlementDays, DoubleVector.getCPtr(notionals), notionals, Schedule.getCPtr(schedule), schedule, SwapIndex.getCPtr(index), index, DayCounter.getCPtr(paymentDayCounter), paymentDayCounter, paymentConvention.swigValue(), fixingDays, DoubleVector.getCPtr(gearings), gearings, DoubleVector.getCPtr(spreads), spreads, DoubleVector.getCPtr(caps), caps), true);
  }

  public AmortizingCmsRateBond(long settlementDays, DoubleVector notionals, Schedule schedule, SwapIndex index, DayCounter paymentDayCounter, BusinessDayConvention paymentConvention, long fixingDays, DoubleVector gearings, DoubleVector spreads) {
    this(QuantLibJNI.new_AmortizingCmsRateBond__SWIG_4(settlementDays, DoubleVector.getCPtr(notionals), notionals, Schedule.getCPtr(schedule), schedule, SwapIndex.getCPtr(index), index, DayCounter.getCPtr(paymentDayCounter), paymentDayCounter, paymentConvention.swigValue(), fixingDays, DoubleVector.getCPtr(gearings), gearings, DoubleVector.getCPtr(spreads), spreads), true);
  }

  public AmortizingCmsRateBond(long settlementDays, DoubleVector notionals, Schedule schedule, SwapIndex index, DayCounter paymentDayCounter, BusinessDayConvention paymentConvention, long fixingDays, DoubleVector gearings) {
    this(QuantLibJNI.new_AmortizingCmsRateBond__SWIG_5(settlementDays, DoubleVector.getCPtr(notionals), notionals, Schedule.getCPtr(schedule), schedule, SwapIndex.getCPtr(index), index, DayCounter.getCPtr(paymentDayCounter), paymentDayCounter, paymentConvention.swigValue(), fixingDays, DoubleVector.getCPtr(gearings), gearings), true);
  }

  public AmortizingCmsRateBond(long settlementDays, DoubleVector notionals, Schedule schedule, SwapIndex index, DayCounter paymentDayCounter, BusinessDayConvention paymentConvention, long fixingDays) {
    this(QuantLibJNI.new_AmortizingCmsRateBond__SWIG_6(settlementDays, DoubleVector.getCPtr(notionals), notionals, Schedule.getCPtr(schedule), schedule, SwapIndex.getCPtr(index), index, DayCounter.getCPtr(paymentDayCounter), paymentDayCounter, paymentConvention.swigValue(), fixingDays), true);
  }

  public AmortizingCmsRateBond(long settlementDays, DoubleVector notionals, Schedule schedule, SwapIndex index, DayCounter paymentDayCounter, BusinessDayConvention paymentConvention) {
    this(QuantLibJNI.new_AmortizingCmsRateBond__SWIG_7(settlementDays, DoubleVector.getCPtr(notionals), notionals, Schedule.getCPtr(schedule), schedule, SwapIndex.getCPtr(index), index, DayCounter.getCPtr(paymentDayCounter), paymentDayCounter, paymentConvention.swigValue()), true);
  }

  public AmortizingCmsRateBond(long settlementDays, DoubleVector notionals, Schedule schedule, SwapIndex index, DayCounter paymentDayCounter) {
    this(QuantLibJNI.new_AmortizingCmsRateBond__SWIG_8(settlementDays, DoubleVector.getCPtr(notionals), notionals, Schedule.getCPtr(schedule), schedule, SwapIndex.getCPtr(index), index, DayCounter.getCPtr(paymentDayCounter), paymentDayCounter), true);
  }

}
