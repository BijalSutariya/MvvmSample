package com.example.daggersample.di.module;

import com.example.daggersample.ui.fragments.ListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract ListFragment contributeListFragment();
}
