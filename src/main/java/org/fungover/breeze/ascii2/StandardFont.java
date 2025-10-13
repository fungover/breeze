package org.fungover.breeze.ascii2;

import java.util.HashMap;
import java.util.Map;

public class StandardFont implements AsciiFont {

    private final Map<Character, String[]> fontMap = new HashMap<>();

    public StandardFont() {
        loadFont();
    }

    private void loadFont() {
        // Simplified version, more characters can be added
        fontMap.put('A', new String[]{
                "  A  ",
                " A A ",
                "AAAAA"
        });
        fontMap.put('B', new String[]{
                "BBB ",
                "B  B",
                "BBB "
        });
        fontMap.put('H', new String[]{
                "H  H",
                "HHHH",
                "H  H"
        });
        fontMap.put('E', new String[]{
                "EEE ",
                "EE  ",
                "EEE "
        });
        fontMap.put('L', new String[]{
                "L   ",
                "L   ",
                "LLL "
        });
        fontMap.put('O', new String[]{
                " OO ",
                "O  O",
                " OO "
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
        return "Standard";
    }
}
