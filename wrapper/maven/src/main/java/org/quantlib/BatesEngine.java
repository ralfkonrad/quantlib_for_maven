/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class BatesEngine extends PricingEngine implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected BatesEngine(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.BatesEngine_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(BatesEngine obj) {
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
        QuantLibJNI.delete_BatesEngine(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public BatesEngine(BatesModel model, long integrationOrder) {
    this(QuantLibJNI.new_BatesEngine__SWIG_0(BatesModel.getCPtr(model), model, integrationOrder), true);
  }

  public BatesEngine(BatesModel model) {
    this(QuantLibJNI.new_BatesEngine__SWIG_1(BatesModel.getCPtr(model), model), true);
  }

  public BatesEngine(BatesModel model, double relTolerance, long maxEvaluations) {
    this(QuantLibJNI.new_BatesEngine__SWIG_2(BatesModel.getCPtr(model), model, relTolerance, maxEvaluations), true);
  }

}
