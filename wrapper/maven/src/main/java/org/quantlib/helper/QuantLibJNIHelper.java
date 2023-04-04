package org.quantlib.helper;

import cz.adamh.utils.NativeUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QuantLibJNIHelper {

    private static final OperatingSystem OS = getOS();
    private static final String ARCH = System.getProperty("os.arch").toLowerCase();

    private QuantLibJNIHelper() {
    }

    public static void loadLibrary() {
        try {
            String libraryPath = getLibraryPath();
            NativeUtils.loadLibraryFromJar(libraryPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static OperatingSystem getOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.startsWith("linux"))
            return OperatingSystem.Linux;
        if (os.startsWith("mac"))
            return OperatingSystem.MacOs;
        if (os.startsWith("win"))
            return OperatingSystem.Windows;
        throw new UnsupportedOperatingSystemException(os);
    }

    private static String getLibraryName() {
        switch (OS) {
            case Linux:
            case MacOs:
                return "libQuantLibJNI.so";

            case Windows:
                return "QuantLibJNI.dll";

            default:
                throw new UnsupportedOperatingSystemException(OS.name());
        }
    }

    private static String getLibraryPath() {
        String rootPath = "/native";
        String path = String.join("/", rootPath, OS.name().toLowerCase());
        switch (OS) {
            case Linux:
            case MacOs:
                return String.join("/", path, getLibraryName());

            case Windows:
                return String.join("/", path, getWindowsArchiceture(), getLibraryName());

            default:
                throw new UnsupportedOperatingSystemException(OS.name());
        }
    }

    private static String getWindowsArchiceture() {
        String arch = System.getProperty("os.arch").toLowerCase();
        return arch;
    }

    private enum OperatingSystem {
        Linux,
        MacOs,
        Windows
    }

    public static class UnsupportedOperatingSystemException extends RuntimeException {
        private final String os;

        private UnsupportedOperatingSystemException(String os) {
            this.os = os;
        }

        public String getUnsupportedOperatingSystem() {
            return os;
        }
    }
}
