/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class MultiAssetOption extends Option implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected MultiAssetOption(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.MultiAssetOption_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(MultiAssetOption obj) {
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
        QuantLibJNI.delete_MultiAssetOption(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public double delta() {
    return QuantLibJNI.MultiAssetOption_delta(swigCPtr, this);
  }

  public double gamma() {
    return QuantLibJNI.MultiAssetOption_gamma(swigCPtr, this);
  }

  public double theta() {
    return QuantLibJNI.MultiAssetOption_theta(swigCPtr, this);
  }

  public double vega() {
    return QuantLibJNI.MultiAssetOption_vega(swigCPtr, this);
  }

  public double rho() {
    return QuantLibJNI.MultiAssetOption_rho(swigCPtr, this);
  }

  public double dividendRho() {
    return QuantLibJNI.MultiAssetOption_dividendRho(swigCPtr, this);
  }

}
