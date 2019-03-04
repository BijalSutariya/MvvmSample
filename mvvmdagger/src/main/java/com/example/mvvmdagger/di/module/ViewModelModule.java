package com.example.mvvmdagger.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.mvvmdagger.ui.details.DetailsViewModel;
import com.example.mvvmdagger.ui.list.ListViewModel;
import com.example.mvvmdagger.di.util.ViewModelKey;
import com.example.mvvmdagger.utils.ViewModelFactory;


import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel.class)
    abstract ViewModel bindListViewModel(ListViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel.class)
    abstract ViewModel bindDetailsViewModel(DetailsViewModel listViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
