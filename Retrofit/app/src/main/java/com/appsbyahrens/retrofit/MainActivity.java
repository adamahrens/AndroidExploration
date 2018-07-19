package com.appsbyahrens.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

  private String LOG_TAG = MainActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.ipify.org/").build();

    Api api = retrofit.create(Api.class);

    api.getMyIp("json").enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        try {
          String body = response.body().string();
          Log.d(LOG_TAG, "Got response: " + body);

          try {
            JSONObject json = new JSONObject(body);
            String ip = json.getString("ip");
            Log.d(LOG_TAG, "Your actual ip is " + ip);
          } catch (JSONException exception) {
            Log.d(LOG_TAG, "Error, invalid JSON. " + exception.getMessage());
          }

        } catch (IOException exception) {
          Log.d(LOG_TAG, "Error parsing body string. " + exception.getMessage());
        }
      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.d(LOG_TAG, "Got failure: " + t.getMessage());
      }
    });
  }

  interface Api {
    @GET("/")
    Call<ResponseBody> getMyIp(@Query("format") String format);
  }
}
