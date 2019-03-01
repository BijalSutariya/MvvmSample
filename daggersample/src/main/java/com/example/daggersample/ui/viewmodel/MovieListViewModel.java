package com.example.daggersample.ui.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.daggersample.data.remote.api.MovieApiService;
import com.example.daggersample.data.Resource;
import com.example.daggersample.data.local.MovieDao;
import com.example.daggersample.data.local.MovieEntity;
import com.example.daggersample.data.repository.MovieRepository;

import java.util.List;

import javax.inject.Inject;

public class MovieListViewModel extends ViewModel {
    private MovieRepository movieRepository;
    /* We are using LiveData to update the UI with the data changes.
     */
    private MutableLiveData<Resource<List<MovieEntity>>> moviesLiveData = new MutableLiveData<>();

    /*
     * We are injecting the MovieDao class
     * and the MovieApiService class to the ViewModel.
     * */
    @Inject
    public MovieListViewModel(MovieDao movieDao, MovieApiService movieApiService) {
        /* You can see we are initialising the MovieRepository class here */
        movieRepository = new MovieRepository(movieDao, movieApiService);
    }


    /*
     * Method called by UI to fetch movies list
     * */
    public void loadMoreMovies() {
        movieRepository.loadMoviesByType()
                .subscribe(resource -> getMoviesLiveData().postValue(resource));
    }

    /*
     * LiveData observed by the UI
     * */
    public MutableLiveData<Resource<List<MovieEntity>>> getMoviesLiveData() {
        return moviesLiveData;
    }


}
