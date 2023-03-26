/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class ConstantYoYOptionletVolatility extends YoYOptionletVolatilitySurface implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected ConstantYoYOptionletVolatility(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.ConstantYoYOptionletVolatility_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ConstantYoYOptionletVolatility obj) {
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
        QuantLibJNI.delete_ConstantYoYOptionletVolatility(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public ConstantYoYOptionletVolatility(double volatility, long settlementDays, Calendar cal, BusinessDayConvention bdc, DayCounter dc, Period observationLag, Frequency frequency, boolean indexIsInterpolated, double minStrike, double maxStrike) {
    this(QuantLibJNI.new_ConstantYoYOptionletVolatility__SWIG_0(volatility, settlementDays, Calendar.getCPtr(cal), cal, bdc.swigValue(), DayCounter.getCPtr(dc), dc, Period.getCPtr(observationLag), observationLag, frequency.swigValue(), indexIsInterpolated, minStrike, maxStrike), true);
  }

  public ConstantYoYOptionletVolatility(double volatility, long settlementDays, Calendar cal, BusinessDayConvention bdc, DayCounter dc, Period observationLag, Frequency frequency, boolean indexIsInterpolated, double minStrike) {
    this(QuantLibJNI.new_ConstantYoYOptionletVolatility__SWIG_1(volatility, settlementDays, Calendar.getCPtr(cal), cal, bdc.swigValue(), DayCounter.getCPtr(dc), dc, Period.getCPtr(observationLag), observationLag, frequency.swigValue(), indexIsInterpolated, minStrike), true);
  }

  public ConstantYoYOptionletVolatility(double volatility, long settlementDays, Calendar cal, BusinessDayConvention bdc, DayCounter dc, Period observationLag, Frequency frequency, boolean indexIsInterpolated) {
    this(QuantLibJNI.new_ConstantYoYOptionletVolatility__SWIG_2(volatility, settlementDays, Calendar.getCPtr(cal), cal, bdc.swigValue(), DayCounter.getCPtr(dc), dc, Period.getCPtr(observationLag), observationLag, frequency.swigValue(), indexIsInterpolated), true);
  }

}
