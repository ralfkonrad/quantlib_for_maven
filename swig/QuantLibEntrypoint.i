#if defined(SWIGJAVA)
// QuantLibJNI class does not have to public
%pragma(java) jniclassclassmodifiers="class"

// Automatically load the shared library for JAVA binding
%pragma(java) jniclasscode=%{
  /// Load the JNI library
  static {
    QuantLibJNIHelpers.loadLibrary();
  }
%}

%include stl.i

%typemap(javainterfaces) SWIGTYPE "QuantLibJNIHelpers.AutoCloseable";
%extend std::vector {
    %typemap(javainterfaces) std::vector "QuantLibJNIHelpers.AutoCloseable, java.util.RandomAccess";
};
%extend std::vector<bool> {
    %typemap(javainterfaces) std::vector "QuantLibJNIHelpers.AutoCloseable, java.util.RandomAccess"
}

// close() method naming conflict with AutoCloseable.close()
%rename(closePrice) IntervalPrice::close();
#endif

%include quantlib.i
