package com.example.mvvmdagger.di.module;

import com.example.mvvmdagger.ui.main.MainActivity;
import com.example.mvvmdagger.ui.main.MainFragmentBindingModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = {MainFragmentBindingModule.class})
    abstract MainActivity bindMainActivity();
}
