/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class FdmSimpleProcess1dMesher extends Fdm1dMesher implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected FdmSimpleProcess1dMesher(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.FdmSimpleProcess1dMesher_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(FdmSimpleProcess1dMesher obj) {
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
        QuantLibJNI.delete_FdmSimpleProcess1dMesher(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public FdmSimpleProcess1dMesher(long size, StochasticProcess1D process, double maturity, long tAvgSteps, double epsilon, double mandatoryPoint) {
    this(QuantLibJNI.new_FdmSimpleProcess1dMesher__SWIG_0(size, StochasticProcess1D.getCPtr(process), process, maturity, tAvgSteps, epsilon, mandatoryPoint), true);
  }

  public FdmSimpleProcess1dMesher(long size, StochasticProcess1D process, double maturity, long tAvgSteps, double epsilon) {
    this(QuantLibJNI.new_FdmSimpleProcess1dMesher__SWIG_1(size, StochasticProcess1D.getCPtr(process), process, maturity, tAvgSteps, epsilon), true);
  }

  public FdmSimpleProcess1dMesher(long size, StochasticProcess1D process, double maturity, long tAvgSteps) {
    this(QuantLibJNI.new_FdmSimpleProcess1dMesher__SWIG_2(size, StochasticProcess1D.getCPtr(process), process, maturity, tAvgSteps), true);
  }

  public FdmSimpleProcess1dMesher(long size, StochasticProcess1D process, double maturity) {
    this(QuantLibJNI.new_FdmSimpleProcess1dMesher__SWIG_3(size, StochasticProcess1D.getCPtr(process), process, maturity), true);
  }

}
