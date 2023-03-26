/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class ZACPI extends ZeroInflationIndex implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected ZACPI(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.ZACPI_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ZACPI obj) {
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
        QuantLibJNI.delete_ZACPI(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public ZACPI(ZeroInflationTermStructureHandle h) {
    this(QuantLibJNI.new_ZACPI__SWIG_0(ZeroInflationTermStructureHandle.getCPtr(h), h), true);
  }

  public ZACPI() {
    this(QuantLibJNI.new_ZACPI__SWIG_1(), true);
  }

  public ZACPI(boolean interpolated, ZeroInflationTermStructureHandle h) {
    this(QuantLibJNI.new_ZACPI__SWIG_2(interpolated, ZeroInflationTermStructureHandle.getCPtr(h), h), true);
  }

  public ZACPI(boolean interpolated) {
    this(QuantLibJNI.new_ZACPI__SWIG_3(interpolated), true);
  }

}
