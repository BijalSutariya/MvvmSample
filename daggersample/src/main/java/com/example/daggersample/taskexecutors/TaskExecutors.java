package com.example.daggersample.taskexecutors;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

import javax.inject.Inject;

public class TaskExecutors {
    private Executor diskOperationThread;
    private Executor networkOperationThread;
    private Executor mainThread;

    @Inject
    public TaskExecutors(Executor diskOperationThread, Executor networkOperationThread, Executor mainThread) {
        this.diskOperationThread = diskOperationThread;
        this.networkOperationThread = networkOperationThread;
        this.mainThread = mainThread;
    }

    public Executor getDiskOperationThread() {
        return diskOperationThread;
    }

    public Executor getNetworkOperationThread() {
        return networkOperationThread;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    private class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }
}
