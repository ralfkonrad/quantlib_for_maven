#if defined(SWIGJAVA)
// QuantLibJNI class does not have to public
%pragma(java) jniclassclassmodifiers="class"

// Automatically load the shared library for JAVA binding
%pragma(java) jniclasscode=%{
  /// Load the JNI library
  static {
    org.quantlib.helpers.QuantLibJNIHelpers.loadLibrary();
  }
%}

%include stl.i

%typemap(javainterfaces) SWIGTYPE "org.quantlib.helpers.QuantLibJNIHelpers.AutoCloseable";
%extend std::vector {
    %typemap(javainterfaces) std::vector "org.quantlib.helpers.QuantLibJNIHelpers.AutoCloseable, java.util.RandomAccess";
};
%extend std::vector<bool> {
    %typemap(javainterfaces) std::vector "org.quantlib.helpers.QuantLibJNIHelpers.AutoCloseable, java.util.RandomAccess"
}

// close() method naming conflict with AutoCloseable.close()
%rename(closePrice) IntervalPrice::close();
#endif

%include quantlib.i
