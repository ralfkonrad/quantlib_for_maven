/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class YearOnYearInflationSwapHelper extends YoYHelper implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected YearOnYearInflationSwapHelper(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.YearOnYearInflationSwapHelper_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(YearOnYearInflationSwapHelper obj) {
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
        QuantLibJNI.delete_YearOnYearInflationSwapHelper(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public YearOnYearInflationSwapHelper(QuoteHandle quote, Period lag, Date maturity, Calendar calendar, BusinessDayConvention bdc, DayCounter dayCounter, YoYInflationIndex index, YieldTermStructureHandle nominalTS) {
    this(QuantLibJNI.new_YearOnYearInflationSwapHelper(QuoteHandle.getCPtr(quote), quote, Period.getCPtr(lag), lag, Date.getCPtr(maturity), maturity, Calendar.getCPtr(calendar), calendar, bdc.swigValue(), DayCounter.getCPtr(dayCounter), dayCounter, YoYInflationIndex.getCPtr(index), index, YieldTermStructureHandle.getCPtr(nominalTS), nominalTS), true);
  }

}
