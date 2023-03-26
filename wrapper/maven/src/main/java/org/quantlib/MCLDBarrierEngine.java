/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class MCLDBarrierEngine extends PricingEngine implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected MCLDBarrierEngine(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.MCLDBarrierEngine_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(MCLDBarrierEngine obj) {
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
        QuantLibJNI.delete_MCLDBarrierEngine(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public MCLDBarrierEngine(GeneralizedBlackScholesProcess process, int timeSteps, int timeStepsPerYear, boolean brownianBridge, boolean antitheticVariate, int requiredSamples, double requiredTolerance, int maxSamples, boolean isBiased, int seed) {
    this(QuantLibJNI.new_MCLDBarrierEngine__SWIG_0(GeneralizedBlackScholesProcess.getCPtr(process), process, timeSteps, timeStepsPerYear, brownianBridge, antitheticVariate, requiredSamples, requiredTolerance, maxSamples, isBiased, seed), true);
  }

  public MCLDBarrierEngine(GeneralizedBlackScholesProcess process, int timeSteps, int timeStepsPerYear, boolean brownianBridge, boolean antitheticVariate, int requiredSamples, double requiredTolerance, int maxSamples, boolean isBiased) {
    this(QuantLibJNI.new_MCLDBarrierEngine__SWIG_1(GeneralizedBlackScholesProcess.getCPtr(process), process, timeSteps, timeStepsPerYear, brownianBridge, antitheticVariate, requiredSamples, requiredTolerance, maxSamples, isBiased), true);
  }

  public MCLDBarrierEngine(GeneralizedBlackScholesProcess process, int timeSteps, int timeStepsPerYear, boolean brownianBridge, boolean antitheticVariate, int requiredSamples, double requiredTolerance, int maxSamples) {
    this(QuantLibJNI.new_MCLDBarrierEngine__SWIG_2(GeneralizedBlackScholesProcess.getCPtr(process), process, timeSteps, timeStepsPerYear, brownianBridge, antitheticVariate, requiredSamples, requiredTolerance, maxSamples), true);
  }

  public MCLDBarrierEngine(GeneralizedBlackScholesProcess process, int timeSteps, int timeStepsPerYear, boolean brownianBridge, boolean antitheticVariate, int requiredSamples, double requiredTolerance) {
    this(QuantLibJNI.new_MCLDBarrierEngine__SWIG_3(GeneralizedBlackScholesProcess.getCPtr(process), process, timeSteps, timeStepsPerYear, brownianBridge, antitheticVariate, requiredSamples, requiredTolerance), true);
  }

  public MCLDBarrierEngine(GeneralizedBlackScholesProcess process, int timeSteps, int timeStepsPerYear, boolean brownianBridge, boolean antitheticVariate, int requiredSamples) {
    this(QuantLibJNI.new_MCLDBarrierEngine__SWIG_4(GeneralizedBlackScholesProcess.getCPtr(process), process, timeSteps, timeStepsPerYear, brownianBridge, antitheticVariate, requiredSamples), true);
  }

  public MCLDBarrierEngine(GeneralizedBlackScholesProcess process, int timeSteps, int timeStepsPerYear, boolean brownianBridge, boolean antitheticVariate) {
    this(QuantLibJNI.new_MCLDBarrierEngine__SWIG_5(GeneralizedBlackScholesProcess.getCPtr(process), process, timeSteps, timeStepsPerYear, brownianBridge, antitheticVariate), true);
  }

  public MCLDBarrierEngine(GeneralizedBlackScholesProcess process, int timeSteps, int timeStepsPerYear, boolean brownianBridge) {
    this(QuantLibJNI.new_MCLDBarrierEngine__SWIG_6(GeneralizedBlackScholesProcess.getCPtr(process), process, timeSteps, timeStepsPerYear, brownianBridge), true);
  }

  public MCLDBarrierEngine(GeneralizedBlackScholesProcess process, int timeSteps, int timeStepsPerYear) {
    this(QuantLibJNI.new_MCLDBarrierEngine__SWIG_7(GeneralizedBlackScholesProcess.getCPtr(process), process, timeSteps, timeStepsPerYear), true);
  }

  public MCLDBarrierEngine(GeneralizedBlackScholesProcess process, int timeSteps) {
    this(QuantLibJNI.new_MCLDBarrierEngine__SWIG_8(GeneralizedBlackScholesProcess.getCPtr(process), process, timeSteps), true);
  }

  public MCLDBarrierEngine(GeneralizedBlackScholesProcess process) {
    this(QuantLibJNI.new_MCLDBarrierEngine__SWIG_9(GeneralizedBlackScholesProcess.getCPtr(process), process), true);
  }

}
