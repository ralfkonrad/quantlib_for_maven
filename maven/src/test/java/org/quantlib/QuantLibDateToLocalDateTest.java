package org.quantlib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class QuantLibDateToLocalDateTest {

    @Test
    public void testQuantLibDateToLocalDate() {
        try (var qlDate = new Date(14, Month.April, 2023)) {
            var localDate = LocalDate.of(2023, 4, 14);

            Assertions.assertEquals(localDate, qlDate.toLocalDate());
            Assertions.assertEquals(qlDate.toString(), Date.of(localDate).toString());
        }
    }
}
