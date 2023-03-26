/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class InvCumulativeMersenneTwisterGaussianRng implements AutoCloseable {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected InvCumulativeMersenneTwisterGaussianRng(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(InvCumulativeMersenneTwisterGaussianRng obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(InvCumulativeMersenneTwisterGaussianRng obj) {
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
        QuantLibJNI.delete_InvCumulativeMersenneTwisterGaussianRng(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  @Override
  public void close() {
   this.delete();
  }

  public InvCumulativeMersenneTwisterGaussianRng(MersenneTwisterUniformRng rng) {
    this(QuantLibJNI.new_InvCumulativeMersenneTwisterGaussianRng(MersenneTwisterUniformRng.getCPtr(rng), rng), true);
  }

  public SampleNumber next() {
    return new SampleNumber(QuantLibJNI.InvCumulativeMersenneTwisterGaussianRng_next(swigCPtr, this), true);
  }

}
