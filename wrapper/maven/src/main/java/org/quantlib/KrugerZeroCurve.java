/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class KrugerZeroCurve extends YieldTermStructure implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected KrugerZeroCurve(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.KrugerZeroCurve_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(KrugerZeroCurve obj) {
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
        QuantLibJNI.delete_KrugerZeroCurve(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public KrugerZeroCurve(DateVector dates, DoubleVector yields, DayCounter dayCounter, Calendar calendar, Kruger i, Compounding compounding, Frequency frequency) {
    this(QuantLibJNI.new_KrugerZeroCurve__SWIG_0(DateVector.getCPtr(dates), dates, DoubleVector.getCPtr(yields), yields, DayCounter.getCPtr(dayCounter), dayCounter, Calendar.getCPtr(calendar), calendar, Kruger.getCPtr(i), i, compounding.swigValue(), frequency.swigValue()), true);
  }

  public KrugerZeroCurve(DateVector dates, DoubleVector yields, DayCounter dayCounter, Calendar calendar, Kruger i, Compounding compounding) {
    this(QuantLibJNI.new_KrugerZeroCurve__SWIG_1(DateVector.getCPtr(dates), dates, DoubleVector.getCPtr(yields), yields, DayCounter.getCPtr(dayCounter), dayCounter, Calendar.getCPtr(calendar), calendar, Kruger.getCPtr(i), i, compounding.swigValue()), true);
  }

  public KrugerZeroCurve(DateVector dates, DoubleVector yields, DayCounter dayCounter, Calendar calendar, Kruger i) {
    this(QuantLibJNI.new_KrugerZeroCurve__SWIG_2(DateVector.getCPtr(dates), dates, DoubleVector.getCPtr(yields), yields, DayCounter.getCPtr(dayCounter), dayCounter, Calendar.getCPtr(calendar), calendar, Kruger.getCPtr(i), i), true);
  }

  public KrugerZeroCurve(DateVector dates, DoubleVector yields, DayCounter dayCounter, Calendar calendar) {
    this(QuantLibJNI.new_KrugerZeroCurve__SWIG_3(DateVector.getCPtr(dates), dates, DoubleVector.getCPtr(yields), yields, DayCounter.getCPtr(dayCounter), dayCounter, Calendar.getCPtr(calendar), calendar), true);
  }

  public KrugerZeroCurve(DateVector dates, DoubleVector yields, DayCounter dayCounter) {
    this(QuantLibJNI.new_KrugerZeroCurve__SWIG_4(DateVector.getCPtr(dates), dates, DoubleVector.getCPtr(yields), yields, DayCounter.getCPtr(dayCounter), dayCounter), true);
  }

  public DoubleVector times() {
    return new DoubleVector(QuantLibJNI.KrugerZeroCurve_times(swigCPtr, this), false);
  }

  public DoubleVector data() {
    return new DoubleVector(QuantLibJNI.KrugerZeroCurve_data(swigCPtr, this), false);
  }

  public DateVector dates() {
    return new DateVector(QuantLibJNI.KrugerZeroCurve_dates(swigCPtr, this), false);
  }

  public DoubleVector zeroRates() {
    return new DoubleVector(QuantLibJNI.KrugerZeroCurve_zeroRates(swigCPtr, this), false);
  }

  public NodeVector nodes() {
    return new NodeVector(QuantLibJNI.KrugerZeroCurve_nodes(swigCPtr, this), true);
  }

}
