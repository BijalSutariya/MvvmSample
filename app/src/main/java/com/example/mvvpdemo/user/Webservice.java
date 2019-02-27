package com.example.mvvpdemo.user;

import com.example.mvvpdemo.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Webservice {

    String BASE_URL = "https://jsonplaceholder.typicode.com";

    @GET("/todos/{id}")
    Call<User> getUser(@Path("id") int userId);
}
