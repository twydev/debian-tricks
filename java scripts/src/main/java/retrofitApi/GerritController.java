package retrofitApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GerritController implements Callback<GerritObject> {
    static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GerritAPI gerritAPI = retrofit.create(GerritAPI.class);

        Call<GerritObject> call = gerritAPI.getResp("1");
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<GerritObject> call, Response<GerritObject> response) {
        if(response.isSuccessful()) {
            GerritObject go = response.body();
            System.out.println(go);
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<GerritObject> call, Throwable t) {
        t.printStackTrace();
    }
}
