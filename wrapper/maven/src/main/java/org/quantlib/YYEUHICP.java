/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class YYEUHICP extends YoYInflationIndex implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected YYEUHICP(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.YYEUHICP_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(YYEUHICP obj) {
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
        QuantLibJNI.delete_YYEUHICP(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public YYEUHICP(boolean interpolated, YoYInflationTermStructureHandle h) {
    this(QuantLibJNI.new_YYEUHICP__SWIG_0(interpolated, YoYInflationTermStructureHandle.getCPtr(h), h), true);
  }

  public YYEUHICP(boolean interpolated) {
    this(QuantLibJNI.new_YYEUHICP__SWIG_1(interpolated), true);
  }

}
