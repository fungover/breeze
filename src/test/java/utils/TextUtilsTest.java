package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TextUtilsTest {

    @Test
    public void testCountWords_NormalSentence() {
        String text = "Det här är en mening.";
        int result = TextUtils.countWords(text);
        assertEquals(5, result);
    }

    @Test
    public void testCountWords_EmptyString() {
        String text = "";
        int result = TextUtils.countWords(text);
        assertEquals(0, result);
    }

    @Test
    public void testCountWords_Null() {
        int result = TextUtils.countWords(null);
        assertEquals(0, result);
    }

    @Test
    public void testCountWords_ExtraSpaces() {
        String text = "  En   mening   med  mellanrum  ";
        int result = TextUtils.countWords(text);
        assertEquals(4, result);
    }
}
