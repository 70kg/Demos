package com.demos.hook.simpleHook.binderHook;

import android.os.IBinder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by wangpeng on 16/7/11.
 * 这里去hook IBinder.queryLocalInterface 一直走第一个if  返回伪造的Iintercace
 */
public class BinderProxyHandler implements InvocationHandler {
    IBinder binder;//原始的bindeer 要被代理掉
    Class<?> stub;
    Class<?> iinterface;

    public BinderProxyHandler(IBinder binder) {
        this.binder = binder;
        try {
            this.stub = Class.forName("android.content.IClipboard$Stub");
            this.iinterface = Class.forName("android.content.IClipboard");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.getName().equals("queryLocalInterface")) {
            //本来应该返回相同进程里面的本地IBinder 现在伪造返回代理过的IBinder
            return Proxy.newProxyInstance(proxy.getClass().getClassLoader(), new Class[]{this.iinterface},
                    new BinderHookHandler(binder, stub));
        }

        return method.invoke(binder, args);
    }
}
