/**
 * Main Method to experiment with different scenarios
 * to observe behaviour of 'finally'
 */
public class FinallyClause {

    public static void main(String[] args) {
        System.out.println(tryfinally());
    }

    public static int tryfinally() {
        try {
            return 1;
        } finally {
            return 2;
        }
    }
}
