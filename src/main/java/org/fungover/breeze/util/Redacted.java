package org.fungover.breeze.util;

public class Redacted implements CharSequence {
    private transient CharSequence savedValue;

    private Redacted(CharSequence savedValue) {
        this.savedValue = savedValue;
    }

    public static Redacted make(CharSequence savedValue) {
        return new Redacted(savedValue);
    }

    public CharSequence getValue() {
        return this.savedValue;
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


