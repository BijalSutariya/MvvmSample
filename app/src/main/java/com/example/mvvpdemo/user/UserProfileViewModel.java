package com.example.mvvpdemo.user;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.mvvpdemo.model.User;

import java.util.List;

public class UserProfileViewModel extends AndroidViewModel {
    private LiveData<List<User>> user;

    public UserProfileViewModel(Application application) {
        super(application);
        user = UserRepository.getInstance().getUser(); //pass id
    }

    LiveData<List<User>> getUserDetails(){
        return user;
    }
}
