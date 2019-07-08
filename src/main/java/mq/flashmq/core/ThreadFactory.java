package mq.flashmq.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadFactory {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    private static ThreadFactory ourInstance = new ThreadFactory();

    private ThreadFactory() {
    }

    public void execute(Runnable runnable) {
        EXECUTOR_SERVICE.execute(runnable);
    }

    public static ThreadFactory getInstance() {
        return ourInstance;
    }
}
