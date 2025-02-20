package org.fungover.breeze.math;

import java.security.SecureRandom;

/**
 * A utility class containing common mathematical functions used for graphics, physics, and simulations.
 * This class includes methods for linear interpolation, clamping values, generating random values, trigonometric functions, and more.
 */
public class MathUtils {

    //Secure random generator for generating random values
    static SecureRandom random = new SecureRandom();

    /**
     * Clamps a value between a minimum and maximum value.
     * This is useful to ensure that a value stays within a valid range, such as when calculating color values
     * or bounding values in physics simulations.
     *
     * @param value The value to clamp.
     * @param min The minimum value.
     * @param max The maximum value.
     * @return The clamped value, which will be between min and max.
     */
    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Performs linear interpolation between two values.
     * This is essential for animations, blending colors, and interpolating positions in graphics.
     * The value t determines the interpolation factor between a and b.
     *
     * @param a The starting value.
     * @param b The ending value.
     * @param t The interpolation factor, where 0 returns a and 1 returns b.
     * @return The interpolated value.
     */
    public static float lerp(float a, float b, float t) {
        return a + t * (b - a);
    }

    /**
     * Performs clamped linear interpolation between two values.
     * Similar to lerp, but the interpolation factor t is clamped between 0 and 1 to prevent extrapolation.
     * This is useful when interpolating positions or states that should not move beyond specified bounds.
     *
     * @param a The starting value.
     * @param b The ending value.
     * @param t The interpolation factor, which will be clamped between 0 and 1.
     * @return The clamped interpolated value.
     */
    public static float lerpClamped(float a, float b, float t) {
        return a + MathUtils.clamp(t, 0.0f, 1.0f) * (b - a);
    }

    /**
     * Performs smooth step interpolation, which creates a smooth transition between two edges.
     * This is used for anti-aliasing, shader effects, and procedural textures to make transitions less abrupt.
     *
     * @param edge0 The lower edge of the interpolation range.
     * @param edge1 The upper edge of the interpolation range.
     * @param x The input value that will be mapped between edge0 and edge1.
     * @return The smooth transition value between edge0 and edge1.
     */
    public static float smoothStep(float edge0, float edge1, float x) {
        float t = clamp((x - edge0) / (edge1 - edge0), 0.0f, 1.0f);
        return t * t * (3 - 2 * t);
    }

    /**
     * Generates a random floating-point number within the specified range.
     * This method uses a cryptographically secure random number generator.
     *
     * @param min The minimum value of the random range.
     * @param max The maximum value of the random range.
     * @return A random float between min and max.
     */
    public static float randomFloat(float min, float max) {
        return min + random.nextFloat() * (max - min);
    }

    /**
     * Computes the sine of an angle in radians.
     *
     * @param angle The angle in radians.
     * @return The sine of the angle.
     */
    public static float sin(float angle) {
        return (float) Math.sin(angle);
    }

    /**
     * Computes the cosine of an angle in radians.
     *
     * @param angle The angle in radians.
     * @return The cosine of the angle.
     */
    public static float cos(float angle) {
        return (float) Math.cos(angle);
    }

    /**
     * Computes the tangent of an angle in radians.
     *
     * @param angle The angle in radians.
     * @return The tangent of the angle.
     */
    public static float tan(float angle) {
        return (float) Math.tan(angle);
    }

    /**
     * Computes the arc tangent of the two values y and x. This function returns the angle in radians
     * between the positive x-axis and the point given by the coordinates (x, y).
     *
     * @param y The y-coordinate.
     * @param x The x-coordinate.
     * @return The angle in radians between the positive x-axis and the point (x, y).
     */
    public static float atan2(float y, float x) {
        return (float) Math.atan2(y, x);
    }
}