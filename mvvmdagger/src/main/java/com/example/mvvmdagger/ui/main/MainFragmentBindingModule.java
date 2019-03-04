package com.example.mvvmdagger.ui.main;

import android.support.v4.app.ListFragment;

import com.example.mvvmdagger.ui.details.DetailsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBindingModule {
    @ContributesAndroidInjector
    abstract ListFragment provideListFragment();

    @ContributesAndroidInjector
    abstract DetailsFragment provideDetailsFragment();
}
