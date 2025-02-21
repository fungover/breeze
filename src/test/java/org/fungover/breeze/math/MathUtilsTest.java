package org.fungover.breeze.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class MathUtilsTest {

    @Test
    void testClamp() {
        assertEquals(5.0f, MathUtils.clamp(5.0f, 0.0f, 10.0f));
        assertEquals(0.0f, MathUtils.clamp(-1.0f, 0.0f, 10.0f));
        assertEquals(10.0f, MathUtils.clamp(15.0f, 0.0f, 10.0f));
        assertEquals(5.0f, MathUtils.clamp(5.0f, -10.0f, 10.0f));
        assertEquals(-10.0f, MathUtils.clamp(-20.0f, -10.0f, -1.0f));
        assertEquals(-1.0f, MathUtils.clamp(0.0f, -10.0f, -1.0f));
    }

    @Test
    void testLerpWithinRange() {
        assertEquals(5.0f, MathUtils.lerp(0.0f, 10.0f, 0.5f), 1e-6);
        assertEquals(10.0f, MathUtils.lerp(0.0f, 10.0f, 1.0f), 1e-6);
        assertEquals(0.0f, MathUtils.lerp(0.0f, 10.0f, 0.0f), 1e-6);
        assertEquals(-5.0f, MathUtils.lerp(-10.0f, 0.0f, 0.5f), 1e-6);
        assertEquals(0.0f, MathUtils.lerp(-10.0f, 0.0f, 1.0f), 1e-6);
        assertEquals(-10.0f, MathUtils.lerp(-10.0f, 0.0f, 0.0f), 1e-6);
        assertEquals(2.0f, MathUtils.lerp(-10.5f, 14.5f, 0.5f), 1e-6);
        assertEquals(14.5f, MathUtils.lerp(-10.5f, 14.5f, 1.0f), 1e-6);
        assertEquals(-10.5f, MathUtils.lerp(-10.5f, 14.5f, 0.0f), 1e-6);
        assertEquals(-7.5f, MathUtils.lerp(-10.5f, -4.5f, 0.5f), 1e-6);
        assertEquals(-4.5f, MathUtils.lerp(-10.5f, -4.5f, 1.0f), 1e-6);
        assertEquals(-10.5f, MathUtils.lerp(-10.5f, -4.5f, 0.0f), 1e-6);
    }

    @Test
    void testLerpExtrapolation() {
        assertEquals(-20.0f, MathUtils.lerp(0.0f, 10.0f, -2.0f), 1e-6);
        assertEquals(30.0f, MathUtils.lerp(10.0f, 0.0f, -2.0f), 1e-6);
        assertEquals(20.0f, MathUtils.lerp(0.0f, 10.0f, 2.0f), 1e-6);
        assertEquals(-10.0f, MathUtils.lerp(10.0f, 0.0f, 2.0f), 1e-6);
        assertEquals(30.0f, MathUtils.lerp(-10.0f, 10.0f, 2.0f), 1e-6);
        assertEquals(14.0f, MathUtils.lerp(-10.0f, 10.0f, 1.2f), 1e-6);
        assertEquals(-34.0f, MathUtils.lerp(-10.0f, 10.0f, -1.2f), 1e-6);
        assertEquals(-7.6f, MathUtils.lerp(-10.0f, -8.0f, 1.2f), 1e-6);
        assertEquals(5.6f, MathUtils.lerp(8.0f, 10.0f, -1.2f), 1e-6);
    }

    @Test
    void testLerpClampedWithinRange() {
        assertEquals(5.0f, MathUtils.lerpClamped(0.0f, 10.0f, 0.5f), 1e-6);
        assertEquals(0.0f, MathUtils.lerpClamped(0.0f, 10.0f, 0.0f), 1e-6);
        assertEquals(10.0f, MathUtils.lerpClamped(0.0f, 10.0f, 1.0f), 1e-6);
    }

    @Test
    void testLerpClampedBelowZero() {
        assertEquals(0.0f, MathUtils.lerpClamped(0.0f, 10.0f, -0.5f), 1e-6);
        assertEquals(5.0f, MathUtils.lerpClamped(5.0f, 15.0f, -1.0f), 1e-6);
        assertEquals(5.0f, MathUtils.lerpClamped(5.0f, 7.0f, -1.0f), 1e-6);
        assertEquals(-15.0f, MathUtils.lerpClamped(-15.0f, -7.0f, -1.0f), 1e-6);
        assertEquals(-15.0f, MathUtils.lerpClamped(-15.0f, -7.0f, -0.5f), 1e-6);
    }

    @Test
    void testLerpClampedAboveOne() {
        assertEquals(10.0f, MathUtils.lerpClamped(0.0f, 10.0f, 1.5f), 1e-6);
        assertEquals(15.0f, MathUtils.lerpClamped(5.0f, 15.0f, 2.0f), 1e-6);
        assertEquals(7.0f, MathUtils.lerpClamped(5.0f, 7.0f, 2.0f), 1e-6);
        assertEquals(-7.0f, MathUtils.lerpClamped(-15.0f, -7.0f, 1.5f), 1e-6);
        assertEquals(-7.0f, MathUtils.lerpClamped(-15.0f, -7.0f, 3.5f), 1e-6);
    }

    @Test
    void testSmoothStepWithinRange() {
        assertEquals(0.0f, MathUtils.smoothStep(0.0f, 10.0f, 0.0f), 1e-6);
        assertEquals(0.5f, MathUtils.smoothStep(0.0f, 10.0f, 5.0f), 1e-6);
        assertEquals(1.0f, MathUtils.smoothStep(0.0f, 10.0f, 10.0f), 1e-6);
    }

    @Test
    void testSmoothStepBelowRange() {
        assertEquals(0.0f, MathUtils.smoothStep(0.0f, 10.0f, -5.0f), 1e-6);
        assertEquals(0.0f, MathUtils.smoothStep(2.0f, 8.0f, 1.0f), 1e-6);
    }

    @Test
    void testSmoothStepAboveRange() {
        assertEquals(1.0f, MathUtils.smoothStep(0.0f, 10.0f, 15.0f), 1e-6);
        assertEquals(1.0f, MathUtils.smoothStep(2.0f, 8.0f, 9.0f), 1e-6);
    }

    @Test
    void testSmoothStepEasingEffect() {
        float start = MathUtils.smoothStep(0.0f, 10.0f, 2.5f);  // Should be slow (t < 0.5)
        float middle = MathUtils.smoothStep(0.0f, 10.0f, 5.0f); // Should be fastest (t = 0.5)
        float end = MathUtils.smoothStep(0.0f, 10.0f, 7.5f);    // Should slow down (t > 0.5)

        assertTrue(start < middle, "Easing should start slow");
        assertTrue(middle < end, "Easing should accelerate in the middle");
        assertEquals(0.5f, middle, 1e-6, "Middle value should be exactly 0.5");

        // Additional checks for slow start and slow end
        float t1 = MathUtils.smoothStep(0.0f, 10.0f, 1.0f);
        float t2 = MathUtils.smoothStep(0.0f, 10.0f, 9.0f);

        assertTrue(t1 < 0.1f, "Very early step should be close to 0");
        assertTrue(t2 > 0.9f, "Very late step should be close to 1");
    }

    @Test
    void testRandomFloatWithinRange() {
        for (int i = 0; i < 1000; i++) { // Run multiple times to reduce fluke failures
            float value = MathUtils.randomFloat(5.0f, 10.0f);
            assertTrue(value >= 5.0f && value <= 10.0f, "randomFloat should stay in range");
        }
    }

    @Test
    void testRandomFloatEdgeCases() {
        assertEquals(5.0f, MathUtils.randomFloat(5.0f, 5.0f), 1e-6, "min == max should always return min");

        for (int i = 0; i < 1000; i++) {
            float value = MathUtils.randomFloat(-10.0f, -5.0f);
            assertTrue(value >= -10.0f && value <= -5.0f, "Negative ranges should work");
        }
    }

    @Test
    void testSinCosTan() {
        assertEquals(0.0, MathUtils.sin(0), 1e-6);
        assertEquals(1.0, MathUtils.cos(0), 1e-6);
        assertEquals(0.0, MathUtils.tan(0), 1e-6);
    }

    @Test
    void testAtan2() {
        assertEquals(Math.PI / 4, MathUtils.atan2(1, 1), 1e-6);
    }


}
