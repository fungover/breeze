package org.fungover.breeze.math;

import java.security.SecureRandom;

public class MathUtils {
//    private static final Random RANDOM = new Random();
static SecureRandom random = new SecureRandom();
//    byte bytes[] = new byte[20];

//    Ensures values stay within a valid range, which is crucial for color calculations, physics simulations, and normalization of values.
    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

//   Linear Interpolation. Essential for animations, blending colors, and interpolating positions in graphics.
    public static float lerp(float a, float b, float t) {
        return a + t * (b - a);
    }

//   Linear Interpolation. Prevented extrapolation.
//   Use: When interpolating an object’s position between two points, clamping prevents it from moving beyond its intended range.
    public static float lerpClamped(float a, float b, float t) {
        return a + MathUtils.clamp(t, 0.0f, 1.0f) * (b - a);
    }

//    Helps create smooth transitions, which is important for anti-aliasing, shader effects, and procedural textures.
    public static float smoothStep(float edge0, float edge1, float x) {
        float t = clamp((x - edge0) / (edge1 - edge0), 0.0f, 1.0f);
        return t * t * (3 - 2 * t);
    }

//    Generates a random floating-point number in the range
    public static float randomFloat(float min, float max) {
        return min + random.nextFloat() * (max - min);
    }


// Trigonometric functions sin, cos, tan, atan2
    public static float sin(float angle) {
        return (float) Math.sin(angle);
    }

    public static float cos(float angle) {
        return (float) Math.cos(angle);
    }

    public static float tan(float angle) {
        return (float) Math.tan(angle);
    }

    public static float atan2(float y, float x) {
        return (float) Math.atan2(y, x);
    }


}




