/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class FdHullWhiteSwaptionEngine extends PricingEngine implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected FdHullWhiteSwaptionEngine(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.FdHullWhiteSwaptionEngine_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(FdHullWhiteSwaptionEngine obj) {
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
        QuantLibJNI.delete_FdHullWhiteSwaptionEngine(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public FdHullWhiteSwaptionEngine(HullWhite model, long tGrid, long xGrid, long dampingSteps, double invEps, FdmSchemeDesc schemeDesc) {
    this(QuantLibJNI.new_FdHullWhiteSwaptionEngine__SWIG_0(HullWhite.getCPtr(model), model, tGrid, xGrid, dampingSteps, invEps, FdmSchemeDesc.getCPtr(schemeDesc), schemeDesc), true);
  }

  public FdHullWhiteSwaptionEngine(HullWhite model, long tGrid, long xGrid, long dampingSteps, double invEps) {
    this(QuantLibJNI.new_FdHullWhiteSwaptionEngine__SWIG_1(HullWhite.getCPtr(model), model, tGrid, xGrid, dampingSteps, invEps), true);
  }

  public FdHullWhiteSwaptionEngine(HullWhite model, long tGrid, long xGrid, long dampingSteps) {
    this(QuantLibJNI.new_FdHullWhiteSwaptionEngine__SWIG_2(HullWhite.getCPtr(model), model, tGrid, xGrid, dampingSteps), true);
  }

  public FdHullWhiteSwaptionEngine(HullWhite model, long tGrid, long xGrid) {
    this(QuantLibJNI.new_FdHullWhiteSwaptionEngine__SWIG_3(HullWhite.getCPtr(model), model, tGrid, xGrid), true);
  }

  public FdHullWhiteSwaptionEngine(HullWhite model, long tGrid) {
    this(QuantLibJNI.new_FdHullWhiteSwaptionEngine__SWIG_4(HullWhite.getCPtr(model), model, tGrid), true);
  }

  public FdHullWhiteSwaptionEngine(HullWhite model) {
    this(QuantLibJNI.new_FdHullWhiteSwaptionEngine__SWIG_5(HullWhite.getCPtr(model), model), true);
  }

}
