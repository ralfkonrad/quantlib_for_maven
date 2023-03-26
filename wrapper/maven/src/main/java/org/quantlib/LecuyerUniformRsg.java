/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class LecuyerUniformRsg implements AutoCloseable {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected LecuyerUniformRsg(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(LecuyerUniformRsg obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(LecuyerUniformRsg obj) {
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
        QuantLibJNI.delete_LecuyerUniformRsg(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  @Override
  public void close() {
   this.delete();
  }

  public LecuyerUniformRsg(long dimensionality, LecuyerUniformRng rng) {
    this(QuantLibJNI.new_LecuyerUniformRsg__SWIG_0(dimensionality, LecuyerUniformRng.getCPtr(rng), rng), true);
  }

  public LecuyerUniformRsg(long dimensionality, long seed) {
    this(QuantLibJNI.new_LecuyerUniformRsg__SWIG_1(dimensionality, seed), true);
  }

  public LecuyerUniformRsg(long dimensionality) {
    this(QuantLibJNI.new_LecuyerUniformRsg__SWIG_2(dimensionality), true);
  }

  public SampleRealVector nextSequence() {
    return new SampleRealVector(QuantLibJNI.LecuyerUniformRsg_nextSequence(swigCPtr, this), false);
  }

  public long dimension() {
    return QuantLibJNI.LecuyerUniformRsg_dimension(swigCPtr, this);
  }

}
