#if defined(SWIGJAVA)
// Automatically load the shared library for JAVA binding
%pragma(java) jniclasscode=%{
  /// Load the JNI library
  static {
    QuantLibJNIHelper.loadLibrary();
  }
%}
#endif

%include quantlib.i

