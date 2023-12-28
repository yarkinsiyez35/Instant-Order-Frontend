package com.example.instantorder.ExecService;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecServiceApp extends Application {
    public ExecutorService executorService = Executors.newCachedThreadPool();
}
