package com.demos.imageLoader.mytest;

import com.socks.library.KLog;

/**
 * Created by Mr_Wrong on 16/3/23.
 */
public class TestTask implements Runnable {
    @Override
    public void run() {
        KLog.e("请求网络等耗时间操作--" + Thread.currentThread().getName());
    }
}
