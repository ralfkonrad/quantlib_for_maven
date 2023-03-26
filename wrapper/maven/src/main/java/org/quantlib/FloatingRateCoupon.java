/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class FloatingRateCoupon extends Coupon implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected FloatingRateCoupon(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.FloatingRateCoupon_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(FloatingRateCoupon obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void swigSetCMemOwn(boolean own) {
    swigCMemOwnDerived = own;
    super.swigSetCMemOwn(own);
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwnDerived) {
        swigCMemOwnDerived = false;
        QuantLibJNI.delete_FloatingRateCoupon(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public Date fixingDate() {
    return new Date(QuantLibJNI.FloatingRateCoupon_fixingDate(swigCPtr, this), true);
  }

  public int fixingDays() {
    return QuantLibJNI.FloatingRateCoupon_fixingDays(swigCPtr, this);
  }

  public boolean isInArrears() {
    return QuantLibJNI.FloatingRateCoupon_isInArrears(swigCPtr, this);
  }

  public double gearing() {
    return QuantLibJNI.FloatingRateCoupon_gearing(swigCPtr, this);
  }

  public double spread() {
    return QuantLibJNI.FloatingRateCoupon_spread(swigCPtr, this);
  }

  public double indexFixing() {
    return QuantLibJNI.FloatingRateCoupon_indexFixing(swigCPtr, this);
  }

  public double adjustedFixing() {
    return QuantLibJNI.FloatingRateCoupon_adjustedFixing(swigCPtr, this);
  }

  public double convexityAdjustment() {
    return QuantLibJNI.FloatingRateCoupon_convexityAdjustment(swigCPtr, this);
  }

  public double price(YieldTermStructureHandle discountCurve) {
    return QuantLibJNI.FloatingRateCoupon_price(swigCPtr, this, YieldTermStructureHandle.getCPtr(discountCurve), discountCurve);
  }

  public InterestRateIndex index() {
    long cPtr = QuantLibJNI.FloatingRateCoupon_index(swigCPtr, this);
    return (cPtr == 0) ? null : new InterestRateIndex(cPtr, true);
  }

  public void setPricer(FloatingRateCouponPricer p) {
    QuantLibJNI.FloatingRateCoupon_setPricer(swigCPtr, this, FloatingRateCouponPricer.getCPtr(p), p);
  }

}
