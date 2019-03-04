package com.example.mvvmdagger.ui.list;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvvmdagger.R;
import com.example.mvvmdagger.base.BaseFragment;
import com.example.mvvmdagger.utils.ViewModelFactory;

import javax.inject.Inject;

public class ListFragment extends BaseFragment {

    @Inject
    ViewModelFactory viewModelFactory;
    private ListViewModel viewModel;


    @Override
    protected int layoutRes() {
        return R.layout.screen_list;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getBaseActivity(),viewModelFactory).get(ListViewModel.class);

        Log.d("TAG", "onViewCreated: "+viewModel.getRepos());
        //observableViewModel();
    }

    private void observableViewModel() {
        viewModel.getRepos().observe(getBaseActivity(),repos ->
                Log.d("TAG", "observableViewModel: "+repos.toString()));
    }
}
