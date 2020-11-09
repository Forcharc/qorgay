package kz.kmg.qorgau.domain.services.executor;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ExecutorHelper {

    public static void IOThread(Runnable t) {
        Executor IO_EXECUTOR = Executors.newSingleThreadExecutor();
        IO_EXECUTOR.execute(t);
    }

    public static void FixedThreadThread(Runnable t, int threadN) {
        Executor IO_EXECUTOR = Executors.newFixedThreadPool(threadN);
        IO_EXECUTOR.execute(t);
    }

    public static void MainThread(Runnable t) {
        Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        mainThreadHandler.post(t);
    }

}