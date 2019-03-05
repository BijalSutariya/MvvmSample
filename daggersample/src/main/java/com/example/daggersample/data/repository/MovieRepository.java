package com.example.daggersample.data.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.daggersample.data.remote.api.MovieApiService;
import com.example.daggersample.data.NetworkBoundResource;
import com.example.daggersample.data.Resource;
import com.example.daggersample.data.local.MovieDao;
import com.example.daggersample.data.local.MovieEntity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.Call;

@Singleton
public class MovieRepository {
    private MovieDao movieDao;
    private MovieApiService movieApiService;

    @Inject
    public MovieRepository(MovieDao movieDao, MovieApiService movieApiService) {
        this.movieDao = movieDao;
        this.movieApiService = movieApiService;
    }

    public LiveData<Resource<List<MovieEntity>>> loadMoviesByType() {
        return new NetworkBoundResource<List<MovieEntity>, List<MovieEntity>>() {
            @Override
            protected void saveCallResult(List<MovieEntity> item) {
                movieDao.insertMovies(item);
            }

            @NonNull
            @Override
            protected LiveData<List<MovieEntity>> loadFromDb() {
                return movieDao.getMoviesByPage();
            }

            @Override
            protected boolean shouldFetch(List<MovieEntity> data) {
                return data == null || data.isEmpty() ;
            }

            @NonNull
            @Override
            protected Call<List<MovieEntity>> createCall() {
                return movieApiService.fetchMovies();
            }
        }.getAsLiveData();

    }

    public LiveData<Resource<MovieEntity>> loadOneUser(Integer newMovieId) {
        return new NetworkBoundResource<MovieEntity, MovieEntity>() {
            @Override
            protected void saveCallResult(MovieEntity item) {

            }

            @NonNull
            @Override
            protected LiveData<MovieEntity> loadFromDb() {
                return movieDao.getMoviesDetails(newMovieId);
            }

            @Override
            protected boolean shouldFetch(MovieEntity data) {
                return false;
            }

            @NonNull
            @Override
            protected Call<MovieEntity> createCall() {
                return null;
            }
        }.getAsLiveData();
    }
}
