/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class RelinkableCapFloorTermVolatilityStructureHandle extends CapFloorTermVolatilityStructureHandle implements AutoCloseable {
  private transient long swigCPtr;

  protected RelinkableCapFloorTermVolatilityStructureHandle(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.RelinkableCapFloorTermVolatilityStructureHandle_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(RelinkableCapFloorTermVolatilityStructureHandle obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(RelinkableCapFloorTermVolatilityStructureHandle obj) {
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
        QuantLibJNI.delete_RelinkableCapFloorTermVolatilityStructureHandle(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public RelinkableCapFloorTermVolatilityStructureHandle(CapFloorTermVolatilityStructure arg0) {
    this(QuantLibJNI.new_RelinkableCapFloorTermVolatilityStructureHandle__SWIG_0(CapFloorTermVolatilityStructure.getCPtr(arg0), arg0), true);
  }

  public RelinkableCapFloorTermVolatilityStructureHandle() {
    this(QuantLibJNI.new_RelinkableCapFloorTermVolatilityStructureHandle__SWIG_1(), true);
  }

  public void linkTo(CapFloorTermVolatilityStructure arg0) {
    QuantLibJNI.RelinkableCapFloorTermVolatilityStructureHandle_linkTo(swigCPtr, this, CapFloorTermVolatilityStructure.getCPtr(arg0), arg0);
  }

  public void reset() {
    QuantLibJNI.RelinkableCapFloorTermVolatilityStructureHandle_reset(swigCPtr, this);
  }

}
