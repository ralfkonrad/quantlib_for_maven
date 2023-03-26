/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class YoYInflationCollar extends YoYInflationCapFloor implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected YoYInflationCollar(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.YoYInflationCollar_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(YoYInflationCollar obj) {
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
        QuantLibJNI.delete_YoYInflationCollar(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public YoYInflationCollar(Leg leg, DoubleVector capRates, DoubleVector floorRates) {
    this(QuantLibJNI.new_YoYInflationCollar(Leg.getCPtr(leg), leg, DoubleVector.getCPtr(capRates), capRates, DoubleVector.getCPtr(floorRates), floorRates), true);
  }

}
