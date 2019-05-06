/**
 * Main Method to experiment with Pass-by-value
 * or sequence of execution in Java
 */
public class PassByValue {

    public static String TEST = "AHHAHAH";

    public static void main (String[] args) {
        Integer i = new Integer(0);
        modVal(i);
        System.out.println(i);
//        System.out.println(TEST);
    }

    public static void modVal (Integer i) {
        i = new Integer(3);
    }
}
