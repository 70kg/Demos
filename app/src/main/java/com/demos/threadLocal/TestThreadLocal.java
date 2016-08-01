package com.demos.threadLocal;

/**
 * Created by Mr_Wrong on 16/3/14.
 */
public class TestThreadLocal {
    private static ThreadLocal<Boolean> mThreadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        mThreadLocal.set(true);
        System.out.println("主线程--" + mThreadLocal.get());
        new Thread(new Runnable() {
            @Override
            public void run() {
                mThreadLocal.set(false);
                System.out.println("线程1--" + mThreadLocal.get());
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程2--" + mThreadLocal.get());
            }
        }).start();
    }
}
