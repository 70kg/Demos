package com.demos.hook.simpleHook.binderHook;

import android.os.IBinder;
import android.os.IInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by wangpeng on 16/7/11.
 * 这里去hook Iinterface 主要是关注asinterface
 */
public class BinderHookHandler implements InvocationHandler {

    IInterface iInterface;//要被代理的IInterface  就是Stub类asInterface的返回值  一般是远程service的代理

    public BinderHookHandler(IBinder binder, Class<?> subClazz) {
        try {
            Method asInterfaceMethod = subClazz.getDeclaredMethod("asInterface", IBinder.class);
            this.iInterface = (IInterface) asInterfaceMethod.invoke(null, binder);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("hasPrimaryClip")) {//伪造剪切板一直有内容
            return true;
        }
        return method.invoke(iInterface, args);
    }
}
