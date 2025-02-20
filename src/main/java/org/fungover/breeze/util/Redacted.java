package org.fungover.breeze.util;

public class Redacted implements CharSequence {
    private final CharSequence value;
    private boolean isWiped;

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
     * @throws IllegalStateException if value has been wiped.
     */
    public CharSequence getValue() {
        return isWiped ? "<wiped>" : value;
    }

    /**
     * Marks the value as wiped, preventing further access to the original value.
     */
    public void wipe (){
        this.isWiped = true;
    }

    /**
     * Returns a redacted or wiped representation of the value.
     *
     * @return {@code <redacted>} if the value is not wiped, otherwise {@code <wiped>}.
     */
    @Override
    public String toString() {
        return isWiped ? "<wiped>" : "<redacted>";
    }

    // CharSequence Implementation
    @Override
    public int length() {
        return 0;
    }

    @Override
    public char charAt(int index) {
        return 0;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return isWiped ? "<wiped>" : "<redacted>";
    }
}
