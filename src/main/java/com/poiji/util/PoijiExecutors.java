package com.poiji.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class PoijiExecutors {

    private PoijiExecutors() {
    }

    public static ExecutorService newExecutor() {
        return Executors.newCachedThreadPool();
    }
}
