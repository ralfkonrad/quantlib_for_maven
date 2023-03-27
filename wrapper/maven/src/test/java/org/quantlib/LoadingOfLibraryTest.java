package org.quantlib;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LoadingOfLibraryTest {
    static {
        try {
            cz.adamh.utils.NativeUtils.loadLibraryFromJar("/org/quantlib/QuantLibJNI.dll");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testLoading() {
        Date date = Settings.instance().getEvaluationDate();
    }
}
