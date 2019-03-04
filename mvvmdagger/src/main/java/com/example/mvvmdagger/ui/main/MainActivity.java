package com.example.mvvmdagger.ui.main;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.example.mvvmdagger.R;
import com.example.mvvmdagger.base.BaseActivity;

public class MainActivity extends BaseActivity {


    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.screenContainer,new ListFragment()).commit();

        }
    }
}
