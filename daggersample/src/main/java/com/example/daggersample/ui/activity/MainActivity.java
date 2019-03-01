package com.example.daggersample.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.daggersample.databinding.MainActivityBinding;
import com.example.daggersample.factory.ViewModelFactory;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    private MainActivityBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
      //  initialiseViewModel();
    }

}
