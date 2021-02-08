package com.example.daggersample.data.remote.api;

import com.example.daggersample.data.local.MovieEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieApiService {
    @GET("/todos")
    Call<List<MovieEntity>> fetchMovies();

    @GET("/todos/{id}")
    Observable<MovieEntity> fetchDetails(@Path("id") int userId);
}
