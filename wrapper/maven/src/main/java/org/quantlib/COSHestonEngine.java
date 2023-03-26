/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class COSHestonEngine extends PricingEngine implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected COSHestonEngine(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.COSHestonEngine_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(COSHestonEngine obj) {
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
        QuantLibJNI.delete_COSHestonEngine(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public COSHestonEngine(HestonModel model, double L, long N) {
    this(QuantLibJNI.new_COSHestonEngine__SWIG_0(HestonModel.getCPtr(model), model, L, N), true);
  }

  public COSHestonEngine(HestonModel model, double L) {
    this(QuantLibJNI.new_COSHestonEngine__SWIG_1(HestonModel.getCPtr(model), model, L), true);
  }

  public COSHestonEngine(HestonModel model) {
    this(QuantLibJNI.new_COSHestonEngine__SWIG_2(HestonModel.getCPtr(model), model), true);
  }

}
