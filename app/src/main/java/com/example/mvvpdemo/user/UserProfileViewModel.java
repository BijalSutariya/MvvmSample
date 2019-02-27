package com.example.mvvpdemo.user;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.mvvpdemo.model.User;

public class UserProfileViewModel extends AndroidViewModel {
    private LiveData<User> user;

    public UserProfileViewModel(Application application) {
        super(application);
        user = UserRepository.getInstance().getUser(10); //pass id
    }

    public LiveData<User> getUserDetails(){
        return user;
    }
}
