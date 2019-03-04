package com.example.daggersample.ui.activity;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.daggersample.R;
import com.example.daggersample.data.local.MovieEntity;
import com.example.daggersample.databinding.MainActivityBinding;
import com.example.daggersample.databinding.MoviesListItemBinding;
import com.example.daggersample.factory.ViewModelFactory;
import com.example.daggersample.ui.viewmodel.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    private MovieListViewModel movieListViewModel;


    private MainActivityBinding binding;

    private MoviesListAdapter moviesListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        moviesListAdapter = new MoviesListAdapter(this);
        binding.moviesList.setLayoutManager(new LinearLayoutManager(this));
        binding.moviesList.setAdapter(moviesListAdapter);

        initialiseViewModel();
    }

    private void initialiseViewModel() {
        movieListViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel.class);

        movieListViewModel.getMoviesLiveData().observe(this, listResource -> {

            if (listResource.isLoading()) {
                displayLoader();

            } else if (!listResource.data.isEmpty()) {
                updateMoviesList(listResource.data);

            } else handleErrorResponse();

        });
        movieListViewModel.loadMoreMovies();
    }

    private void hideLoader() {
        binding.moviesList.setVisibility(View.VISIBLE);
        binding.loaderLayout.rootView.setVisibility(View.GONE);
    }

    private void handleErrorResponse() {
        hideLoader();
        binding.moviesList.setVisibility(View.GONE);
        binding.emptyLayout.emptyContainer.setVisibility(View.VISIBLE);
    }

    private void updateMoviesList(List<MovieEntity> movies) {
        hideLoader();
        binding.emptyLayout.emptyContainer.setVisibility(View.GONE);
        binding.moviesList.setVisibility(View.VISIBLE);
        moviesListAdapter.setItems(movies);
    }

    private void displayLoader() {
        binding.moviesList.setVisibility(View.GONE);
        binding.loaderLayout.rootView.setVisibility(View.VISIBLE);
    }


    public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.CustomViewHolder> {

        private Activity activity;
        private List<MovieEntity> movies;

        public MoviesListAdapter(Activity activity) {
            this.activity = activity;
            this.movies = new ArrayList<>();
        }

        @NonNull
        @Override
        public MoviesListAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            MoviesListItemBinding itemBinding = MoviesListItemBinding.inflate(layoutInflater, parent, false);
            CustomViewHolder viewHolder = new CustomViewHolder(itemBinding);
            return viewHolder;
        }

        public void setItems(List<MovieEntity> movies) {
            int startPosition = this.movies.size();
            this.movies.addAll(movies);
            notifyItemRangeChanged(startPosition, movies.size());
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }

        public MovieEntity getItem(int position) {
            return movies.get(position);
        }

        @Override
        public void onBindViewHolder(@NonNull MoviesListAdapter.CustomViewHolder holder, int position) {
              holder.bindTo(getItem(position));
        }

        protected class CustomViewHolder extends RecyclerView.ViewHolder {

            private MoviesListItemBinding binding;

            public CustomViewHolder(MoviesListItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bindTo(MovieEntity movie) {
                binding.image.setText(movie.getTitle());
            }
        }
    }

}
