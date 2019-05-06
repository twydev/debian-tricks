import java.io.*;

/**
 * Example of how to read content from local files
 */
public class ReadFileContents {

    public static String read(String relativeFilepathFromRoot) {
        String output = null;
        try {
            String filename = new File("").getAbsolutePath().concat(relativeFilepathFromRoot);
            InputStream is = new FileInputStream(filename);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }
            output = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

}