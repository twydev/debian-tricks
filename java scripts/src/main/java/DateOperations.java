import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Example to work with Date format in Java
 */
public class DateOperations {
    public static void main (String[] args) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date today = format.parse("2018-11-01");
            System.out.println(today);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
