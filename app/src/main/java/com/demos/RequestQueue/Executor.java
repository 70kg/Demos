package com.demos.RequestQueue;

import com.socks.library.KLog;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Mr_Wrong on 16/3/4.
 */
public class Executor extends Thread {

    BlockingQueue<Resquestfor70kg> mQueue;
    Dispatcher mDispatcher;
    Resquestfor70kg request;

    public Executor(BlockingQueue<Resquestfor70kg> queue) {
        this.mQueue = queue;
        mDispatcher = new Dispatcher();
    }

    @Override
    public void run() {
        try {
            while (true) {
                request = mQueue.take();
                mDispatcher.dispatch(request);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
