package com.example.mvvmdagger.di.component;

import android.app.Application;

import com.example.mvvmdagger.base.BaseApplication;
import com.example.mvvmdagger.di.module.ActivityBindingModule;
import com.example.mvvmdagger.di.module.ApplicationModule;
import com.example.mvvmdagger.di.module.ContextModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        AndroidSupportInjectionModule.class,
        ActivityBindingModule.class})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    void inject(BaseApplication application);

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);
        ApplicationComponent build();
    }
}
