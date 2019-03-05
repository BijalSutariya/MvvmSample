package com.example.daggersample.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertMovies(List<MovieEntity> movies);

    @Query("SELECT * FROM `MovieEntity`")
    LiveData<List<MovieEntity>> getMoviesByPage();

    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    LiveData<MovieEntity> getMoviesDetails(int id);
}
