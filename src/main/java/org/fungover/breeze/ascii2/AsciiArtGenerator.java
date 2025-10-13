package org.fungover.breeze.ascii2;

public class AsciiArtGenerator {

    private final AsciiFont font;

    public AsciiArtGenerator(AsciiFont font) {
        this.font = font;
    }

    public String generate(String text) {
        if (text == null || text.isBlank()) throw new IllegalArgumentException("Text cannot be null or empty");
        if (text.length() > 50) throw new IllegalArgumentException("Text too long. Max 50 characters.");
        return font.render(text);
    }
}
