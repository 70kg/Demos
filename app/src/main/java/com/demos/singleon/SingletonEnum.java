package com.demos.singleon;

import com.socks.library.KLog;

/**
 * Created by Mr_Wrong on 16/3/27.
 */
public enum SingletonEnum {
    INSTANCE;

    public void doSomeThings() {
        KLog.e("初始化了");
    }
}
