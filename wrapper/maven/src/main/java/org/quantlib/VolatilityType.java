/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.quantlib;

public final class VolatilityType {
  public final static VolatilityType ShiftedLognormal = new VolatilityType("ShiftedLognormal");
  public final static VolatilityType Normal = new VolatilityType("Normal");

  public final int swigValue() {
    return swigValue;
  }

  public String toString() {
    return swigName;
  }

  public static VolatilityType swigToEnum(int swigValue) {
    if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
      return swigValues[swigValue];
    for (int i = 0; i < swigValues.length; i++)
      if (swigValues[i].swigValue == swigValue)
        return swigValues[i];
    throw new IllegalArgumentException("No enum " + VolatilityType.class + " with value " + swigValue);
  }

  private VolatilityType(String swigName) {
    this.swigName = swigName;
    this.swigValue = swigNext++;
  }

  private VolatilityType(String swigName, int swigValue) {
    this.swigName = swigName;
    this.swigValue = swigValue;
    swigNext = swigValue+1;
  }

  private VolatilityType(String swigName, VolatilityType swigEnum) {
    this.swigName = swigName;
    this.swigValue = swigEnum.swigValue;
    swigNext = this.swigValue+1;
  }

  private static VolatilityType[] swigValues = { ShiftedLognormal, Normal };
  private static int swigNext = 0;
  private final int swigValue;
  private final String swigName;
}

