/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class AnalyticH1HWEngine extends PricingEngine implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected AnalyticH1HWEngine(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.AnalyticH1HWEngine_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(AnalyticH1HWEngine obj) {
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
        QuantLibJNI.delete_AnalyticH1HWEngine(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public AnalyticH1HWEngine(HestonModel hestonModel, SWIGTYPE_p_ext__shared_ptrT_HullWhite_t hullWhiteModel, double rhoSr, long integrationOrder) {
    this(QuantLibJNI.new_AnalyticH1HWEngine__SWIG_0(HestonModel.getCPtr(hestonModel), hestonModel, SWIGTYPE_p_ext__shared_ptrT_HullWhite_t.getCPtr(hullWhiteModel), rhoSr, integrationOrder), true);
  }

  public AnalyticH1HWEngine(HestonModel hestonModel, SWIGTYPE_p_ext__shared_ptrT_HullWhite_t hullWhiteModel, double rhoSr) {
    this(QuantLibJNI.new_AnalyticH1HWEngine__SWIG_1(HestonModel.getCPtr(hestonModel), hestonModel, SWIGTYPE_p_ext__shared_ptrT_HullWhite_t.getCPtr(hullWhiteModel), rhoSr), true);
  }

  public AnalyticH1HWEngine(HestonModel model, SWIGTYPE_p_ext__shared_ptrT_HullWhite_t hullWhiteModel, double rhoSr, double relTolerance, long maxEvaluations) {
    this(QuantLibJNI.new_AnalyticH1HWEngine__SWIG_2(HestonModel.getCPtr(model), model, SWIGTYPE_p_ext__shared_ptrT_HullWhite_t.getCPtr(hullWhiteModel), rhoSr, relTolerance, maxEvaluations), true);
  }

}
