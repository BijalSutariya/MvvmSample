package com.example.daggersample.ui.activity;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import timber.log.Timber;

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

        /* SnapHelper to change the background of the activity based on the list item
         * currently visible */

        moviesListAdapter = new MoviesListAdapter(this);
        binding.moviesList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.moviesList.setAdapter(moviesListAdapter);
       /* SnapHelper startSnapHelper = new PagerSnapHelper(position -> {
            MovieEntity movie = moviesListAdapter.getItem(position);
            binding.overlayLayout.updateCurrentBackground(movie.getPosterPath());
        });
        startSnapHelper.attachToRecyclerView(binding.moviesList);*/


        initialiseViewModel();
    }

    private void initialiseViewModel() {
        movieListViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel.class);

        movieListViewModel.getMoviesLiveData().observe(this,listResource -> {

                Log.d("TAG", "initialiseViewModel: "+listResource.data.toString());

              //  displayLoader();

        });
        movieListViewModel.loadMoreMovies();
       // Log.d("TAG", "initialiseViewModel: "+movieListViewModel.getMoviesLiveData().getValue());
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
           // holder.bindTo(getItem(position));
        }

        protected class CustomViewHolder extends RecyclerView.ViewHolder {

            private MoviesListItemBinding binding;

            public CustomViewHolder(MoviesListItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;

                DisplayMetrics displayMetrics = new DisplayMetrics();
                activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int width = displayMetrics.widthPixels;

                itemView.setLayoutParams(new RecyclerView.LayoutParams(new Float(width * 0.85f).intValue(),
                        RecyclerView.LayoutParams.WRAP_CONTENT));
            }


        }
    }

}
