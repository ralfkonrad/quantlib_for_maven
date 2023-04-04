package org.quantlib;

import org.junit.jupiter.api.Test;

public class LoadingOfLibraryTest {
    @Test
    public void testLoading() {
        try (Settings instance = Settings.instance();
             Date ignored = instance.getEvaluationDate()) {
        }
    }
}
