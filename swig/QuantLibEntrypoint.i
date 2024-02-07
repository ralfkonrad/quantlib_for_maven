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

#define QL_JAVA_INTERFACES "org.quantlib.helpers.QuantLibJNIHelpers.AutoCloseable, "
%typemap(javainterfaces) SWIGTYPE "org.quantlib.helpers.QuantLibJNIHelpers.AutoCloseable";
%extend std::vector {
    %typemap(javainterfaces) std::vector QL_JAVA_INTERFACES "java.util.RandomAccess";
};
%extend std::vector<bool> {
    %typemap(javainterfaces) std::vector QL_JAVA_INTERFACES "java.util.RandomAccess";
}

// close() method naming conflict with AutoCloseable.close()
%rename(closePrice) IntervalPrice::close();
#endif

%include quantlib.i
