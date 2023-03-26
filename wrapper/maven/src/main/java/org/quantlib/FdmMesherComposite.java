/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class FdmMesherComposite extends FdmMesher implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected FdmMesherComposite(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.FdmMesherComposite_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(FdmMesherComposite obj) {
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
        QuantLibJNI.delete_FdmMesherComposite(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public FdmMesherComposite(FdmLinearOpLayout layout, Fdm1dMesherVector mesher) {
    this(QuantLibJNI.new_FdmMesherComposite__SWIG_0(FdmLinearOpLayout.getCPtr(layout), layout, Fdm1dMesherVector.getCPtr(mesher), mesher), true);
  }

  public FdmMesherComposite(Fdm1dMesherVector mesher) {
    this(QuantLibJNI.new_FdmMesherComposite__SWIG_1(Fdm1dMesherVector.getCPtr(mesher), mesher), true);
  }

  public FdmMesherComposite(Fdm1dMesher mesher) {
    this(QuantLibJNI.new_FdmMesherComposite__SWIG_2(Fdm1dMesher.getCPtr(mesher), mesher), true);
  }

  public FdmMesherComposite(Fdm1dMesher m1, Fdm1dMesher m2) {
    this(QuantLibJNI.new_FdmMesherComposite__SWIG_3(Fdm1dMesher.getCPtr(m1), m1, Fdm1dMesher.getCPtr(m2), m2), true);
  }

  public FdmMesherComposite(Fdm1dMesher m1, Fdm1dMesher m2, Fdm1dMesher m3) {
    this(QuantLibJNI.new_FdmMesherComposite__SWIG_4(Fdm1dMesher.getCPtr(m1), m1, Fdm1dMesher.getCPtr(m2), m2, Fdm1dMesher.getCPtr(m3), m3), true);
  }

  public FdmMesherComposite(Fdm1dMesher m1, Fdm1dMesher m2, Fdm1dMesher m3, Fdm1dMesher m4) {
    this(QuantLibJNI.new_FdmMesherComposite__SWIG_5(Fdm1dMesher.getCPtr(m1), m1, Fdm1dMesher.getCPtr(m2), m2, Fdm1dMesher.getCPtr(m3), m3, Fdm1dMesher.getCPtr(m4), m4), true);
  }

  public Fdm1dMesherVector getFdm1dMeshers() {
    return new Fdm1dMesherVector(QuantLibJNI.FdmMesherComposite_getFdm1dMeshers(swigCPtr, this), false);
  }

}
