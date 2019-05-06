package retrofitApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GerritAPI {

    @GET("todos/{testParam}")
    Call<GerritObject> getResp(@Path("testParam") String endpoint);
}
