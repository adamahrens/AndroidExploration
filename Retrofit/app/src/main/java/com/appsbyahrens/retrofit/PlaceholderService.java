package com.appsbyahrens.retrofit;

import android.util.Log;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PlaceholderService {

  private Retrofit retrofit;
  private PlaceholderApi api;
  private String LOG_TAG = PlaceholderService.class.getSimpleName();

  PlaceholderService() {
    retrofit = new Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build();
    api = retrofit.create(PlaceholderApi.class);
  }

  public void fetchUsers() {
    api.getUsers().enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Log.d(LOG_TAG, "Success getUsers");

        try {
          String responseString = response.body().string();
          Log.d(LOG_TAG, "Users = " + responseString);
        } catch (IOException e) {
          Log.e(LOG_TAG, "Error getting response body string. " + e.getMessage());
        }
      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e(LOG_TAG, "Error fetching Users. " + t.getMessage());
      }
    });
  }
}
