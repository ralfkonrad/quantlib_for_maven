/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class FFTVarianceGammaEngine extends PricingEngine implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected FFTVarianceGammaEngine(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.FFTVarianceGammaEngine_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(FFTVarianceGammaEngine obj) {
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
        QuantLibJNI.delete_FFTVarianceGammaEngine(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public FFTVarianceGammaEngine(VarianceGammaProcess process, double logStrikeSpacing) {
    this(QuantLibJNI.new_FFTVarianceGammaEngine__SWIG_0(VarianceGammaProcess.getCPtr(process), process, logStrikeSpacing), true);
  }

  public FFTVarianceGammaEngine(VarianceGammaProcess process) {
    this(QuantLibJNI.new_FFTVarianceGammaEngine__SWIG_1(VarianceGammaProcess.getCPtr(process), process), true);
  }

  public void precalculate(InstrumentVector optionList) {
    QuantLibJNI.FFTVarianceGammaEngine_precalculate(swigCPtr, this, InstrumentVector.getCPtr(optionList), optionList);
  }

}
