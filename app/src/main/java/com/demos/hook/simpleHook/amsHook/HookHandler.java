package com.demos.hook.simpleHook.amsHook;

import com.socks.library.KLog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by wangpeng on 16/7/11.
 */
public class HookHandler implements InvocationHandler {

    Object mBase;

    public HookHandler(Object mBase) {
        this.mBase = mBase;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        KLog.e(method.getName() + "--hook_ams--" + args);
        return method.invoke(mBase, args);
    }
}
