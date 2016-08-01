package com.demos.RequestQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Mr_Wrong on 16/3/4.
 */
public class RequestQueue {

    private BlockingQueue<Resquestfor70kg> mRequestQueue;

    static RequestQueue instance;

    public static RequestQueue getInstance() {
        if (instance == null) {
            synchronized (RequestQueue.class) {
                if (instance == null) {
                    instance = new RequestQueue();
                }
            }

        }
        return instance;
    }

    public RequestQueue() {
        mRequestQueue = new PriorityBlockingQueue<>();
    }

    public void add(Resquestfor70kg resquestfor70kg) {
        mRequestQueue.add(resquestfor70kg);
    }

    public void start() {
        Executor executor = new Executor(mRequestQueue);
        executor.start();
    }

}
