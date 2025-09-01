package org.fungover.breeze.ascii2;

import java.util.HashMap;
import java.util.Map;

public class BlockFont implements AsciiFont {

    private final Map<Character, String[]> fontMap = new HashMap<>();

    public BlockFont() {
        loadFont();
    }

    private void loadFont() {
        fontMap.put('H', new String[]{
                "██  ██",
                "██████",
                "██  ██"
        });
        fontMap.put('E', new String[]{
                "█████ ",
                "████  ",
                "█████ "
        });
        fontMap.put('L', new String[]{
                "██    ",
                "██    ",
                "█████ "
        });
        fontMap.put('O', new String[]{
                " ███  ",
                "█   █ ",
                " ███  "
        });
    }

    @Override
    public String render(String text) {
        if (text == null || text.isBlank()) throw new IllegalArgumentException("Text cannot be null or empty");
        text = text.toUpperCase();
        StringBuilder sb = new StringBuilder();
        int height = 3;
        for (int row = 0; row < height; row++) {
            for (char c : text.toCharArray()) {
                if (!fontMap.containsKey(c)) {
                    throw new IllegalArgumentException("Unsupported character: " + c);
                }
                sb.append(fontMap.get(c)[row]).append("  ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String getName() {
        return "Block";
    }
}
