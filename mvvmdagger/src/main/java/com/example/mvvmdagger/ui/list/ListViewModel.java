package com.example.mvvmdagger.ui.list;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.mvvmdagger.data.model.Repo;
import com.example.mvvmdagger.data.rest.RepoRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {
    private RepoRepository repoRepository;

    private CompositeDisposable disposable;
    private LiveData<List<Repo>> repoData;

    private final MutableLiveData<List<Repo>> repos = new MutableLiveData<>();

    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public ListViewModel(RepoRepository repoRepository) {
        this.repoRepository = repoRepository;
        disposable = new CompositeDisposable();
        fetchRepos();
    }

    LiveData<List<Repo>> getRepos() {
        return repos;
    }

    LiveData<Boolean> getError() {
        return repoLoadError;
    }
    LiveData<Boolean> getLoading() {
        return loading;
    }

    private void fetchRepos() {
        loading.setValue(true);
        disposable.add(repoRepository.getRepositories().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Repo>>(){

                    @Override
                    public void onSuccess(List<Repo> value) {
                        repoLoadError.setValue(false);
                        repos.setValue(value);
                        Log.d("TAG", "onSuccess: "+value.toString());
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        repoLoadError.setValue(true);
                        loading.setValue(false);
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable!=null){
            disposable.clear();
            disposable = null;
        }
    }
}
