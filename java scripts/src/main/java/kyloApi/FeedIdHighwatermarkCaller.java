package kyloApi;

import retrofit2.Call;
import retrofit2.Response;

import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Main method to check the latest highwatermark for selected feeds.
 */
public class FeedIdHighwatermarkCaller {

    public static void makeHighWaterMarkCall() {
        List<String> monitoredFeeds = Arrays.asList( new String[] {
                "Feed Name 1",
                "Feed Name 2"
        });

        KyloServiceAPI service = KyloServiceGenerator.createService(KyloServiceAPI.class, Credentials.username, Credentials.password);
        Map<String, String> outputMap = new HashMap();

        try {

            Call<JobsData> callSync = service.getAllFeeds();
            Response<JobsData> response = callSync.execute();
            JobsData dataObject = response.body();
            int j = 1;
            for (Map<String, Object> map : dataObject.data) {
                if (map.containsKey("feedName")) {
                    if (map.containsKey("feedId")) {
                        String feedname = (String) map.get("feedName");
                        String feedid = (String) map.get("feedId");
                        outputMap.put(feedname, feedid);
                    }
                }
            }

            System.out.println("-------- Current High Water Mark Values --------");
            for (String key : monitoredFeeds) {
                if (outputMap.containsKey(key)) {
                    String feedId = outputMap.get(key);
                    Call<String> callStr = service.getHighWaterMark(feedId);
                    Response<String> hwmResponse = callStr.execute();
                    String highwatermark = hwmResponse.body();
                    String[] hwmValue = highwatermark.split("\\|");
                    Date timeStamp = new Date();
                    timeStamp.setTime(Long.parseLong(hwmValue[0]));
                    System.out.println(timeStamp + " | " + hwmValue[1]);
                }
            }
            System.out.println("------------------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
