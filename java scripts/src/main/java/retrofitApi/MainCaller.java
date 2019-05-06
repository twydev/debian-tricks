package retrofitApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Example of how to use retrofit2 library to call API endpoints
 * This example follows the online tutorial: https://www.vogella.com/tutorials/Retrofit/article.html
 * Uses a mock endpoint provided by: https://jsonplaceholder.typicode.com/
 * Other helpful resources: https://square.github.io/retrofit/
 */
public class MainCaller {
    public static void main(String[] args) {
        String BASE_URL = "https://jsonplaceholder.typicode.com/";

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GerritAPI service = retrofit.create(GerritAPI.class);
        Call<GerritObject> callSync = service.getResp("3");

        try {
            Response<GerritObject> response = callSync.execute();
            GerritObject go = response.body();
            System.out.println(go);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
