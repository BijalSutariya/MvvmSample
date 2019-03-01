package com.example.daggersample;

import android.app.Activity;
import android.app.Application;


import com.example.daggersample.di.component.AppComponent;

import javax.inject.Inject;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

public class MyApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());


       /* DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);*/
    }
}