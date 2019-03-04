package com.example.mvvmdagger.data.rest;

import android.arch.lifecycle.LiveData;

import com.example.mvvmdagger.data.model.Repo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class RepoRepository {
    private final RepoService repoService;

    public RepoRepository(RepoService repoService) {
        this.repoService = repoService;
    }

    public Single<List<Repo>> getRepositories(){
        return repoService.getRepositories();
    }

    /*public Single<Repo> getRepo(String owner, String name){
        return repoService.getRepo(owner,name);
    }*/


}
