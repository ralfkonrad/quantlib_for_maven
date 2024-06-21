package org.quantlib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ZigguratXoshiro256StarStarGaussianTest {
    private static final int SEED = 42;
    private static final double FIRST_EXPECTED = -2.13814;
    private static final double SECOND_EXPECTED = -0.37490;
    private static final double TOLERANCE = 1e-5;

    @Test
    public void testGaussianRngNextValue() {
        var rng = new Xoshiro256StarStarUniformRng(SEED);
        var gaussianRng = new ZigguratXoshiro256StarStarGaussianRng(rng);

        Assertions.assertEquals(FIRST_EXPECTED, gaussianRng.next().value(), TOLERANCE);
        Assertions.assertEquals(SECOND_EXPECTED, gaussianRng.next().value(), TOLERANCE);
    }

    @Test
    public void testGaussianRsgNextValue() {
        var rng = new Xoshiro256StarStarUniformRng(SEED);
        var gaussianRng = new ZigguratXoshiro256StarStarGaussianRng(rng);
        var gaussianRsg = new ZigguratXoshiro256StarStarGaussianRsg(2, gaussianRng);
        var sample = gaussianRsg.nextSequence();

        Assertions.assertEquals(FIRST_EXPECTED, sample.value().get(0), TOLERANCE);
        Assertions.assertEquals(SECOND_EXPECTED, sample.value().get(1), TOLERANCE);
    }
}
