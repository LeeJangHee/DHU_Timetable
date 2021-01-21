package com.example.dhu_timetable.network;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 백그라운드 스레드 설정 부분
 */
public class AppExecutors {
    private static AppExecutors instance;

    public static AppExecutors getInstance() {
        if (instance == null) {
            instance = new AppExecutors();
        }
        return instance;
    }

    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(4);

    public ScheduledExecutorService networkIO() {
        return mNetworkIO;
    }
}
