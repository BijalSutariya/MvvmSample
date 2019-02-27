package com.example.mvvpdemo.user;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.mvvpdemo.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {
    private static UserRepository userRepository;
    private Webservice webservice;

    private UserRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Webservice.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        webservice = retrofit.create(Webservice.class);
    }

    public synchronized static UserRepository getInstance() {
        //TODO No need to implement this singleton in Part #2 since Dagger will handle it ...
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

        public LiveData<User> getUser(int userId){
            final MutableLiveData<User> data = new MutableLiveData<>();
            webservice.getUser(userId).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    data.setValue(response.body());
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
            return data;
        }
    }
