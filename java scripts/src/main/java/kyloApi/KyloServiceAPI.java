package kyloApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface used to to call Kylo API.
 *
 * Method interfaces expose various Kylo API endpoints and specify the API parameters that the method caller may use.
 * Actual methods Will be implemented by retrofit2 framework.
 */
public interface KyloServiceAPI {

    @GET("/proxy/v1/about/me")
    Call<UserInfo> getRequesterInfo();

    @GET("/proxy/v1/jobs/running")
    Call<JobsData> getJobsData(@Query("limit") String limit, @Query("start") String start);

    @GET("/proxy/v1/jobs/failed")
    Call<JobsData> getJobsFailed(@Query("limit") String limit, @Query("start") String start);

    @POST("/proxy/v1/jobs/{executionId}/fail")
    Call<Object> failJob(@Path("executionId") String executionId);

    @POST("/proxy/v1/jobs/{executionId}/abandon")
    Call<Object> abandonJob(@Path("executionId") String executionId);

    @GET("/proxy/v1/feedmgr/feeds")
    Call<JobsData> getAllFeeds();

    @GET("/proxy/v1/metadata/feed/{feedId}/watermark/highWaterMark")
    Call<String> getHighWaterMark(@Path("feedId") String feedId);
}
