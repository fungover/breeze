package org.fungover.breeze.stringInterpolation;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class StringTemplateTest {
    @Test
    void testApplyFormatWithoutFormat() {
        String result = StringTemplate.applyFormat("Test", null);
        assertEquals("Test", result);

    }
    @Test
    void testApplyFormatWithNumericFormat() {
        String result = StringTemplate.applyFormat(123.456, "%.2f");
        assertEquals("123.46", result);
    }
    @Test
    void testApplyFormatWithDateTimeFormat(){
        LocalDate date = LocalDate.of(2025, 2, 19);
        String result = StringTemplate.applyFormat(date, "yyyy-MM-dd");
        assertEquals("2025-02-19", result);
    }
    @Test
    void testParseTemplate() {
        String template = "Hello, {name}! Today is {date:yyyy-MM-dd}.";
        List<StringTemplate.Token> tokens = StringTemplate.parseTemplate(template);

        assertEquals(5, tokens.size());
        assertTrue(tokens.get(0).isLiteral());
        assertEquals("Hello, ", tokens.get(0).getContent());

        assertFalse(tokens.get(1).isLiteral());
        assertEquals("name", tokens.get(1).getContent());

        assertTrue(tokens.get(2).isLiteral());
        assertEquals("! Today is ", tokens.get(2).getContent());

        assertFalse(tokens.get(3).isLiteral());
        assertEquals("date", tokens.get(3).getContent());
        assertEquals("yyyy-MM-dd", tokens.get(3).getFormat());

        assertTrue(tokens.get(4).isLiteral());
        assertEquals(".", tokens.get(4).getContent());
    }
    @Test
    void testRenderWithIndexedPlaceholders() {
        String template = "Hello, {0}! Your balance is {1:%.2f}";
        String result = StringTemplate.format(template, "John Doe", 123.456);
        assertEquals("Hello, John Doe! Your balance is 123.46", result);
    }
    @Test
    void testRenderWithNamedPlaceholders() {
        String template = "Hello, {name}! Today is {date:yyyy-MM-dd}";
        String result = StringTemplate.format(template,
                Map.of("name", "John Doe", "date", LocalDate.of(2025, 2, 19)));
        assertEquals("Hello, John Doe! Today is 2025-02-19", result);
    }
    @Test
    void testBuilderPattern() {
        StringTemplate template = StringTemplate.builder()
                .template("Hi {name}, amount: {amount:%.2f}")
                .put("name", "John Doe")
                .put("amount", 99.99)
                .build();
        String result = template.render();
        assertEquals("Hi John Doe, amount: 99.99", result);
    }
    @Test
    void testMissingPlaceholderValueThrowsException() {
        String template = "Hi {name}!";
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> StringTemplate.format(template, Map.of("Random", "John Doe"))
        );
        assertTrue(exception.getMessage().contains("Missing value for placeholder"));
    }
}
