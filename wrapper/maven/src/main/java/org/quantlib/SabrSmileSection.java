/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class SabrSmileSection extends SmileSection implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected SabrSmileSection(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.SabrSmileSection_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(SabrSmileSection obj) {
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
        QuantLibJNI.delete_SabrSmileSection(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public SabrSmileSection(Date d, double forward, DoubleVector sabrParameters, Date referenceDate, DayCounter dc, double shift, VolatilityType volatilityType) {
    this(QuantLibJNI.new_SabrSmileSection__SWIG_0(Date.getCPtr(d), d, forward, DoubleVector.getCPtr(sabrParameters), sabrParameters, Date.getCPtr(referenceDate), referenceDate, DayCounter.getCPtr(dc), dc, shift, volatilityType.swigValue()), true);
  }

  public SabrSmileSection(Date d, double forward, DoubleVector sabrParameters, Date referenceDate, DayCounter dc, double shift) {
    this(QuantLibJNI.new_SabrSmileSection__SWIG_1(Date.getCPtr(d), d, forward, DoubleVector.getCPtr(sabrParameters), sabrParameters, Date.getCPtr(referenceDate), referenceDate, DayCounter.getCPtr(dc), dc, shift), true);
  }

  public SabrSmileSection(Date d, double forward, DoubleVector sabrParameters, Date referenceDate, DayCounter dc) {
    this(QuantLibJNI.new_SabrSmileSection__SWIG_2(Date.getCPtr(d), d, forward, DoubleVector.getCPtr(sabrParameters), sabrParameters, Date.getCPtr(referenceDate), referenceDate, DayCounter.getCPtr(dc), dc), true);
  }

  public SabrSmileSection(Date d, double forward, DoubleVector sabrParameters, Date referenceDate) {
    this(QuantLibJNI.new_SabrSmileSection__SWIG_3(Date.getCPtr(d), d, forward, DoubleVector.getCPtr(sabrParameters), sabrParameters, Date.getCPtr(referenceDate), referenceDate), true);
  }

  public SabrSmileSection(Date d, double forward, DoubleVector sabrParameters) {
    this(QuantLibJNI.new_SabrSmileSection__SWIG_4(Date.getCPtr(d), d, forward, DoubleVector.getCPtr(sabrParameters), sabrParameters), true);
  }

  public SabrSmileSection(Date d, double forward, DoubleVector sabrParameters, DayCounter dc, double shift, VolatilityType volatilityType) {
    this(QuantLibJNI.new_SabrSmileSection__SWIG_5(Date.getCPtr(d), d, forward, DoubleVector.getCPtr(sabrParameters), sabrParameters, DayCounter.getCPtr(dc), dc, shift, volatilityType.swigValue()), true);
  }

  public SabrSmileSection(Date d, double forward, DoubleVector sabrParameters, DayCounter dc, double shift) {
    this(QuantLibJNI.new_SabrSmileSection__SWIG_6(Date.getCPtr(d), d, forward, DoubleVector.getCPtr(sabrParameters), sabrParameters, DayCounter.getCPtr(dc), dc, shift), true);
  }

  public SabrSmileSection(Date d, double forward, DoubleVector sabrParameters, DayCounter dc) {
    this(QuantLibJNI.new_SabrSmileSection__SWIG_7(Date.getCPtr(d), d, forward, DoubleVector.getCPtr(sabrParameters), sabrParameters, DayCounter.getCPtr(dc), dc), true);
  }

  public SabrSmileSection(double timeToExpiry, double forward, DoubleVector sabrParameters, double shift, VolatilityType volatilityType) {
    this(QuantLibJNI.new_SabrSmileSection__SWIG_8(timeToExpiry, forward, DoubleVector.getCPtr(sabrParameters), sabrParameters, shift, volatilityType.swigValue()), true);
  }

  public SabrSmileSection(double timeToExpiry, double forward, DoubleVector sabrParameters, double shift) {
    this(QuantLibJNI.new_SabrSmileSection__SWIG_9(timeToExpiry, forward, DoubleVector.getCPtr(sabrParameters), sabrParameters, shift), true);
  }

  public SabrSmileSection(double timeToExpiry, double forward, DoubleVector sabrParameters) {
    this(QuantLibJNI.new_SabrSmileSection__SWIG_10(timeToExpiry, forward, DoubleVector.getCPtr(sabrParameters), sabrParameters), true);
  }

  public double alpha() {
    return QuantLibJNI.SabrSmileSection_alpha(swigCPtr, this);
  }

  public double beta() {
    return QuantLibJNI.SabrSmileSection_beta(swigCPtr, this);
  }

  public double nu() {
    return QuantLibJNI.SabrSmileSection_nu(swigCPtr, this);
  }

  public double rho() {
    return QuantLibJNI.SabrSmileSection_rho(swigCPtr, this);
  }

}
