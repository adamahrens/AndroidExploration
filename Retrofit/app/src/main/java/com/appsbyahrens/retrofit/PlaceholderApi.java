package com.appsbyahrens.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PlaceholderApi {
  @GET("/users")
  Call<ResponseBody> getUsers();
}
