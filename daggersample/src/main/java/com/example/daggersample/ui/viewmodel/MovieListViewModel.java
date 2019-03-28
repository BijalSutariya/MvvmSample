package com.example.daggersample.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.daggersample.data.Resource;
import com.example.daggersample.data.local.MovieEntity;
import com.example.daggersample.data.repository.MovieRepository;

import java.util.List;

import javax.inject.Inject;

public class MovieListViewModel extends ViewModel {
    /* We are using LiveData to update the UI with the data changes.
     */
    private LiveData<Resource<List<MovieEntity>>> moviesLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> movieIdLiveData;

    @Inject
    MovieRepository repository;

    /*
     * We are injecting the MovieDao class
     * and the MovieApiService class to the ViewModel.
     * */
    @Inject
    public MovieListViewModel(MovieRepository repository) {
        moviesLiveData = repository.loadUsers();
        movieIdLiveData = new MutableLiveData<>();
    }

    /*
     * LiveData observed by the UI
     * */
    public LiveData<Resource<List<MovieEntity>>> getMoviesLiveData() {
        return moviesLiveData;
    }

    public void loadDetails(int position) {
        movieIdLiveData.setValue(position);
    }

    public LiveData<Resource<MovieEntity>> fetchDetails() {
        return Transformations.switchMap(movieIdLiveData, newMovieId -> repository.loadOneUser(newMovieId));
    }
}
