package org.quantlib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class QuantLibTest {
    @Test
    public void testLocalDate() {
        try (Settings instance = Settings.instance();
             Date evaluationDate = instance.getEvaluationDate()) {

            Assertions.assertEquals(evaluationDate.toLocalDate(), LocalDate.now());
            Assertions.assertEquals(evaluationDate.toString(), Date.of(LocalDate.now()).toString());
        }
    }
}
