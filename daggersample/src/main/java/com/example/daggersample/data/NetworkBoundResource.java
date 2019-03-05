package com.example.daggersample.data;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class NetworkBoundResource<T, V> {
    private final MediatorLiveData<Resource<T>> result = new MediatorLiveData<>();

    @MainThread
    protected NetworkBoundResource() {
        result.setValue(Resource.loading(null));
        LiveData<T> dbSource = loadFromDb();
        result.addSource(dbSource,movieData->{result.removeSource(dbSource);
        if (shouldFetch(movieData)) {
            fetchFromNetwork(dbSource);
        } else {
            result.addSource(dbSource, newData -> {
                if(null != newData)
                    result.setValue(Resource.success(newData)) ;
            });
        }
        });
    }

    private void fetchFromNetwork(LiveData<T> dbSource){
        result.addSource(dbSource, newMovie->result.setValue(Resource.loading(newMovie)));
        createCall().enqueue(new Callback<V>() {
            @Override
            public void onResponse(Call<V> call, Response<V> response) {
                result.removeSource(dbSource);
                saveResultAndReInit(response.body());
            }

            @Override
            public void onFailure(Call<V> call, Throwable t) {
                result.removeSource(dbSource);
                result.addSource(dbSource, newData -> result.setValue(Resource.error(t.getMessage(), newData)));            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    @MainThread
    private void saveResultAndReInit(V response) {
        new AsyncTask<Void, Void, Void>() {

            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... voids) {
                saveCallResult(response);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                result.addSource(loadFromDb(), newData -> {
                    if (null != newData)
                        result.setValue(Resource.success(newData));
                });
            }
        }.execute();
    }

    @WorkerThread
    protected abstract void saveCallResult(V item);

    @NonNull
    @MainThread
    protected abstract LiveData<T> loadFromDb();

    protected abstract boolean shouldFetch(T data);

    @NonNull
    @MainThread
    protected abstract Call<V> createCall();

    public final LiveData<Resource<T>> getAsLiveData() {
        return result;
    }
}