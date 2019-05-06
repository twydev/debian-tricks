import java.util.regex.Pattern;

/**
 * Main Method to conveniently experiment with Regex pattern matching
 */
public class RegexTester {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("[.][0-9]*");
        String testInput = "123.450";
        System.out.println(pattern.matcher(testInput).replaceAll(""));
    }
}
