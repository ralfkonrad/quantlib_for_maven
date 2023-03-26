/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class Concentrating1dMesherPoint implements AutoCloseable {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected Concentrating1dMesherPoint(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(Concentrating1dMesherPoint obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(Concentrating1dMesherPoint obj) {
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
        QuantLibJNI.delete_Concentrating1dMesherPoint(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  @Override
  public void close() {
   this.delete();
  }

  public Concentrating1dMesherPoint(double arg0, double arg1, boolean arg2) {
    this(QuantLibJNI.new_Concentrating1dMesherPoint(arg0, arg1, arg2), true);
  }

  public double first() {
    return QuantLibJNI.Concentrating1dMesherPoint_first(swigCPtr, this);
  }

  public double second() {
    return QuantLibJNI.Concentrating1dMesherPoint_second(swigCPtr, this);
  }

  public boolean third() {
    return QuantLibJNI.Concentrating1dMesherPoint_third(swigCPtr, this);
  }

}
