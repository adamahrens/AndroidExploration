package com.appsbyahrens.retrofit;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
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

  public void affixUser(String user) {
    RequestBody request = RequestBody.create(MediaType.parse("application/json"), user);
    api.postUser(request).enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Log.d(LOG_TAG, "Success POST User");
      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e(LOG_TAG, "Error posting User. " + t.getMessage());
      }
    });
  }

  public void affixPost(String post) {
    RequestBody request = RequestBody.create(MediaType.parse("application/json"), post);
    api.postPost(request).enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Log.d(LOG_TAG, "Success POST Post");
      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e(LOG_TAG, "Error posting POST. " + t.getMessage());
      }
    });
  }

  public void fetchComments(int postId) {
    api.getComments(postId).enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Log.d(LOG_TAG, "Success GET Comments for Post");

        try {
          String responseString = response.body().string();
          Log.d(LOG_TAG, "Comments = " + responseString);
        } catch (IOException e) {
          Log.e(LOG_TAG, "Error getting response body string. " + e.getMessage());
        }
      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e(LOG_TAG, "Error fetching Comments for Post. " + t.getMessage());
      }
    });
  }
  public void fetchPosts(int userId) {
    api.getPosts(userId).enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Log.d(LOG_TAG, "Success GET Posts for User");

        try {
          String responseString = response.body().string();
          Log.d(LOG_TAG, "Posts = " + responseString);
        } catch (IOException e) {
          Log.e(LOG_TAG, "Error getting response body string. " + e.getMessage());
        }
      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e(LOG_TAG, "Error fetching Posts for User. " + t.getMessage());
      }
    });
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

  public void fetchTodo(int id) {
    api.getTodo(id).enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Log.d(LOG_TAG, "Success getTodo:");

        try {
          String responseString = response.body().string();
          Log.d(LOG_TAG, "Todo = " + responseString);
        } catch (IOException e) {
          Log.e(LOG_TAG, "Error getting response body string. " + e.getMessage());
        }
      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e(LOG_TAG, "Error fetching Todo. " + t.getMessage());
      }
    });
  }
}
