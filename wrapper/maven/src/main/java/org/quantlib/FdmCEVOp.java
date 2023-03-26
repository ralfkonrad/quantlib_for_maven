/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class FdmCEVOp extends FdmLinearOpComposite implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected FdmCEVOp(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.FdmCEVOp_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(FdmCEVOp obj) {
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
        QuantLibJNI.delete_FdmCEVOp(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public FdmCEVOp(FdmMesher mesher, YieldTermStructure rTS, double f0, double alpha, double beta, long direction) {
    this(QuantLibJNI.new_FdmCEVOp(FdmMesher.getCPtr(mesher), mesher, YieldTermStructure.getCPtr(rTS), rTS, f0, alpha, beta, direction), true);
  }

}
