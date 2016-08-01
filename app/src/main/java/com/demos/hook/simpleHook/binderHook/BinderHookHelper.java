package com.demos.hook.simpleHook.binderHook;

import android.os.IBinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * Created by wangpeng on 16/7/11.
 */
public class BinderHookHelper {
    static final String CLIPBOARD_SERVICE = "clipboard";

    public static void hookClipboardService() throws Exception {
        Class<?> serviceManager = Class.forName("android.os.ServiceManager");
        Method getService = serviceManager.getDeclaredMethod("getService", String.class);
        IBinder binder = (IBinder) getService.invoke(serviceManager, "CLIPBOARD_SERVICE");
        //代理成自己的binder

        IBinder hookBinder = (IBinder) Proxy.newProxyInstance(binder.getClass().getClassLoader(), new Class[]{IBinder.class},
                new BinderProxyHandler(binder));

        //Ibinder在sm中有缓存  缓存在一个叫sCache的map里面  key->name:String,value-> Ibinder:IBinder
        Field cacheFild = serviceManager.getDeclaredField("sCache");
        cacheFild.setAccessible(true);
        Map<String, IBinder> map = (Map<String, IBinder>) cacheFild.get(null);
        map.put(CLIPBOARD_SERVICE, hookBinder);
    }
}
