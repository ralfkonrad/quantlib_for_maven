package org.quantlib.helpers;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class QuantLibJNIHelpersTest {

    @Test
    public void testNormalizeArchitectureForAmd64() throws Exception {
        var normalizeArchMethod = QuantLibJNIHelpers.class.getDeclaredMethod("normalizeArchitecture", String.class);
        normalizeArchMethod.setAccessible(true);

        assertEquals("amd64", normalizeArchMethod.invoke(null, "x86_64"));
        assertEquals("amd64", normalizeArchMethod.invoke(null, "amd64"));
    }

    @Test
    public void testNormalizeArchitectureForArm64() throws Exception {
        var normalizeArchMethod = QuantLibJNIHelpers.class.getDeclaredMethod("normalizeArchitecture", String.class);
        normalizeArchMethod.setAccessible(true);

        assertEquals("arm64", normalizeArchMethod.invoke(null, "aarch64"));
        assertEquals("arm64", normalizeArchMethod.invoke(null, "arm64"));
    }

    @Test
    public void testNormalizeArchitectureThrowsForUnsupportedArch() throws Exception {
        var normalizeArchMethod = QuantLibJNIHelpers.class.getDeclaredMethod("normalizeArchitecture", String.class);
        normalizeArchMethod.setAccessible(true);

        assertThrows(Exception.class, () -> normalizeArchMethod.invoke(null, "unsupported"));
    }
}
