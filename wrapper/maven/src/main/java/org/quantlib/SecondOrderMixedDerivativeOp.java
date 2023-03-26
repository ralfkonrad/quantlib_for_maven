/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class SecondOrderMixedDerivativeOp extends NinePointLinearOp implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected SecondOrderMixedDerivativeOp(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.SecondOrderMixedDerivativeOp_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(SecondOrderMixedDerivativeOp obj) {
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
        QuantLibJNI.delete_SecondOrderMixedDerivativeOp(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public SecondOrderMixedDerivativeOp(long d0, long d1, FdmMesher mesher) {
    this(QuantLibJNI.new_SecondOrderMixedDerivativeOp(d0, d1, FdmMesher.getCPtr(mesher), mesher), true);
  }

}
