package com.demos.asynchronous;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.ArrayDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mr_Wrong on 16/3/15.
 */
public abstract class AsyncTaskFor70kg<Params, Progress, Result> {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
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
    public static final Executor THREAD_POOL_EXECUTOR//真正执行耗时任务的线程池
            = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);


    private static volatile Executor defaultExecutor = new Executor() {//默认的串行线程池
        final ArrayDeque<Runnable> tasks = new ArrayDeque<Runnable>();//排队的任务队列
        Runnable activeRunnable;

        @Override
        public void execute(final Runnable r) {
            tasks.offer(new Runnable() {

                @Override
                public void run() {//添加到尾巴上
                    try {
                        r.run();
                    } finally {
                        scheduleNext();//执行完了才调度下一个
                    }
                }
            });

            if (activeRunnable == null) {//第一次为空
                scheduleNext();
            }
        }

        protected synchronized void scheduleNext() {
            if ((activeRunnable = tasks.poll()) != null) {//取出来一个
                THREAD_POOL_EXECUTOR.execute(activeRunnable);//执行
            }
        }
    };

    private static class TaskResult<Data> {//包装的执行结果
        final AsyncTaskFor70kg mTask;
        final Data[] mData;

        TaskResult(AsyncTaskFor70kg task, Data... data) {
            mTask = task;
            mData = data;
        }
    }

    private static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] mParams;
    }

    private final WorkerRunnable<Params, Result> workerRunnable;
    private final FutureTask<Result> futureTask;
    private static final int MESSAGE_POST_RESULT = 0x01;//结果
    private static final int MESSAGE_POST_PROGRESS = 0x2;//进度


    public AsyncTaskFor70kg() {
        workerRunnable = new WorkerRunnable<Params, Result>() {
            @Override
            public Result call() throws Exception {
                return postResult(doInBackground(mParams));//自己写的耗时任务  并且返回执行结果
            }
        };
        futureTask = new FutureTask<Result>(workerRunnable);
    }

    private static final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            TaskResult result = (TaskResult) msg.obj;
            switch (msg.what) {
                case MESSAGE_POST_RESULT:
                    result.mTask.finish(result.mData[0]);//调到主线程里面了
                    break;
                case MESSAGE_POST_PROGRESS:
                    result.mTask.onProgressUpdate(result.mData);//发布进度
                    break;
            }
        }
    };

    private Result postResult(Result result) {//包装一下result 发送给handler
        Message message = handler.obtainMessage(
                MESSAGE_POST_RESULT,
                new TaskResult<Result>(this, result));
        message.sendToTarget();
        return result;
    }

    private void finish(Result result) {//在主线程调用的
        onPostExecute(result);
    }

    public void publishProgress(Progress... Progress) {//发布进度
        handler.obtainMessage(MESSAGE_POST_PROGRESS, new TaskResult<Progress>(this, Progress)).sendToTarget();
    }

    public final AsyncTaskFor70kg<Params, Progress, Result> execute(Params... params) {
        return executeOnExecutor(defaultExecutor, params);//默认的串行
    }

    public final AsyncTaskFor70kg<Params, Progress, Result> executeOnExecutor(Executor executor, Params... params) {
        onPreExecute();
        workerRunnable.mParams = params;
        executor.execute(futureTask);
        return this;
    }

    //---重写的
    protected void onPreExecute() {
    }

    protected void onProgressUpdate(Progress... values) {

    }

    protected abstract Result doInBackground(Params... params);

    protected void onPostExecute(Result result) {
    }
}
