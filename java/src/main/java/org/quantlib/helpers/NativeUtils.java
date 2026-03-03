/*
 * This class is a modernized version of
 *
 * @see <a href="http://adamheinrich.com/blog/2012/how-to-load-native-jni-library-from-jar">http://adamheinrich.com/blog/2012/how-to-load-native-jni-library-from-jar</a>
 * @see <a href="https://github.com/adamheinrich/native-utils">https://github.com/adamheinrich/native-utils</a>
 *
 * A simple library class which helps with loading dynamic libraries stored in the
 * JAR archive. These libraries usually contain implementation of some methods in
 * native code (using JNI - Java Native Interface).
 *
 * Class NativeUtils is published under the The MIT License:
 *
 * Copyright (c) 2012 Adam Heinrich <adam@adamh.cz>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.quantlib.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.ProviderNotFoundException;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.EnumSet;

/**
 * Utility for extracting a native library from the classpath and loading it
 * via {@link System#load(String)}.
 *
 * <p>The library is copied to a temporary directory first to satisfy the
 * filesystem requirements of the JVM native loader.</p>
 */
class NativeUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(NativeUtils.class);
    private static final String NATIVE_FOLDER_PATH_PREFIX = "nativeutils.";

    /**
     * Prevent instantiation of this utility class.
     */
    private NativeUtils() {
    }

    /**
     * Loads a native library packaged inside the application JAR.
     *
     * <p>The library resource is copied to a temporary directory and then loaded
     * with {@link System#load(String)}.</p>
     *
     * @param path absolute classpath resource path to the native library
     * @throws IOException              if the resource cannot be found or copied
     * @throws IllegalArgumentException if {@code path} is not absolute or filename is too short
     */
    public static void loadLibraryFromJar(Path path) throws IOException {
        var filename = getFilename(path);

        var temporaryDir = createTempDirectory();
        var temporaryFile = Path.of(temporaryDir.toString(), filename);

        var javaCompliantPathString = path.toString().replace('\\', '/');
        try (var inputStream = NativeUtils.class.getResourceAsStream(javaCompliantPathString)) {
            if (inputStream == null) {
                throw new FileNotFoundException("File '" + javaCompliantPathString + "' was not found inside JAR.");
            }
            Files.copy(inputStream, temporaryFile);
        }

        try {
            System.load(temporaryFile.toString());

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    Files.deleteIfExists(temporaryFile);
                    Files.deleteIfExists(temporaryDir);
                } catch (Throwable throwable) {
                    try {
                        LOGGER.error("NativeUtils cleanup failed, delete manually: {}", temporaryFile, throwable);
                    } catch (Throwable ignored) {
                        // Logging frameworks may be already stopped during JVM shutdown (especially in tests),
                        // so ensure the failure is visible.
                        try {
                            System.err.println("NativeUtils cleanup failed, delete manually: " + temporaryFile);
                            throwable.printStackTrace(System.err);
                        } catch (Throwable ignoredInner) {
                            // last resort: ignore
                        }
                    }
                }
            }, "nativeutils-cleanup"));
        } finally {
            if (isPosixCompliant()) {
                // Assume POSIX compliant file system, can be deleted after loading
                Files.deleteIfExists(temporaryFile);
                Files.deleteIfExists(temporaryDir);
            } else {
                LOGGER.warn(
                        "Auto-remove of the extracted library '{}' " +
                                "might fail silently during JVM shutdown, " +
                                "remove them manually then",
                        temporaryFile);
            }
        }
    }

    /**
     * Extracts the filename portion of an absolute resource path.
     *
     * @param path absolute classpath resource path
     * @return filename portion of the path
     * @throws IllegalArgumentException if the path is not absolute or filename is too short
     */
    private static String getFilename(Path path) {
        if (null == path || !path.startsWith("/")) {
            throw new IllegalArgumentException("The path '" + path + "' has to be absolute.");
        }
        return path.getFileName().toString();
    }

    /**
     * Creates a temporary directory for extracted native libraries.
     *
     * <p>On POSIX filesystems, a restrictive owner-only permission set is applied.</p>
     *
     * @return path to the created temporary directory
     * @throws IOException if the directory cannot be created
     */
    private static Path createTempDirectory() throws IOException {
        if (isPosixCompliant()) {
            var permissions = PosixFilePermissions.asFileAttribute(
                    EnumSet.of(
                            PosixFilePermission.OWNER_READ,
                            PosixFilePermission.OWNER_WRITE,
                            PosixFilePermission.OWNER_EXECUTE));
            return Files.createTempDirectory(NATIVE_FOLDER_PATH_PREFIX, permissions);
        }

        return Files.createTempDirectory(NATIVE_FOLDER_PATH_PREFIX);
    }

    /**
     * Determines whether the default filesystem supports POSIX file attributes.
     *
     * @return {@code true} if POSIX attributes are supported, otherwise {@code false}
     */
    private static boolean isPosixCompliant() {
        try {
            return FileSystems.getDefault()
                    .supportedFileAttributeViews()
                    .contains("posix");
        } catch (FileSystemNotFoundException
                 | ProviderNotFoundException
                 | SecurityException e) {
            return false;
        }
    }
}
