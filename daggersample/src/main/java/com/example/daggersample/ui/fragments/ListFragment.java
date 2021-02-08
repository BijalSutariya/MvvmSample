package com.example.daggersample.ui.fragments;


import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.daggersample.MyClickListener;
import com.example.daggersample.R;
import com.example.daggersample.data.local.MovieEntity;
import com.example.daggersample.databinding.FragmentListBinding;
import com.example.daggersample.databinding.MoviesListItemBinding;
import com.example.daggersample.factory.ViewModelFactory;
import com.example.daggersample.ui.viewmodel.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class ListFragment extends Fragment implements MyClickListener {

    @Inject
    ViewModelFactory factory;

    private View view;
    private MovieListViewModel movieListViewModel;
    private FragmentListBinding binding;
    private MoviesListAdapter moviesListAdapter;
    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
        movieListViewModel = ViewModelProviders.of(this, factory).get(MovieListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        view = binding.getRoot();

        moviesListAdapter = new MoviesListAdapter(getActivity(),this);
        binding.moviesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.moviesList.setAdapter(moviesListAdapter);

           initialiseViewModel();

        return view;
    }

    private void initialiseViewModel() {
        movieListViewModel.getMoviesLiveData().observe(this, listResource -> {

            if (listResource.isLoading()) {
                displayLoader();

            } else if (!listResource.data.isEmpty()) {
                updateMoviesList(listResource.data);

            } else handleErrorResponse();

        });
    }

    private void hideLoader() {
        binding.moviesList.setVisibility(View.VISIBLE);
        binding.loaderLayout.rootView.setVisibility(View.GONE);
    }
    private void displayLoader() {
        binding.moviesList.setVisibility(View.GONE);
        binding.loaderLayout.rootView.setVisibility(View.VISIBLE);
    }

    private void updateMoviesList(List<MovieEntity> movies) {
        hideLoader();
        binding.emptyLayout.emptyContainer.setVisibility(View.GONE);
        binding.moviesList.setVisibility(View.VISIBLE);
        moviesListAdapter.setItems(movies);
    }

    private void handleErrorResponse() {
        hideLoader();
        binding.moviesList.setVisibility(View.GONE);
        binding.emptyLayout.emptyContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMyClick(View view, int position) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.listFragment, DetailsFragment.newInstance(view,position)).addToBackStack(null);
        fragmentTransaction.commit();
    }

    public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.CustomViewHolder> {

        private MyClickListener listener;
        private Activity activity;
        private List<MovieEntity> movies;

        public MoviesListAdapter(Activity activity,MyClickListener listener) {
            this.activity = activity;
            this.listener = listener;
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
                binding.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onMyClick(view,getAdapterPosition());
                    }
                });
            }
        }
    }
}
