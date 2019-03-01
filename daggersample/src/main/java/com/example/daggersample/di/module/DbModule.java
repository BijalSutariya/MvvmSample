package com.example.daggersample.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.daggersample.data.local.AppDatabase;
import com.example.daggersample.data.local.MovieDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.Nullable;

@Module
public class DbModule {
    @Provides
    @Singleton
    AppDatabase provideDatabase(@Nullable Application application){
        return Room.databaseBuilder(application,
                AppDatabase.class,"Entertainment.db")
                .allowMainThreadQueries().build();
    }

    @Provides
    @Singleton
    MovieDao provideMovieDao(AppDatabase appDatabase){
        return appDatabase.movieDao();
    }
}
