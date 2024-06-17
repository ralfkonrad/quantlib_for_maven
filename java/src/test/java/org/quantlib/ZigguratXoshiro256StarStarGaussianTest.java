package org.quantlib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ZigguratXoshiro256StarStarGaussianTest {
    @Test
    public void testNextValue() {
        var rng = new Xoshiro256StarStarUniformRng(42);
        var gaussianRng = new ZigguratXoshiro256StarStarGaussianRng(rng);
        var expected = -2.13814;
        Assertions.assertEquals(expected, gaussianRng.next().value(), 1e-5);
    }
}
