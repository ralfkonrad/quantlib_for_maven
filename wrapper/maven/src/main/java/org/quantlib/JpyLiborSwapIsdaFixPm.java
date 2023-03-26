/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class JpyLiborSwapIsdaFixPm extends SwapIndex implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected JpyLiborSwapIsdaFixPm(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.JpyLiborSwapIsdaFixPm_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(JpyLiborSwapIsdaFixPm obj) {
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
        QuantLibJNI.delete_JpyLiborSwapIsdaFixPm(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public JpyLiborSwapIsdaFixPm(Period tenor, YieldTermStructureHandle h) {
    this(QuantLibJNI.new_JpyLiborSwapIsdaFixPm__SWIG_0(Period.getCPtr(tenor), tenor, YieldTermStructureHandle.getCPtr(h), h), true);
  }

  public JpyLiborSwapIsdaFixPm(Period tenor) {
    this(QuantLibJNI.new_JpyLiborSwapIsdaFixPm__SWIG_1(Period.getCPtr(tenor), tenor), true);
  }

  public JpyLiborSwapIsdaFixPm(Period tenor, YieldTermStructureHandle h1, YieldTermStructureHandle h2) {
    this(QuantLibJNI.new_JpyLiborSwapIsdaFixPm__SWIG_2(Period.getCPtr(tenor), tenor, YieldTermStructureHandle.getCPtr(h1), h1, YieldTermStructureHandle.getCPtr(h2), h2), true);
  }

}
