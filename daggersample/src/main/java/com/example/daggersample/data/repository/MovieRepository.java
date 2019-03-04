package com.example.daggersample.data.repository;

import android.support.annotation.NonNull;

import com.example.daggersample.data.remote.api.MovieApiService;
import com.example.daggersample.data.NetworkBoundResource;
import com.example.daggersample.data.Resource;
import com.example.daggersample.data.local.MovieDao;
import com.example.daggersample.data.local.MovieEntity;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;

@Singleton
public class MovieRepository {
    private MovieDao movieDao;
    private MovieApiService movieApiService;

    public MovieRepository(MovieDao movieDao, MovieApiService movieApiService) {
        this.movieDao = movieDao;
        this.movieApiService = movieApiService;
    }

    public Observable<Resource<List<MovieEntity>>> loadMoviesByType() {
        return new NetworkBoundResource<List<MovieEntity>,List<MovieEntity>>() {
            @Override
            protected void saveCallResult(@NonNull List<MovieEntity> item) {
                movieDao.insertMovies(item);

            }

            @Override
            protected boolean shouldFetch() {
                return true;
            }

            @NonNull
            @Override
            protected Flowable<List<MovieEntity>> loadFromDb() {
                List<MovieEntity> movieEntities = movieDao.getMoviesByPage();
                if(movieEntities == null || movieEntities.isEmpty()) {
                    return Flowable.empty();
                }
                return Flowable.just(movieEntities);            }

            @NonNull
            @Override
            protected Observable<Resource<List<MovieEntity>>> createCall() {
                return movieApiService.fetchMovies()
                        .flatMap(movieEntityList -> Observable.just(movieEntityList == null
                        ?Resource.error("",null):Resource.success(movieEntityList)));
            }

        }.getAsObservable();
    }
}
