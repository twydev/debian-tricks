package kyloApi;

import retrofit2.Call;
import retrofit2.Response;

import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;

/**
 * Main method to create a status summary of all feeds in Kylo.
 * Writes the information out in CSV
 */
public class FeedSummaryCaller {

    public static void main(String[] args) {
        KyloServiceAPI service = KyloServiceGenerator.createService(KyloServiceAPI.class, Credentials.username, Credentials.password);

        try {

            Call<JobsData> callSync = service.getAllFeeds();
            Response<JobsData> response = callSync.execute();
            JobsData dataObject = response.body();
            System.out.println("Number of Feeds total : " + dataObject.recordsTotal);
            String headerline = "Category,Feed,State,Template,Owner,Email,FeedLastUpdated,FeedId,PublishedOn";

            String filename = new File("").getAbsolutePath().concat("\\src\\test\\outputfiles\\feed_summary.csv");
            OutputStream os = new FileOutputStream(filename);
            BufferedWriter buf = new BufferedWriter(new OutputStreamWriter(os));
            buf.write(headerline);
            buf.newLine();
            System.out.println(headerline);

            int j = 1;
            for (Map<String, Object> map : dataObject.data) {
                String feedname = "None";
                String categoryname = "None";

                String state = "None";
                String template = "None";
                String email = "None";
                String name = "None";

                String feedid = "None";
                String lastUpdated = "None";

                String delimiter = ",";
                StringBuilder builder = new StringBuilder();

                if (map.containsKey("categoryAndFeedSystemName")) {
                    String sysCatName = (String) map.get("categoryAndFeedSystemName");
                    String[] names = sysCatName.split("[.]");
                    categoryname = names[0];
                    feedname = names[1];
                }
                builder.append(categoryname);
                builder.append(delimiter);
                builder.append(feedname);
                builder.append(delimiter);

                if (map.containsKey("state")) {
                    state = (String) map.get("state");
                }
                builder.append(state);
                builder.append(delimiter);

                if (map.containsKey("templateName")) {
                    template = (String) map.get("templateName");
                }
                builder.append(template);
                builder.append(delimiter);

                if (map.containsKey("owner") && map.get("owner") != null) {
                    Map owner = (Map) map.get("owner");
                    if (owner.containsKey("email") && owner.get("email") != null) {
                        email = (String) owner.get("email");
                    }
                    if (owner.containsKey("displayName") && owner.get("displayName") != null) {
                        name = (String) owner.get("displayName");
                    }
                }
                builder.append(name);
                builder.append(delimiter);
                builder.append(email);
                builder.append(delimiter);

                if (map.containsKey("updateDate")) {
                    long timeSinceEpoch = ((Double) map.get("updateDate")).longValue();
                    LocalDate date = Instant.ofEpochMilli(timeSinceEpoch).atZone(ZoneId.systemDefault()).toLocalDate();
                    lastUpdated = date.toString();
                }
                builder.append(lastUpdated);
                builder.append(delimiter);

                if (map.containsKey("feedId")) {
                    feedid = (String) map.get("feedId");
                }
                builder.append(feedid);
                builder.append(delimiter);

                LocalDate today = LocalDate.now();
                builder.append(today.toString());

                String lineEntry = builder.toString();
                buf.write(lineEntry);
                buf.newLine();
                System.out.println(lineEntry);
                j++;
            }
            buf.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
