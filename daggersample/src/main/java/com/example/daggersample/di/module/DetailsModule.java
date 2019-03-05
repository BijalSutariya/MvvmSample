package com.example.daggersample.di.module;

import com.example.daggersample.ui.fragments.DetailsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DetailsModule {
    @ContributesAndroidInjector
    abstract DetailsFragment contributesDetailsFragment();
}
