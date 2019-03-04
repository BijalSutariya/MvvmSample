package com.example.daggersample.data.remote.api;

import com.example.daggersample.data.local.MovieEntity;
import com.example.daggersample.data.remote.model.MovieApiResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MovieApiService {
    @GET("/todos")
    Observable<List<MovieEntity>> fetchMovies();
}
