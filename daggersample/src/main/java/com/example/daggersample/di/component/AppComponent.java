package com.example.daggersample.di.component;

import android.app.Application;

import com.example.daggersample.MyApplication;
import com.example.daggersample.di.module.ActivityModule;
import com.example.daggersample.di.module.ApiModule;
import com.example.daggersample.di.module.DbModule;
import com.example.daggersample.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {
        ApiModule.class,
        DbModule.class,
        ViewModelModule.class,
        ActivityModule.class,
        AndroidSupportInjectionModule.class})
@Singleton
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(MyApplication application);
}
