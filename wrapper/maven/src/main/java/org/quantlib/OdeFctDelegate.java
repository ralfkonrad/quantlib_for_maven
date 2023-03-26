/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class OdeFctDelegate implements AutoCloseable {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected OdeFctDelegate(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(OdeFctDelegate obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(OdeFctDelegate obj) {
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
        QuantLibJNI.delete_OdeFctDelegate(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  protected void swigDirectorDisconnect() {
    swigCMemOwn = false;
    delete();
  }

  public void swigReleaseOwnership() {
    swigCMemOwn = false;
    QuantLibJNI.OdeFctDelegate_change_ownership(this, swigCPtr, false);
  }

  public void swigTakeOwnership() {
    swigCMemOwn = true;
    QuantLibJNI.OdeFctDelegate_change_ownership(this, swigCPtr, true);
  }

  @Override
  public void close() {
   this.delete();
  }

  public DoubleVector value(double x, DoubleVector y) {
    return new DoubleVector((getClass() == OdeFctDelegate.class) ? QuantLibJNI.OdeFctDelegate_value(swigCPtr, this, x, DoubleVector.getCPtr(y), y) : QuantLibJNI.OdeFctDelegate_valueSwigExplicitOdeFctDelegate(swigCPtr, this, x, DoubleVector.getCPtr(y), y), true);
  }

  public OdeFctDelegate() {
    this(QuantLibJNI.new_OdeFctDelegate(), true);
    QuantLibJNI.OdeFctDelegate_director_connect(this, swigCPtr, true, true);
  }

}
