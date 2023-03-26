/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class RelinkableOptionletVolatilityStructureHandle extends OptionletVolatilityStructureHandle implements AutoCloseable {
  private transient long swigCPtr;

  protected RelinkableOptionletVolatilityStructureHandle(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.RelinkableOptionletVolatilityStructureHandle_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(RelinkableOptionletVolatilityStructureHandle obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(RelinkableOptionletVolatilityStructureHandle obj) {
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
        QuantLibJNI.delete_RelinkableOptionletVolatilityStructureHandle(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public RelinkableOptionletVolatilityStructureHandle(OptionletVolatilityStructure arg0) {
    this(QuantLibJNI.new_RelinkableOptionletVolatilityStructureHandle__SWIG_0(OptionletVolatilityStructure.getCPtr(arg0), arg0), true);
  }

  public RelinkableOptionletVolatilityStructureHandle() {
    this(QuantLibJNI.new_RelinkableOptionletVolatilityStructureHandle__SWIG_1(), true);
  }

  public void linkTo(OptionletVolatilityStructure arg0) {
    QuantLibJNI.RelinkableOptionletVolatilityStructureHandle_linkTo(swigCPtr, this, OptionletVolatilityStructure.getCPtr(arg0), arg0);
  }

  public void reset() {
    QuantLibJNI.RelinkableOptionletVolatilityStructureHandle_reset(swigCPtr, this);
  }

}
