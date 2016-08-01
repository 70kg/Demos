package com.demos.imageLoader.core;

import com.demos.imageLoader.mytest.LoadEngine;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mr_Wrong on 16/3/21.
 * 图片请求队列
 */
public class BitmapRequestQueue {
    private BlockingQueue<BitmapRequest> mRequestQueue = new PriorityBlockingQueue<BitmapRequest>();
    private AtomicInteger mSerialNumGenerator = new AtomicInteger(0);
    public static int DEFAULT_CORE_NUMS = Runtime.getRuntime().availableProcessors() + 1;

    private int mDispatcherNums = DEFAULT_CORE_NUMS;
    /**
     * NetworkExecutor,执行网络请求的线程
     */
    private RequestDispatcher[] mDispatchers = null;

    protected BitmapRequestQueue() {
        this(DEFAULT_CORE_NUMS);
    }

    public BitmapRequestQueue(int threadCount) {
        mDispatcherNums = threadCount;
    }



    LoadEngine mLoadEngine;

    private final void startDispatchers() {
//        mDispatchers = new RequestDispatcher[mDispatcherNums];
//        for (int i = 0; i < mDispatcherNums; i++) {
//            mDispatchers[i] = new RequestDispatcher(mRequestQueue);
//            mDispatchers[i].start();
//        }
        mLoadEngine = new LoadEngine();
    }

    public void addRequest(BitmapRequest request) {
//        if (!mRequestQueue.contains(request)) {
//            request.serialNum = this.generateSerialNumber();
//            mRequestQueue.add(request);
//        }
        mLoadEngine.submit(request);
    }

    /**
     * 为每个请求生成一个系列号
     *
     * @return 序列号
     */
    private int generateSerialNumber() {
        return mSerialNumGenerator.incrementAndGet();
    }

    public void stop() {
        if (mDispatchers != null && mDispatchers.length > 0) {
            for (int i = 0; i < mDispatchers.length; i++) {
                mDispatchers[i].interrupt();
            }
        }
    }

    public void start() {
        stop();
        startDispatchers();
    }

    public void clear() {
        mRequestQueue.clear();
    }
}
