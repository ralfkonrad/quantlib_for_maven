package org.quantlib.helpers;

import cz.adamh.utils.NativeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;

public class QuantLibJNIHelpers {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuantLibJNIHelpers.class);
    private final static String OS_NAME = System.getProperty("os.name");
    private final static String OS_ARCH = System.getProperty("os.arch");
    private final static String OS_VERSION = System.getProperty("os.version");
    private final static String OS = OS_NAME + " (arch: " + OS_ARCH + ", version: " + OS_VERSION + ")";
    private static boolean libraryIsLoaded = false;

    public interface AutoCloseable extends java.lang.AutoCloseable {
        void delete();

        @Override
        default void close() {
            this.delete();
        }
    }

    public static void loadLibrary() {
        try {
            if (libraryIsLoaded) {
                LOGGER.warn("QuantLib native library for {} was already loaded, " +
                        "skipping reloading", OS);
                return;
            }

            LOGGER.debug("Trying to load QuantLib native library for {}", OS);
            traceLogAllSystemProperties();

            var libraryPath = getLibraryPath();
            NativeUtils.loadLibraryFromJar(libraryPath);
            libraryIsLoaded = true;

            LOGGER.debug("Native library loaded");
        } catch (IOException e) {
            throw new UnsupportedOperatingSystemException(e);
        }
    }

    public static class UnsupportedOperatingSystemException extends RuntimeException {
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
                return String.join("/", path, normalizeArchitecture(ARCH), getLibraryName());

            case MacOs:
                return String.join("/", path, getLibraryName());

            case Windows:
                return String.join("/", path, normalizeArchitecture(ARCH), getLibraryName());

            default:
                throw new UnsupportedOperatingSystemException();
        }
    }

    private static String normalizeArchitecture(String arch) {
        if (arch.equals("x86_64") || arch.equals("amd64")) {
            return "amd64";
        }
        if (arch.equals("aarch64") || arch.equals("arm64")) {
            return "arm64";
        }
        throw new UnsupportedOperatingSystemException();
    }

    private enum OperatingSystem {
        Linux,
        MacOs,
        Windows
    }

    private static void traceLogAllSystemProperties() {
        if (!LOGGER.isTraceEnabled()) {
            return;
        }

        LOGGER.trace("System properties:");
        var properties = System.getProperties();
        properties.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getKey().toString()))
                .forEach(entry -> LOGGER.trace("\t{}: {}", entry.getKey(), entry.getValue()));
    }
}
