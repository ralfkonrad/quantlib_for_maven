/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class MCPRHimalayaEngine extends PricingEngine implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected MCPRHimalayaEngine(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.MCPRHimalayaEngine_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(MCPRHimalayaEngine obj) {
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
        QuantLibJNI.delete_MCPRHimalayaEngine(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public MCPRHimalayaEngine(StochasticProcessArray process, boolean brownianBridge, boolean antitheticVariate, int requiredSamples, double requiredTolerance, int maxSamples, int seed) {
    this(QuantLibJNI.new_MCPRHimalayaEngine__SWIG_0(StochasticProcessArray.getCPtr(process), process, brownianBridge, antitheticVariate, requiredSamples, requiredTolerance, maxSamples, seed), true);
  }

  public MCPRHimalayaEngine(StochasticProcessArray process, boolean brownianBridge, boolean antitheticVariate, int requiredSamples, double requiredTolerance, int maxSamples) {
    this(QuantLibJNI.new_MCPRHimalayaEngine__SWIG_1(StochasticProcessArray.getCPtr(process), process, brownianBridge, antitheticVariate, requiredSamples, requiredTolerance, maxSamples), true);
  }

  public MCPRHimalayaEngine(StochasticProcessArray process, boolean brownianBridge, boolean antitheticVariate, int requiredSamples, double requiredTolerance) {
    this(QuantLibJNI.new_MCPRHimalayaEngine__SWIG_2(StochasticProcessArray.getCPtr(process), process, brownianBridge, antitheticVariate, requiredSamples, requiredTolerance), true);
  }

  public MCPRHimalayaEngine(StochasticProcessArray process, boolean brownianBridge, boolean antitheticVariate, int requiredSamples) {
    this(QuantLibJNI.new_MCPRHimalayaEngine__SWIG_3(StochasticProcessArray.getCPtr(process), process, brownianBridge, antitheticVariate, requiredSamples), true);
  }

  public MCPRHimalayaEngine(StochasticProcessArray process, boolean brownianBridge, boolean antitheticVariate) {
    this(QuantLibJNI.new_MCPRHimalayaEngine__SWIG_4(StochasticProcessArray.getCPtr(process), process, brownianBridge, antitheticVariate), true);
  }

  public MCPRHimalayaEngine(StochasticProcessArray process, boolean brownianBridge) {
    this(QuantLibJNI.new_MCPRHimalayaEngine__SWIG_5(StochasticProcessArray.getCPtr(process), process, brownianBridge), true);
  }

  public MCPRHimalayaEngine(StochasticProcessArray process) {
    this(QuantLibJNI.new_MCPRHimalayaEngine__SWIG_6(StochasticProcessArray.getCPtr(process), process), true);
  }

}
