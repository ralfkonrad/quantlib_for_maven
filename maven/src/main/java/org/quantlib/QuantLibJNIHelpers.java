package org.quantlib;

import cz.adamh.utils.NativeUtils;

import java.io.IOException;

class QuantLibJNIHelpers {
    public interface AutoCloseable extends java.lang.AutoCloseable {
        void delete();

        @Override
        default void close() {
            this.delete();
        }
    }

    static void loadLibrary() {
        try {
            var libraryPath = getLibraryPath();
            NativeUtils.loadLibraryFromJar(libraryPath);
        } catch (IOException e) {
            throw new UnsupportedOperatingSystemException(e);
        }
    }

    public static class UnsupportedOperatingSystemException extends RuntimeException {
        private final static String OS = System.getProperty("os.name") + " (arch: " + System.getProperty("os.arch") + ")";
        private final static String MESSAGE = "The operating system '" + OS + "' is not supported.";

        private UnsupportedOperatingSystemException() {
            super(MESSAGE);
        }

        private UnsupportedOperatingSystemException(Exception exception) {
            super(MESSAGE, exception);
        }

        @SuppressWarnings("unused")
        public String getUnsupportedOperatingSystem() {
            return OS;
        }
    }

    private static final OperatingSystem OS = getOS();
    private static final String ARCH = System.getProperty("os.arch").toLowerCase();

    private QuantLibJNIHelpers() {
    }

    private static OperatingSystem getOS() {
        var os = System.getProperty("os.name").toLowerCase();
        if (os.startsWith("linux")) {
            return OperatingSystem.Linux;
        }
        if (os.startsWith("mac")) {
            return OperatingSystem.MacOs;
        }
        if (os.startsWith("win")) {
            return OperatingSystem.Windows;
        }
        throw new UnsupportedOperatingSystemException();
    }

    private static String getLibraryName() {
        switch (OS) {
            case Linux:
                return "libQuantLibJNI.so";

            case MacOs:
                return "libQuantLibJNI.jnilib";

            case Windows:
                return "QuantLibJNI.dll";

            default:
                throw new UnsupportedOperatingSystemException();
        }
    }

    private static String getLibraryPath() {
        var rootPath = "/native";
        var path = String.join("/", rootPath, OS.name().toLowerCase());
        switch (OS) {
            case Linux:
            case MacOs:
                return String.join("/", path, getLibraryName());

            case Windows:
                return String.join("/", path, ARCH, getLibraryName());

            default:
                throw new UnsupportedOperatingSystemException();
        }
    }

    private enum OperatingSystem {
        Linux,
        MacOs,
        Windows
    }
}
