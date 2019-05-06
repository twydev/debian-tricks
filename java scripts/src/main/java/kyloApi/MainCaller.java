package kyloApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Main method to call the API for some quick and dirty results.
 *
 */
public class MainCaller {

    public static void main(String[] args) {
        KyloServiceAPI service = KyloServiceGenerator.createService(KyloServiceAPI.class, Credentials.username, Credentials.password);
        List<String> targetList = new ArrayList<>();
        int recordsTotal = 0;

        try {
            for (int i = 0; i < 197; i+=10) {
                Call<JobsData> callSync = service.getJobsData("10", Integer.toString(i));
                //Call<JobsData> callSync = service.getJobsFailed("10", Integer.toString(i));
                Response<JobsData> response = callSync.execute();
                JobsData jobsData = response.body();
                recordsTotal = jobsData.recordsTotal;
                System.out.println("current total records : " + recordsTotal);

                int j = 0;
                for (Map<String, Object> map : jobsData.data) {
                    j++;
                    if (map.containsKey("jobName") && map.containsKey("executionId")) {
                        String feedName = (String) map.get("jobName");
                        long id = Math.round((double) map.get("executionId"));
                        if (feedName.equals("Feed Name 1")) {
                            if (!targetList.contains(Long.toString(id))) {
                                targetList.add(Long.toString(id));
                            }
                        }
                        System.out.println("#" + j + "Feed : " + feedName + " : " + id );
                    }
                }
            }

            System.out.println("number of targets : " + targetList.size());


            int k = 0;
            for (String id : targetList) {
                Call<Object> callToFail = service.failJob( id );
                callToFail.execute();
                k++;
                System.out.println("#" + k + " - " + id + " - Failed!");
            }

            int q = 0;
            for (String id : targetList) {
                Call<Object> callToAbandon = service.abandonJob( id );
                callToAbandon.execute();
                q++;
                System.out.println("#" + q + " - " + id + " - Terminated!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
