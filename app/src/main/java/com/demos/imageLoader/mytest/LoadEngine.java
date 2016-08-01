package com.demos.imageLoader.mytest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mr_Wrong on 16/3/23.
 */
public class LoadEngine {
    private static final BlockingQueue<Runnable> sPoolWorkQueue =//任务队列最大128
            new LinkedBlockingQueue<Runnable>(Integer.MAX_VALUE);
    private static final int CPU_COUNT = 4;//模拟CPU数
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;//核心线程数 5
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;//最大线程数 9
    private static final int KEEP_ALIVE = 1;//核心线程无超时  非核心线程闲置时超时时间1秒
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "70kg'Task #" + mCount.getAndIncrement());
        }
    };
    public final Executor taskExecutor
            = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);

    public void submit(Runnable task) {
        taskExecutor.execute(task);
    }
}
