#if defined(SWIGJAVA)
// Automatically load the shared library for JAVA binding
%pragma(java) jniclasscode=%{
  /// Load the JNI library
  static {
    org.quantlib.helper.QuantLibJNIHelper.loadLibrary();
  }
%}
#endif

%include quantlib.i

