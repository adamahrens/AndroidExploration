package com.appsbyahrens.retrofit;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PlaceholderApi {
  @GET("/users")
  Call<ResponseBody> getUsers();

  @POST("/users")
  Call<ResponseBody> postUser(@Body RequestBody body);

  @GET("/todos/{id}")
  Call<ResponseBody> getTodo(@Path("id") int todoId);

  @POST("/posts")
  Call<ResponseBody> postPost(@Body RequestBody body);

  @GET("/posts")
  Call<ResponseBody> getPosts(@Query("userId") int userId);

  @GET("/comments")
  Call<ResponseBody> getComments(@Query("postId") int postId);
}
