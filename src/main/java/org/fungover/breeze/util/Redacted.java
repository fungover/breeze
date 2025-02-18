package org.fungover.breeze.util;

public class Redacted implements CharSequence {

    private Redacted(CharSequence savedValue) {
    }

    public static Redacted make(CharSequence savedValue) {
        return new Redacted(savedValue);
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


