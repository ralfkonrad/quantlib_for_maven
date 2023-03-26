/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

import java.lang.AutoCloseable;

public class FdBlackScholesVanillaEngine extends PricingEngine implements AutoCloseable {
  private transient long swigCPtr;
  private transient boolean swigCMemOwnDerived;

  protected FdBlackScholesVanillaEngine(long cPtr, boolean cMemoryOwn) {
    super(QuantLibJNI.FdBlackScholesVanillaEngine_SWIGSmartPtrUpcast(cPtr), true);
    swigCMemOwnDerived = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(FdBlackScholesVanillaEngine obj) {
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
        QuantLibJNI.delete_FdBlackScholesVanillaEngine(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  @Override
  public void close() {
   this.delete();
  }

  public FdBlackScholesVanillaEngine(GeneralizedBlackScholesProcess process, long tGrid, long xGrid, long dampingSteps, FdmSchemeDesc schemeDesc, boolean localVol, double illegalLocalVolOverwrite, FdBlackScholesVanillaEngine.CashDividendModel cashDividendModel) {
    this(QuantLibJNI.new_FdBlackScholesVanillaEngine__SWIG_0(GeneralizedBlackScholesProcess.getCPtr(process), process, tGrid, xGrid, dampingSteps, FdmSchemeDesc.getCPtr(schemeDesc), schemeDesc, localVol, illegalLocalVolOverwrite, cashDividendModel.swigValue()), true);
  }

  public FdBlackScholesVanillaEngine(GeneralizedBlackScholesProcess process, long tGrid, long xGrid, long dampingSteps, FdmSchemeDesc schemeDesc, boolean localVol, double illegalLocalVolOverwrite) {
    this(QuantLibJNI.new_FdBlackScholesVanillaEngine__SWIG_1(GeneralizedBlackScholesProcess.getCPtr(process), process, tGrid, xGrid, dampingSteps, FdmSchemeDesc.getCPtr(schemeDesc), schemeDesc, localVol, illegalLocalVolOverwrite), true);
  }

  public FdBlackScholesVanillaEngine(GeneralizedBlackScholesProcess process, long tGrid, long xGrid, long dampingSteps, FdmSchemeDesc schemeDesc, boolean localVol) {
    this(QuantLibJNI.new_FdBlackScholesVanillaEngine__SWIG_2(GeneralizedBlackScholesProcess.getCPtr(process), process, tGrid, xGrid, dampingSteps, FdmSchemeDesc.getCPtr(schemeDesc), schemeDesc, localVol), true);
  }

  public FdBlackScholesVanillaEngine(GeneralizedBlackScholesProcess process, long tGrid, long xGrid, long dampingSteps, FdmSchemeDesc schemeDesc) {
    this(QuantLibJNI.new_FdBlackScholesVanillaEngine__SWIG_3(GeneralizedBlackScholesProcess.getCPtr(process), process, tGrid, xGrid, dampingSteps, FdmSchemeDesc.getCPtr(schemeDesc), schemeDesc), true);
  }

  public FdBlackScholesVanillaEngine(GeneralizedBlackScholesProcess process, long tGrid, long xGrid, long dampingSteps) {
    this(QuantLibJNI.new_FdBlackScholesVanillaEngine__SWIG_4(GeneralizedBlackScholesProcess.getCPtr(process), process, tGrid, xGrid, dampingSteps), true);
  }

  public FdBlackScholesVanillaEngine(GeneralizedBlackScholesProcess process, long tGrid, long xGrid) {
    this(QuantLibJNI.new_FdBlackScholesVanillaEngine__SWIG_5(GeneralizedBlackScholesProcess.getCPtr(process), process, tGrid, xGrid), true);
  }

  public FdBlackScholesVanillaEngine(GeneralizedBlackScholesProcess process, long tGrid) {
    this(QuantLibJNI.new_FdBlackScholesVanillaEngine__SWIG_6(GeneralizedBlackScholesProcess.getCPtr(process), process, tGrid), true);
  }

  public FdBlackScholesVanillaEngine(GeneralizedBlackScholesProcess process) {
    this(QuantLibJNI.new_FdBlackScholesVanillaEngine__SWIG_7(GeneralizedBlackScholesProcess.getCPtr(process), process), true);
  }

  public FdBlackScholesVanillaEngine(GeneralizedBlackScholesProcess arg0, FdmQuantoHelper quantoHelper, long tGrid, long xGrid, long dampingSteps, FdmSchemeDesc schemeDesc, boolean localVol, double illegalLocalVolOverwrite, FdBlackScholesVanillaEngine.CashDividendModel cashDividendModel) {
    this(QuantLibJNI.new_FdBlackScholesVanillaEngine__SWIG_8(GeneralizedBlackScholesProcess.getCPtr(arg0), arg0, FdmQuantoHelper.getCPtr(quantoHelper), quantoHelper, tGrid, xGrid, dampingSteps, FdmSchemeDesc.getCPtr(schemeDesc), schemeDesc, localVol, illegalLocalVolOverwrite, cashDividendModel.swigValue()), true);
  }

  public FdBlackScholesVanillaEngine(GeneralizedBlackScholesProcess arg0, FdmQuantoHelper quantoHelper, long tGrid, long xGrid, long dampingSteps, FdmSchemeDesc schemeDesc, boolean localVol, double illegalLocalVolOverwrite) {
    this(QuantLibJNI.new_FdBlackScholesVanillaEngine__SWIG_9(GeneralizedBlackScholesProcess.getCPtr(arg0), arg0, FdmQuantoHelper.getCPtr(quantoHelper), quantoHelper, tGrid, xGrid, dampingSteps, FdmSchemeDesc.getCPtr(schemeDesc), schemeDesc, localVol, illegalLocalVolOverwrite), true);
  }

  public FdBlackScholesVanillaEngine(GeneralizedBlackScholesProcess arg0, FdmQuantoHelper quantoHelper, long tGrid, long xGrid, long dampingSteps, FdmSchemeDesc schemeDesc, boolean localVol) {
    this(QuantLibJNI.new_FdBlackScholesVanillaEngine__SWIG_10(GeneralizedBlackScholesProcess.getCPtr(arg0), arg0, FdmQuantoHelper.getCPtr(quantoHelper), quantoHelper, tGrid, xGrid, dampingSteps, FdmSchemeDesc.getCPtr(schemeDesc), schemeDesc, localVol), true);
  }

  public FdBlackScholesVanillaEngine(GeneralizedBlackScholesProcess arg0, FdmQuantoHelper quantoHelper, long tGrid, long xGrid, long dampingSteps, FdmSchemeDesc schemeDesc) {
    this(QuantLibJNI.new_FdBlackScholesVanillaEngine__SWIG_11(GeneralizedBlackScholesProcess.getCPtr(arg0), arg0, FdmQuantoHelper.getCPtr(quantoHelper), quantoHelper, tGrid, xGrid, dampingSteps, FdmSchemeDesc.getCPtr(schemeDesc), schemeDesc), true);
  }

  public FdBlackScholesVanillaEngine(GeneralizedBlackScholesProcess arg0, FdmQuantoHelper quantoHelper, long tGrid, long xGrid, long dampingSteps) {
    this(QuantLibJNI.new_FdBlackScholesVanillaEngine__SWIG_12(GeneralizedBlackScholesProcess.getCPtr(arg0), arg0, FdmQuantoHelper.getCPtr(quantoHelper), quantoHelper, tGrid, xGrid, dampingSteps), true);
  }

  public FdBlackScholesVanillaEngine(GeneralizedBlackScholesProcess arg0, FdmQuantoHelper quantoHelper, long tGrid, long xGrid) {
    this(QuantLibJNI.new_FdBlackScholesVanillaEngine__SWIG_13(GeneralizedBlackScholesProcess.getCPtr(arg0), arg0, FdmQuantoHelper.getCPtr(quantoHelper), quantoHelper, tGrid, xGrid), true);
  }

  public FdBlackScholesVanillaEngine(GeneralizedBlackScholesProcess arg0, FdmQuantoHelper quantoHelper, long tGrid) {
    this(QuantLibJNI.new_FdBlackScholesVanillaEngine__SWIG_14(GeneralizedBlackScholesProcess.getCPtr(arg0), arg0, FdmQuantoHelper.getCPtr(quantoHelper), quantoHelper, tGrid), true);
  }

  public FdBlackScholesVanillaEngine(GeneralizedBlackScholesProcess arg0, FdmQuantoHelper quantoHelper) {
    this(QuantLibJNI.new_FdBlackScholesVanillaEngine__SWIG_15(GeneralizedBlackScholesProcess.getCPtr(arg0), arg0, FdmQuantoHelper.getCPtr(quantoHelper), quantoHelper), true);
  }

  public final static class CashDividendModel {
    public final static FdBlackScholesVanillaEngine.CashDividendModel Spot = new FdBlackScholesVanillaEngine.CashDividendModel("Spot");
    public final static FdBlackScholesVanillaEngine.CashDividendModel Escrowed = new FdBlackScholesVanillaEngine.CashDividendModel("Escrowed");

    public final int swigValue() {
      return swigValue;
    }

    public String toString() {
      return swigName;
    }

    public static CashDividendModel swigToEnum(int swigValue) {
      if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
        return swigValues[swigValue];
      for (int i = 0; i < swigValues.length; i++)
        if (swigValues[i].swigValue == swigValue)
          return swigValues[i];
      throw new IllegalArgumentException("No enum " + CashDividendModel.class + " with value " + swigValue);
    }

    private CashDividendModel(String swigName) {
      this.swigName = swigName;
      this.swigValue = swigNext++;
    }

    private CashDividendModel(String swigName, int swigValue) {
      this.swigName = swigName;
      this.swigValue = swigValue;
      swigNext = swigValue+1;
    }

    private CashDividendModel(String swigName, CashDividendModel swigEnum) {
      this.swigName = swigName;
      this.swigValue = swigEnum.swigValue;
      swigNext = this.swigValue+1;
    }

    private static CashDividendModel[] swigValues = { Spot, Escrowed };
    private static int swigNext = 0;
    private final int swigValue;
    private final String swigName;
  }

}
