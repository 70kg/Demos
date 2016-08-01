package com.demos.hook.simpleHook.amsHook;

import android.app.ActivityManager;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * Created by wangpeng on 16/7/11.
 */
public class HookHelper {
    /**
     * hook AMS  在ActivityManagerNative中有静态常量 Singleton<IActivityManager> gDefault 缓存了IActivityManager这个IInterface
     * 这里是hook 从app进程到AMS进程的过程
     * @throws Exception
     */
    public static void hookAMS() throws Exception {
        Class<?> activityManagerNative = Class.forName("android.app.ActivityManagerNative");
        Field gDefaultField = activityManagerNative.getDeclaredField("gDefault");
        gDefaultField.setAccessible(true);

        Object gDefault = gDefaultField.get(null);

        Class<?> singleton = Class.forName("android.util.Singleton");
        Field mInstanceField = singleton.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);
        Object rawIActivityManager = mInstanceField.get(gDefault);

        Object proxyIActivityManager = Proxy.newProxyInstance(rawIActivityManager.getClass().getClassLoader(),
                new Class[]{Class.forName("android.app.IActivityManager")}, new HookHandler(rawIActivityManager));

        mInstanceField.set(gDefault, proxyIActivityManager);

    }
}
