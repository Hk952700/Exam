package com.huaxia.exam.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolUtil {
    private static ExecutorService instance = null;

    private ThreadPoolUtil() {
    }

    public static ExecutorService getInstance() {
        if (instance == null) instance = Executors.newCachedThreadPool();
        return instance;
    }
}
