package com.demos.asynchronous;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mr_Wrong on 16/3/14.
 */
public class ThreadPool {
    //    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CPU_COUNT = 4;//模拟CPU数
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;//核心线程数 5
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;//最大线程数 9
    private static final int KEEP_ALIVE = 1;//核心线程无超时  非核心线程闲置时超时时间1秒
    private static final BlockingQueue<Runnable> sPoolWorkQueue =//任务队列最大128
            new LinkedBlockingQueue<Runnable>(128);
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "70kg'Task #" + mCount.getAndIncrement());
        }
    };
    public static final Executor THREAD_POOL_EXECUTOR
            = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);

    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            executor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println(Thread.currentThread().getName());
//                }
//            });
//        }
        ThreadPool pool = new ThreadPool();
        pool.test();

    }

    private static volatile Executor defaultExecutor = new Executor() {
        final ArrayDeque<Runnable> tasks = new ArrayDeque<Runnable>();
        Runnable activeRunnable;

        @Override
        public void execute(final Runnable r) {
            tasks.offer(new Runnable() {

                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName());
                        r.run();
                    } finally {
                        System.out.println("调度");
                        scheduleNext();
                    }
                }
            });

            if (activeRunnable == null) {
                System.out.println("null");
                scheduleNext();
            }
        }

        protected synchronized void scheduleNext() {
            if ((activeRunnable = tasks.poll()) != null) {
                THREAD_POOL_EXECUTOR.execute(activeRunnable);
            }
        }
    };

    private void test() {
        List<FutureTask<String>> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println("calling");
                    Thread.sleep(1000);
                    return "hello";
                }
            }) {
                @Override
                protected void done() {
                    try {
                        System.out.println("done " + get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            };
            list.add(task);
        }
        for (FutureTask<String> task : list)
            defaultExecutor.execute(task);
    }
}
