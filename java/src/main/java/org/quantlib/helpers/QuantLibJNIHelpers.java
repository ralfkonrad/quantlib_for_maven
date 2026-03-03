package org.quantlib.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;

public class QuantLibJNIHelpers {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuantLibJNIHelpers.class);
    private static final String OS_NAME = System.getProperty("os.name");
    private static final String OS_ARCH = System.getProperty("os.arch");
    private static final String OS_VERSION = System.getProperty("os.version");
    private static final String OS = OS_NAME + " (arch: " + OS_ARCH + ", version: " + OS_VERSION + ")";

    static {
        Path libraryPath = null;
        try {
            LOGGER.debug("Trying to load QuantLib native library for {}", OS);
            traceLogAllSystemProperties();

            libraryPath = getLibraryPath();
            NativeUtils.loadLibraryFromJar(libraryPath);

            LOGGER.debug("Native library '{}' loaded", libraryPath);
        } catch (IOException ioException) {
            throw new NativeLibraryLoaderException(libraryPath, ioException);
        }
    }

    public interface AutoCloseable extends java.lang.AutoCloseable {
        void delete();

        @Override
        default void close() {
            this.delete();
        }
    }

    public static void loadLibrary() {
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


    public static final class NativeLibraryLoaderException extends RuntimeException {
        private final Path nativeLibraryPath;

        public NativeLibraryLoaderException(Path nativeLibraryPath, IOException ioException) {
            super("Cannot load native library '" + nativeLibraryPath + "'", ioException);
            this.nativeLibraryPath = nativeLibraryPath;
        }

        public Path getNativeLibraryPath() {
            return nativeLibraryPath;
        }
    }

    private static final OperatingSystem OS_SYSTEM = getOS();
    private static final String ARCH = System.getProperty("os.arch").toLowerCase();

    private QuantLibJNIHelpers() {
    }

    private static OperatingSystem getOS() {
        var os = OS_NAME.toLowerCase();
        if (os.startsWith("linux")) {
            return OperatingSystem.LINUX;
        }
        if (os.startsWith("mac")) {
            return OperatingSystem.MAC_OS;
        }
        if (os.startsWith("win")) {
            return OperatingSystem.WINDOWS;
        }
        throw new UnsupportedOperatingSystemException();
    }

    private static String getLibraryName() {
        return switch (OS_SYSTEM) {
            case LINUX -> "libQuantLibJNI.so";
            case MAC_OS -> "libQuantLibJNI.jnilib";
            case WINDOWS -> "QuantLibJNI.dll";
        };
    }

    private static Path getLibraryPath() {
        var libraryName = getLibraryName();
        var rootPath = "/native";
        var path = Path.of(rootPath, OS_SYSTEM.name().toLowerCase());
        return switch (OS_SYSTEM) {
            case LINUX, WINDOWS ->
                    Path.of(path.toString(), normalizeArchitecture(ARCH), libraryName);
            case MAC_OS -> Path.of(path.toString(), libraryName);
        };
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
        LINUX,
        MAC_OS,
        WINDOWS
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
