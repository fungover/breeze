public class StringUtil {

    public static String reverse(String input) {
        if (input == null) {
            return null;
        }
        return new StringBuilder(input).reverse().toString();
    }

    public static double calculateArea(double length, double width) {
        if (length <= 0 || width <= 0) {
            return 0;
        }
        return length * width;
    }
}
git add .
git commit -m "Fixar #142: Implementerade funktionen calculateArea(length, width)"