package org.quantlib;

import cz.adamh.utils.NativeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;

class QuantLibJNIHelpers {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuantLibJNIHelpers.class);
    private final static String OS_NAME = System.getProperty("os.name");
    private final static String OS_ARCH = System.getProperty("os.arch");

    public interface AutoCloseable extends java.lang.AutoCloseable {
        void delete();

        @Override
        default void close() {
            this.delete();
        }
    }

    static void loadLibrary() {
        try {
            LOGGER.debug("Trying to load QuantLib native library for {} (arch: {})",
                    OS_NAME, OS_ARCH);
            if (LOGGER.isTraceEnabled()) {
                traceLogAllSystemProperties();
            }

            var libraryPath = getLibraryPath();
            NativeUtils.loadLibraryFromJar(libraryPath);

            LOGGER.debug("Native library loaded");
        } catch (IOException e) {
            throw new UnsupportedOperatingSystemException(e);
        }
    }

    public static class UnsupportedOperatingSystemException extends RuntimeException {
        private final static String OS = OS_NAME + " (arch: " + OS_ARCH + ")";
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

    private static final OperatingSystem OS_SYSTEM = getOS();
    private static final String ARCH = System.getProperty("os.arch").toLowerCase();

    private QuantLibJNIHelpers() {
    }

    private static OperatingSystem getOS() {
        var os = OS_NAME.toLowerCase();
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
        switch (OS_SYSTEM) {
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
        var path = String.join("/", rootPath, OS_SYSTEM.name().toLowerCase());
        switch (OS_SYSTEM) {
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

    private static void traceLogAllSystemProperties() {
        LOGGER.trace("System properties:");
        var properties = System.getProperties();
        properties.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getKey().toString()))
                .forEach(entry -> LOGGER.trace("\t{}: {}", entry.getKey(), entry.getValue()));
    }
}
