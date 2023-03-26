/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class UnitedKingdom extends Calendar implements AutoCloseable {
  private transient long swigCPtr;

  protected UnitedKingdom(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.UnitedKingdom_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(UnitedKingdom obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(UnitedKingdom obj) {
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
        QuantLibJNI.delete_UnitedKingdom(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public UnitedKingdom(UnitedKingdom.Market m) {
    this(QuantLibJNI.new_UnitedKingdom__SWIG_0(m.swigValue()), true);
  }

  public UnitedKingdom() {
    this(QuantLibJNI.new_UnitedKingdom__SWIG_1(), true);
  }

  public final static class Market {
    public final static UnitedKingdom.Market Settlement = new UnitedKingdom.Market("Settlement");
    public final static UnitedKingdom.Market Exchange = new UnitedKingdom.Market("Exchange");
    public final static UnitedKingdom.Market Metals = new UnitedKingdom.Market("Metals");

    public final int swigValue() {
      return swigValue;
    }

    public String toString() {
      return swigName;
    }

    public static Market swigToEnum(int swigValue) {
      if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
        return swigValues[swigValue];
      for (int i = 0; i < swigValues.length; i++)
        if (swigValues[i].swigValue == swigValue)
          return swigValues[i];
      throw new IllegalArgumentException("No enum " + Market.class + " with value " + swigValue);
    }

    private Market(String swigName) {
      this.swigName = swigName;
      this.swigValue = swigNext++;
    }

    private Market(String swigName, int swigValue) {
      this.swigName = swigName;
      this.swigValue = swigValue;
      swigNext = swigValue+1;
    }

    private Market(String swigName, Market swigEnum) {
      this.swigName = swigName;
      this.swigValue = swigEnum.swigValue;
      swigNext = this.swigValue+1;
    }

    private static Market[] swigValues = { Settlement, Exchange, Metals };
    private static int swigNext = 0;
    private final int swigValue;
    private final String swigName;
  }

}
