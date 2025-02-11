package org.fungover.breeze;

/**
 * A utility class for when you need to do absolutely nothing, but in a professional way.
 *
 * @deprecated As of release 0.2.0. Will be removed in a future release
 */
@Deprecated(since="0.2.0", forRemoval = true)
public class Procrastinator {

    private Procrastinator() {}

    /**
     * Does absolutely nothing, but promises to do it tomorrow.
     *
     * @return always returns "I'll do it tomorrow" because that's what procrastinators do
     */
    public static String doItLater() {
        return "I'll do it tomorrow";
    }
}
