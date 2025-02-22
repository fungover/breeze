package org.fungover.breeze.util;

import java.util.Objects;
/**
 * The {@code Redacted} class is used to securely store and manage sensitive information.
 * It implements {@link CharSequence} to support polymorphism with {@link String},
 * {@link StringBuilder}, {@link StringBuffer}, and other implementations.
 * <p>
 * The value stored in a {@code Redacted} instance can be redacted (displayed as {@code <redacted>})
 * or wiped (displayed as {@code <wiped>}). Once wiped, the original value cannot be retrieved.
 * </p>
 *
 * Example usage:
 * <pre>
 * Redacted redacted = Redacted.make("MySecretPassword");
 * System.out.println(redacted); // Output: {@code <redacted>}
 * String originalValue = redacted.getValue(); // Retrieves the original value
 * redacted.wipe();
 * System.out.println(redacted); // Output: {@code <wiped>}
 * redacted.getValue(); // Output: {@code <wiped>}
 * </pre>
 *
 */
public class Redacted implements CharSequence {
    private CharSequence value;
    private boolean isWiped;
    private static final String wipedMarker = "<wiped>";
    private static final String redactedMarker = "<redacted>";

    /**
     * Private Constructor
     *
     * @param value The value to be redacted
     */
    private Redacted(CharSequence value) {
        this.value = value;
        this.isWiped = false;
    }

    /**
     * Static factory method to create a {@code Redacted} instance.
     *
     * @param value The value to be redacted.
     * @return A new {@code Redacted} instance.
     * @throws IllegalArgumentException if the secret is null.
     */
    public static Redacted make(CharSequence value) {
        if (value == null) {
            throw new IllegalArgumentException("value can not be null");
        }
        return new Redacted(value);
    }

    /**
     * Method for retrieving the original value.
     *
     * @return Saved {@code CharSequence}.
     */
    public CharSequence getValue() {
        return value;
    }

    /**
     * Marks the value as wiped, preventing further access to the original value.
     */
    public void wipe (){
        this.isWiped = true;
        this.value = wipedMarker;
    }

    /**
     * Returns a redacted or wiped representation of the value.
     *
     * @return {@code <redacted>} if the value is not wiped, otherwise {@code <wiped>}.
     */
    @Override
    public String toString() {
        return isWiped ? wipedMarker : redactedMarker;
    }


    /**
     *
     * @return Value is redacted or wiped and therefor does not return information about value.
     */
    @Override
    public int length() {
        return 0;
    }

    /**
     *
     * @return Value is redacted or wiped and therefor does not return information about value.
     */
    @Override
    public char charAt(int index) {
        return 0;
    }

    /**
     *
     * @return Value is redacted or wiped and therefor does not return information about value.
     */
    @Override
    public CharSequence subSequence(int start, int end) {
        return isWiped ? wipedMarker : redactedMarker;
    }



    /**
     * Compares this Redacted object to another object for equality.
     * Two Redacted objects are considered equal if they are instances of the same class,
     * their {@code isWiped} flags are the same, and their {@code value} fields are equal
     *
     * @param  o The object to compare with this Redacted object.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Redacted secret)) return false;
        return isWiped == secret.isWiped && Objects.equals(value, secret.value);
    }
    /**
     * @return The hash code for this Redacted object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(value, isWiped);
    }

}
