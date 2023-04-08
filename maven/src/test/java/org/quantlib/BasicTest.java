package org.quantlib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class BasicTest {
    @Test
    public void testLoading() {
        try (Settings instance = Settings.instance();
             Date evaluationDate = instance.getEvaluationDate()) {
            LocalDate localDate = evaluationDate.toLocalDate();
            Assertions.assertEquals(localDate, LocalDate.now());
            Assertions.assertEquals(evaluationDate.toString(), Date.of(LocalDate.now()).toString());
        }
    }
}
