#if defined(SWIGJAVA)
// Automatically load the shared library for JAVA binding
%pragma(java) jniclasscode=%{
  /// Load the JNI library
  static {
    QuantLibJNIHelper.loadLibrary();
  }
%}

%typemap(javainterfaces) SWIGTYPE "QuantLibJNIHelper.AutoCloseable";
%extend std::vector {
    %typemap(javainterfaces) std::vector "QuantLibJNIHelper.AutoCloseable, java.util.RandomAccess";
};
%extend std::vector<bool> {
    %typemap(javainterfaces) std::vector "QuantLibJNIHelper.AutoCloseable, java.util.RandomAccess"
}

// close() method naming conflict with AutoCloseable.close()
%rename(closePrice) IntervalPrice::close();

%typemap(javacode) Date %{
  // convenience method to use java.time API
  public static Date of(java.time.LocalDate localDate) {
    return new Date(localDate.getDayOfMonth(), Month.swigToEnum(localDate.getMonthValue()), localDate.getYear());
  }

  // convenience method to use java.time API
  public java.time.LocalDate toLocalDate() {
    return java.time.LocalDate.of(this.year(), this.month().swigValue(), this.dayOfMonth());
  }
%}
#endif

%include quantlib.i

