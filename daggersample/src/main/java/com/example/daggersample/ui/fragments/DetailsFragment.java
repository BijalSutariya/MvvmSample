package com.example.daggersample.ui.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.daggersample.R;
import com.example.daggersample.data.Resource;
import com.example.daggersample.data.local.MovieEntity;
import com.example.daggersample.databinding.DetailsFragmentBinding;
import com.example.daggersample.factory.ViewModelFactory;
import com.example.daggersample.ui.viewmodel.MovieListViewModel;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

public class DetailsFragment extends Fragment {

    @Inject
    ViewModelFactory factory;
    private View view;
    private DetailsFragmentBinding binding;
    private MovieListViewModel movieListViewModel;
    private int position;

    public static Fragment newInstance(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
        movieListViewModel = ViewModelProviders.of(this,factory).get(MovieListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_details,container,false);
        view = binding.getRoot();

        position = getArguments().getInt("position");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        movieListViewModel.fetchDetails().observe(this,this::consumeResponse);
        movieListViewModel.loadDetails(position+1);
    }

    private void consumeResponse(Resource resource) {
        switch (resource.getCurrentState()) {
            case LOADING: {

            }
            break;
            case SUCCESS: {
                /*
                 * Retrieve whatever data you expect from here with just one object.*/
          //      Movie movie = (Movie)resource.getData();
                //                Movie movie = (Movie)dataRequest.getData();
                MovieEntity entity = (MovieEntity) resource.getData();
                Timber.d("data entity %s",entity.getTitle());
                binding.tvDetails.setText(entity.getTitle().toString());
                //Glide.with(MovieDetailsFragment.this).load(movie.getPoster()).into(moviePosterImageView);


            }
            break;
            case ERROR: {

            }
            break;
        }
    }

}
