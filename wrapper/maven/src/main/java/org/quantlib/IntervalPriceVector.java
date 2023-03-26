/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class IntervalPriceVector extends java.util.AbstractList<IntervalPrice> implements AutoCloseable, java.util.RandomAccess {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected IntervalPriceVector(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(IntervalPriceVector obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(IntervalPriceVector obj) {
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
        QuantLibJNI.delete_IntervalPriceVector(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  @Override
  public void close() {
   this.delete();
  }

  public IntervalPriceVector(IntervalPrice[] initialElements) {
    this();
    reserve(initialElements.length);

    for (IntervalPrice element : initialElements) {
      add(element);
    }
  }

  public IntervalPriceVector(Iterable<IntervalPrice> initialElements) {
    this();
    for (IntervalPrice element : initialElements) {
      add(element);
    }
  }

  public IntervalPrice get(int index) {
    return doGet(index);
  }

  public IntervalPrice set(int index, IntervalPrice e) {
    return doSet(index, e);
  }

  public boolean add(IntervalPrice e) {
    modCount++;
    doAdd(e);
    return true;
  }

  public void add(int index, IntervalPrice e) {
    modCount++;
    doAdd(index, e);
  }

  public IntervalPrice remove(int index) {
    modCount++;
    return doRemove(index);
  }

  protected void removeRange(int fromIndex, int toIndex) {
    modCount++;
    doRemoveRange(fromIndex, toIndex);
  }

  public int size() {
    return doSize();
  }

  public IntervalPriceVector() {
    this(QuantLibJNI.new_IntervalPriceVector__SWIG_0(), true);
  }

  public IntervalPriceVector(IntervalPriceVector other) {
    this(QuantLibJNI.new_IntervalPriceVector__SWIG_1(IntervalPriceVector.getCPtr(other), other), true);
  }

  public long capacity() {
    return QuantLibJNI.IntervalPriceVector_capacity(swigCPtr, this);
  }

  public void reserve(long n) {
    QuantLibJNI.IntervalPriceVector_reserve(swigCPtr, this, n);
  }

  public boolean isEmpty() {
    return QuantLibJNI.IntervalPriceVector_isEmpty(swigCPtr, this);
  }

  public void clear() {
    QuantLibJNI.IntervalPriceVector_clear(swigCPtr, this);
  }

  public IntervalPriceVector(int count, IntervalPrice value) {
    this(QuantLibJNI.new_IntervalPriceVector__SWIG_2(count, IntervalPrice.getCPtr(value), value), true);
  }

  private int doSize() {
    return QuantLibJNI.IntervalPriceVector_doSize(swigCPtr, this);
  }

  private void doAdd(IntervalPrice x) {
    QuantLibJNI.IntervalPriceVector_doAdd__SWIG_0(swigCPtr, this, IntervalPrice.getCPtr(x), x);
  }

  private void doAdd(int index, IntervalPrice x) {
    QuantLibJNI.IntervalPriceVector_doAdd__SWIG_1(swigCPtr, this, index, IntervalPrice.getCPtr(x), x);
  }

  private IntervalPrice doRemove(int index) {
    return new IntervalPrice(QuantLibJNI.IntervalPriceVector_doRemove(swigCPtr, this, index), true);
  }

  private IntervalPrice doGet(int index) {
    return new IntervalPrice(QuantLibJNI.IntervalPriceVector_doGet(swigCPtr, this, index), false);
  }

  private IntervalPrice doSet(int index, IntervalPrice val) {
    return new IntervalPrice(QuantLibJNI.IntervalPriceVector_doSet(swigCPtr, this, index, IntervalPrice.getCPtr(val), val), true);
  }

  private void doRemoveRange(int fromIndex, int toIndex) {
    QuantLibJNI.IntervalPriceVector_doRemoveRange(swigCPtr, this, fromIndex, toIndex);
  }

}
