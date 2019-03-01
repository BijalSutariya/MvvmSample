package com.example.daggersample.data.remote.api;

import com.example.daggersample.data.remote.model.MovieApiResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MovieApiService {
    @GET("movie/popular?language=en-US&region=US&page=1")
    Observable<MovieApiResponse> fetchMovies();
}
