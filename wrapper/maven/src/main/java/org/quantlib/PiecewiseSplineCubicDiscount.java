/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class PiecewiseSplineCubicDiscount extends YieldTermStructure implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected PiecewiseSplineCubicDiscount(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.PiecewiseSplineCubicDiscount_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(PiecewiseSplineCubicDiscount obj) {
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
        QuantLibJNI.delete_PiecewiseSplineCubicDiscount(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public PiecewiseSplineCubicDiscount(Date referenceDate, RateHelperVector instruments, DayCounter dayCounter, QuoteHandleVector jumps, DateVector jumpDates, SplineCubic i, IterativeBootstrap b) {
    this(QuantLibJNI.new_PiecewiseSplineCubicDiscount__SWIG_0(Date.getCPtr(referenceDate), referenceDate, RateHelperVector.getCPtr(instruments), instruments, DayCounter.getCPtr(dayCounter), dayCounter, QuoteHandleVector.getCPtr(jumps), jumps, DateVector.getCPtr(jumpDates), jumpDates, SplineCubic.getCPtr(i), i, IterativeBootstrap.getCPtr(b), b), true);
  }

  public PiecewiseSplineCubicDiscount(Date referenceDate, RateHelperVector instruments, DayCounter dayCounter, QuoteHandleVector jumps, DateVector jumpDates, SplineCubic i) {
    this(QuantLibJNI.new_PiecewiseSplineCubicDiscount__SWIG_1(Date.getCPtr(referenceDate), referenceDate, RateHelperVector.getCPtr(instruments), instruments, DayCounter.getCPtr(dayCounter), dayCounter, QuoteHandleVector.getCPtr(jumps), jumps, DateVector.getCPtr(jumpDates), jumpDates, SplineCubic.getCPtr(i), i), true);
  }

  public PiecewiseSplineCubicDiscount(Date referenceDate, RateHelperVector instruments, DayCounter dayCounter, QuoteHandleVector jumps, DateVector jumpDates) {
    this(QuantLibJNI.new_PiecewiseSplineCubicDiscount__SWIG_2(Date.getCPtr(referenceDate), referenceDate, RateHelperVector.getCPtr(instruments), instruments, DayCounter.getCPtr(dayCounter), dayCounter, QuoteHandleVector.getCPtr(jumps), jumps, DateVector.getCPtr(jumpDates), jumpDates), true);
  }

  public PiecewiseSplineCubicDiscount(Date referenceDate, RateHelperVector instruments, DayCounter dayCounter, QuoteHandleVector jumps) {
    this(QuantLibJNI.new_PiecewiseSplineCubicDiscount__SWIG_3(Date.getCPtr(referenceDate), referenceDate, RateHelperVector.getCPtr(instruments), instruments, DayCounter.getCPtr(dayCounter), dayCounter, QuoteHandleVector.getCPtr(jumps), jumps), true);
  }

  public PiecewiseSplineCubicDiscount(Date referenceDate, RateHelperVector instruments, DayCounter dayCounter) {
    this(QuantLibJNI.new_PiecewiseSplineCubicDiscount__SWIG_4(Date.getCPtr(referenceDate), referenceDate, RateHelperVector.getCPtr(instruments), instruments, DayCounter.getCPtr(dayCounter), dayCounter), true);
  }

  public PiecewiseSplineCubicDiscount(int settlementDays, Calendar calendar, RateHelperVector instruments, DayCounter dayCounter, QuoteHandleVector jumps, DateVector jumpDates, SplineCubic i, IterativeBootstrap b) {
    this(QuantLibJNI.new_PiecewiseSplineCubicDiscount__SWIG_5(settlementDays, Calendar.getCPtr(calendar), calendar, RateHelperVector.getCPtr(instruments), instruments, DayCounter.getCPtr(dayCounter), dayCounter, QuoteHandleVector.getCPtr(jumps), jumps, DateVector.getCPtr(jumpDates), jumpDates, SplineCubic.getCPtr(i), i, IterativeBootstrap.getCPtr(b), b), true);
  }

  public PiecewiseSplineCubicDiscount(int settlementDays, Calendar calendar, RateHelperVector instruments, DayCounter dayCounter, QuoteHandleVector jumps, DateVector jumpDates, SplineCubic i) {
    this(QuantLibJNI.new_PiecewiseSplineCubicDiscount__SWIG_6(settlementDays, Calendar.getCPtr(calendar), calendar, RateHelperVector.getCPtr(instruments), instruments, DayCounter.getCPtr(dayCounter), dayCounter, QuoteHandleVector.getCPtr(jumps), jumps, DateVector.getCPtr(jumpDates), jumpDates, SplineCubic.getCPtr(i), i), true);
  }

  public PiecewiseSplineCubicDiscount(int settlementDays, Calendar calendar, RateHelperVector instruments, DayCounter dayCounter, QuoteHandleVector jumps, DateVector jumpDates) {
    this(QuantLibJNI.new_PiecewiseSplineCubicDiscount__SWIG_7(settlementDays, Calendar.getCPtr(calendar), calendar, RateHelperVector.getCPtr(instruments), instruments, DayCounter.getCPtr(dayCounter), dayCounter, QuoteHandleVector.getCPtr(jumps), jumps, DateVector.getCPtr(jumpDates), jumpDates), true);
  }

  public PiecewiseSplineCubicDiscount(int settlementDays, Calendar calendar, RateHelperVector instruments, DayCounter dayCounter, QuoteHandleVector jumps) {
    this(QuantLibJNI.new_PiecewiseSplineCubicDiscount__SWIG_8(settlementDays, Calendar.getCPtr(calendar), calendar, RateHelperVector.getCPtr(instruments), instruments, DayCounter.getCPtr(dayCounter), dayCounter, QuoteHandleVector.getCPtr(jumps), jumps), true);
  }

  public PiecewiseSplineCubicDiscount(int settlementDays, Calendar calendar, RateHelperVector instruments, DayCounter dayCounter) {
    this(QuantLibJNI.new_PiecewiseSplineCubicDiscount__SWIG_9(settlementDays, Calendar.getCPtr(calendar), calendar, RateHelperVector.getCPtr(instruments), instruments, DayCounter.getCPtr(dayCounter), dayCounter), true);
  }

  public PiecewiseSplineCubicDiscount(Date referenceDate, RateHelperVector instruments, DayCounter dayCounter, IterativeBootstrap b) {
    this(QuantLibJNI.new_PiecewiseSplineCubicDiscount__SWIG_10(Date.getCPtr(referenceDate), referenceDate, RateHelperVector.getCPtr(instruments), instruments, DayCounter.getCPtr(dayCounter), dayCounter, IterativeBootstrap.getCPtr(b), b), true);
  }

  public PiecewiseSplineCubicDiscount(int settlementDays, Calendar calendar, RateHelperVector instruments, DayCounter dayCounter, IterativeBootstrap b) {
    this(QuantLibJNI.new_PiecewiseSplineCubicDiscount__SWIG_11(settlementDays, Calendar.getCPtr(calendar), calendar, RateHelperVector.getCPtr(instruments), instruments, DayCounter.getCPtr(dayCounter), dayCounter, IterativeBootstrap.getCPtr(b), b), true);
  }

  public DateVector dates() {
    return new DateVector(QuantLibJNI.PiecewiseSplineCubicDiscount_dates(swigCPtr, this), false);
  }

  public DoubleVector times() {
    return new DoubleVector(QuantLibJNI.PiecewiseSplineCubicDiscount_times(swigCPtr, this), false);
  }

  public NodeVector nodes() {
    return new NodeVector(QuantLibJNI.PiecewiseSplineCubicDiscount_nodes(swigCPtr, this), true);
  }

  public void recalculate() {
    QuantLibJNI.PiecewiseSplineCubicDiscount_recalculate(swigCPtr, this);
  }

  public void freeze() {
    QuantLibJNI.PiecewiseSplineCubicDiscount_freeze(swigCPtr, this);
  }

  public void unfreeze() {
    QuantLibJNI.PiecewiseSplineCubicDiscount_unfreeze(swigCPtr, this);
  }

}
