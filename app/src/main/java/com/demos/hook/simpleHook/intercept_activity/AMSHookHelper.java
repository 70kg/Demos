package com.demos.hook.simpleHook.intercept_activity;

import android.os.Handler;

import com.demos.hook.simpleHook.classLoad_hook.IPackageManagerHookHandler;
import com.socks.library.KLog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by wangpeng on 16/7/13.
 * 这个类主要实现了启动不在mainlifast中声明的activity，但是还是在一个apk文件里面
 */
public class AMSHookHelper {
    public static final String RAW_INTENT = "rawIntent";

    /**
     * 在APP进程到AMS进程时进行activity替换
     *
     * @throws Exception
     */
    public static void hookActivityManagerNative() throws Exception {
        Class<?> activityManagerNative = Class.forName("android.app.ActivityManagerNative");
        Field gDefault = activityManagerNative.getDeclaredField("gDefault");
        gDefault.setAccessible(true);
        Object singleton = gDefault.get(null);


        Class<?> singletonClazz = Class.forName("android.util.Singleton");
        Field mInstanceField = singletonClazz.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);
        Object rawIActivityManager = mInstanceField.get(singleton);

        Object proxyIActivityManager = Proxy.newProxyInstance(rawIActivityManager.getClass().getClassLoader(),
                new Class[]{Class.forName("android.app.IActivityManager")}, new IActivityManagerHandler(rawIActivityManager));

        mInstanceField.set(singleton, proxyIActivityManager);
    }

    /**
     * 从AMS进程回到APP进程进行activity的恢复
     *
     * @throws Exception
     */
    public static void hookActivityThreadHandler() throws Exception {
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");

        Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");

        currentActivityThreadField.setAccessible(true);
        //获取ActivityThread类中的ActivityThread实例sCurrentActivityThread
        Object currentActivityThread = currentActivityThreadField.get(null);

        Field mHField = activityThreadClass.getDeclaredField("mH");
        mHField.setAccessible(true);
        Handler mH = (Handler) mHField.get(currentActivityThread);

        Field mCallBackField = Handler.class.getDeclaredField("mCallback");
        mCallBackField.setAccessible(true);
        mCallBackField.set(mH, new ActivityThreadHandlerCallback(mH));
    }

}
