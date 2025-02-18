package org.fungover.breeze.util;

public class Redacted implements CharSequence {
    private final CharSequence value;
    boolean isWiped;

    /**
     * Private Constructor
     *
     * @param savedValue The value to be redacted
     */
    private Redacted(CharSequence savedValue) {
        this.value = savedValue;
        this.isWiped = false;
    }

    /**
     * Static factory method to create a {@code Redacted} instance.
     *
     * @param savedValue The value to be redacted.
     * @return A new {@code Redacted} instance.
     * @throws IllegalArgumentException if the secret is null.
     */
    public static Redacted make(CharSequence savedValue) {
        if (savedValue == null) {
            throw new IllegalArgumentException("SavedValue can not be null");
        }
        return new Redacted(savedValue);
    }

    /**
     * Method for retrieving the value saved in {@code Redacted}
     * @return Saved {@code CharSequence}
     */
    public CharSequence getValue() {
        if(isWiped) {
            throw new IllegalStateException("Value has been wiped and is no longer accessible");
        }
        return this.value;
    }

    public void wipe (){
        this.isWiped = true;
    }

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
        return null;
    }


}


