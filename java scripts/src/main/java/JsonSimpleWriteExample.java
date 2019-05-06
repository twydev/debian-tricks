import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Example to write a JSON file
 */
public class JsonSimpleWriteExample {
    public static void main(String[] args) {
        JSONObject obj = new JSONObject();
        obj.put("name", "john");
        obj.put("age", new Integer(100));

        JSONArray list = new JSONArray();
        list.add("msg 1");
        list.add("msg 2");
        list.add("msg 3");

        obj.put("messages", list);

        try {
            FileWriter file = new FileWriter("test.json");
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(obj);
    }
}
