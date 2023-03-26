/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class FdmLinearOpLayout implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwn;

  protected FdmLinearOpLayout(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(FdmLinearOpLayout obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void swigSetCMemOwn(boolean own) {
    swigCMemOwn = own;
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        QuantLibJNI.delete_FdmLinearOpLayout(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  @Override
  public void close() {
   this.delete();
  }

  public FdmLinearOpLayout(UnsignedIntVector dim) {
    this(QuantLibJNI.new_FdmLinearOpLayout(UnsignedIntVector.getCPtr(dim), dim), true);
  }

  public UnsignedIntVector spacing() {
    return new UnsignedIntVector(QuantLibJNI.FdmLinearOpLayout_spacing(swigCPtr, this), true);
  }

  public UnsignedIntVector dim() {
    return new UnsignedIntVector(QuantLibJNI.FdmLinearOpLayout_dim(swigCPtr, this), true);
  }

  public long index(UnsignedIntVector coordinates) {
    return QuantLibJNI.FdmLinearOpLayout_index(swigCPtr, this, UnsignedIntVector.getCPtr(coordinates), coordinates);
  }

  public FdmLinearOpIterator begin() {
    return new FdmLinearOpIterator(QuantLibJNI.FdmLinearOpLayout_begin(swigCPtr, this), true);
  }

  public FdmLinearOpIterator end() {
    return new FdmLinearOpIterator(QuantLibJNI.FdmLinearOpLayout_end(swigCPtr, this), true);
  }

  public long size() {
    return QuantLibJNI.FdmLinearOpLayout_size(swigCPtr, this);
  }

  public long neighbourhood(FdmLinearOpIterator iterator, long i, int offset) {
    return QuantLibJNI.FdmLinearOpLayout_neighbourhood__SWIG_0(swigCPtr, this, FdmLinearOpIterator.getCPtr(iterator), iterator, i, offset);
  }

  public long neighbourhood(FdmLinearOpIterator iterator, long i1, int offset1, long i2, int offset2) {
    return QuantLibJNI.FdmLinearOpLayout_neighbourhood__SWIG_1(swigCPtr, this, FdmLinearOpIterator.getCPtr(iterator), iterator, i1, offset1, i2, offset2);
  }

  public FdmLinearOpIterator iter_neighbourhood(FdmLinearOpIterator iterator, long i, int offset) {
    return new FdmLinearOpIterator(QuantLibJNI.FdmLinearOpLayout_iter_neighbourhood(swigCPtr, this, FdmLinearOpIterator.getCPtr(iterator), iterator, i, offset), true);
  }

}
