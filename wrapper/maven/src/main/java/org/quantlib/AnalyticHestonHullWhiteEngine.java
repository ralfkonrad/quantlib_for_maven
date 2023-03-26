/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class AnalyticHestonHullWhiteEngine extends PricingEngine implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected AnalyticHestonHullWhiteEngine(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.AnalyticHestonHullWhiteEngine_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(AnalyticHestonHullWhiteEngine obj) {
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
        QuantLibJNI.delete_AnalyticHestonHullWhiteEngine(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public AnalyticHestonHullWhiteEngine(HestonModel hestonModel, SWIGTYPE_p_ext__shared_ptrT_HullWhite_t hullWhiteModel, long integrationOrder) {
    this(QuantLibJNI.new_AnalyticHestonHullWhiteEngine__SWIG_0(HestonModel.getCPtr(hestonModel), hestonModel, SWIGTYPE_p_ext__shared_ptrT_HullWhite_t.getCPtr(hullWhiteModel), integrationOrder), true);
  }

  public AnalyticHestonHullWhiteEngine(HestonModel hestonModel, SWIGTYPE_p_ext__shared_ptrT_HullWhite_t hullWhiteModel) {
    this(QuantLibJNI.new_AnalyticHestonHullWhiteEngine__SWIG_1(HestonModel.getCPtr(hestonModel), hestonModel, SWIGTYPE_p_ext__shared_ptrT_HullWhite_t.getCPtr(hullWhiteModel)), true);
  }

  public AnalyticHestonHullWhiteEngine(HestonModel model, SWIGTYPE_p_ext__shared_ptrT_HullWhite_t hullWhiteModel, double relTolerance, long maxEvaluations) {
    this(QuantLibJNI.new_AnalyticHestonHullWhiteEngine__SWIG_2(HestonModel.getCPtr(model), model, SWIGTYPE_p_ext__shared_ptrT_HullWhite_t.getCPtr(hullWhiteModel), relTolerance, maxEvaluations), true);
  }

}
