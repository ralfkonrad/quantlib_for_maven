/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class ConstantParameter extends Parameter implements AutoCloseable {
  private transient long swigCPtr;

  protected ConstantParameter(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.ConstantParameter_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ConstantParameter obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(ConstantParameter obj) {
    long ptr = 0;
    if (obj != null) {
      if (!obj.swigCMemOwn)
        throw new RuntimeException("Cannot release ownership as memory is not owned");
      ptr = obj.swigCPtr;
      obj.swigCMemOwn = false;
      obj.delete();
    }
    return ptr;
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        QuantLibJNI.delete_ConstantParameter(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public ConstantParameter(Constraint constraint) {
    this(QuantLibJNI.new_ConstantParameter__SWIG_0(Constraint.getCPtr(constraint), constraint), true);
  }

  public ConstantParameter(double value, Constraint constraint) {
    this(QuantLibJNI.new_ConstantParameter__SWIG_1(value, Constraint.getCPtr(constraint), constraint), true);
  }

}
