/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class ZeroInflationCurve extends ZeroInflationTermStructure implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected ZeroInflationCurve(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.ZeroInflationCurve_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ZeroInflationCurve obj) {
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
        QuantLibJNI.delete_ZeroInflationCurve(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public ZeroInflationCurve(Date referenceDate, Calendar calendar, DayCounter dayCounter, Period lag, Frequency frequency, DateVector dates, DoubleVector rates, Linear interpolator) {
    this(QuantLibJNI.new_ZeroInflationCurve__SWIG_0(Date.getCPtr(referenceDate), referenceDate, Calendar.getCPtr(calendar), calendar, DayCounter.getCPtr(dayCounter), dayCounter, Period.getCPtr(lag), lag, frequency.swigValue(), DateVector.getCPtr(dates), dates, DoubleVector.getCPtr(rates), rates, Linear.getCPtr(interpolator), interpolator), true);
  }

  public ZeroInflationCurve(Date referenceDate, Calendar calendar, DayCounter dayCounter, Period lag, Frequency frequency, DateVector dates, DoubleVector rates) {
    this(QuantLibJNI.new_ZeroInflationCurve__SWIG_1(Date.getCPtr(referenceDate), referenceDate, Calendar.getCPtr(calendar), calendar, DayCounter.getCPtr(dayCounter), dayCounter, Period.getCPtr(lag), lag, frequency.swigValue(), DateVector.getCPtr(dates), dates, DoubleVector.getCPtr(rates), rates), true);
  }

  public ZeroInflationCurve(Date referenceDate, Calendar calendar, DayCounter dayCounter, Period lag, Frequency frequency, boolean indexIsInterpolated, DateVector dates, DoubleVector rates, Linear interpolator) {
    this(QuantLibJNI.new_ZeroInflationCurve__SWIG_2(Date.getCPtr(referenceDate), referenceDate, Calendar.getCPtr(calendar), calendar, DayCounter.getCPtr(dayCounter), dayCounter, Period.getCPtr(lag), lag, frequency.swigValue(), indexIsInterpolated, DateVector.getCPtr(dates), dates, DoubleVector.getCPtr(rates), rates, Linear.getCPtr(interpolator), interpolator), true);
  }

  public ZeroInflationCurve(Date referenceDate, Calendar calendar, DayCounter dayCounter, Period lag, Frequency frequency, boolean indexIsInterpolated, DateVector dates, DoubleVector rates) {
    this(QuantLibJNI.new_ZeroInflationCurve__SWIG_3(Date.getCPtr(referenceDate), referenceDate, Calendar.getCPtr(calendar), calendar, DayCounter.getCPtr(dayCounter), dayCounter, Period.getCPtr(lag), lag, frequency.swigValue(), indexIsInterpolated, DateVector.getCPtr(dates), dates, DoubleVector.getCPtr(rates), rates), true);
  }

  public DateVector dates() {
    return new DateVector(QuantLibJNI.ZeroInflationCurve_dates(swigCPtr, this), false);
  }

  public DoubleVector times() {
    return new DoubleVector(QuantLibJNI.ZeroInflationCurve_times(swigCPtr, this), false);
  }

  public DoubleVector data() {
    return new DoubleVector(QuantLibJNI.ZeroInflationCurve_data(swigCPtr, this), false);
  }

  public DoubleVector rates() {
    return new DoubleVector(QuantLibJNI.ZeroInflationCurve_rates(swigCPtr, this), false);
  }

  public NodeVector nodes() {
    return new NodeVector(QuantLibJNI.ZeroInflationCurve_nodes(swigCPtr, this), true);
  }

}
