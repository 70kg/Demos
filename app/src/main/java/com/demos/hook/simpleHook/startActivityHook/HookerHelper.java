package com.demos.hook.simpleHook.startActivityHook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by wangpeng on 16/7/10.
 */
public class HookerHelper {
    public static void attachContext(Context newBase) throws Exception {
        // 先获取到当前的ActivityThread对象
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread", false, newBase.getClassLoader());
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);
        // 拿到原始的 mInstrumentation字段
        Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
        mInstrumentationField.setAccessible(true);
        Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);
        // 创建代理对象
        Instrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);
        // 偷梁换柱
        mInstrumentationField.set(currentActivityThread, evilInstrumentation);

    }

    public static void hookActivity(Activity activity) throws Exception {
        Class<?> currentActivity = Class.forName("android.app.Activity");
        Field instrumentationField = currentActivity.getDeclaredField("mInstrumentation");
        instrumentationField.setAccessible(true);
        Instrumentation mInstrumentation = (Instrumentation) instrumentationField.get(activity);
        Instrumentation customInstrumentation = new EvilInstrumentation(mInstrumentation);
        instrumentationField.set(activity, customInstrumentation);
    }

}
