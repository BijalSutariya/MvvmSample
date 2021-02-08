package com.example.daggersample.data.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.daggersample.data.NetworkBoundResource;
import com.example.daggersample.data.Resource;
import com.example.daggersample.data.local.MovieDao;
import com.example.daggersample.data.local.MovieEntity;
import com.example.daggersample.data.remote.api.MovieApiService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;

@Singleton
public class MovieRepository {
    private final MovieDao movieDao;
    private final MovieApiService movieApiService;

    @Inject
    public MovieRepository(MovieDao movieDao, MovieApiService movieApiService) {
        this.movieDao = movieDao;
        this.movieApiService = movieApiService;
    }

    public LiveData<Resource<List<MovieEntity>>> loadUsers() {
        return new NetworkBoundResource<List<MovieEntity>, List<MovieEntity>>() {

            @Override
            protected void saveCallResult(List<MovieEntity> item) {
                if(item!=null)
                    movieDao.insertMovies(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<MovieEntity> data) {
                return data == null || data.isEmpty() ;

            }

            @NonNull
            @Override
            protected LiveData<List<MovieEntity>> loadFromDb() {
                return movieDao.getMoviesByPage();
            }

            @NonNull
            @Override
            protected Call<List<MovieEntity>> createCall() {
                return movieApiService.fetchMovies();
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<MovieEntity>> loadOneUser(int id) {
        return new NetworkBoundResource<MovieEntity , MovieEntity>(){

            @Override
            protected void saveCallResult(MovieEntity item) {

            }

            @Override
            protected boolean shouldFetch(MovieEntity data) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<MovieEntity> loadFromDb() {
                return movieDao.getMoviesDetails(id);
            }

            @NonNull
            @Override
            protected Call<MovieEntity> createCall() {
                return null;
            }
        }.getAsLiveData();
    }
}
