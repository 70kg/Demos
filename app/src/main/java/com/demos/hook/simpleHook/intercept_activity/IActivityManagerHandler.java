package com.demos.hook.simpleHook.intercept_activity;

import android.content.ComponentName;
import android.content.Intent;

import com.demos.App;
import com.socks.library.KLog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by wangpeng on 16/7/13.
 */
public class IActivityManagerHandler implements InvocationHandler {

    Object object;//activityManagerNative

    public IActivityManagerHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //hook startActivity 把intent参数中的Component替换成StubActivity 并且将真正的Component存在newintent中
        //到从AMS进程回到APP进程再进行替换回来
        if (method.getName().equals("startActivity")) {

            int index = 0;
            Intent rawIntent;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    index = i;
                    break;
                }
            }
            rawIntent = (Intent) args[index];

            Intent newIntent = new Intent();

            String packageName = App.getContext().getPackageName();

            newIntent.setComponent(new ComponentName(packageName, StubActivity.class.getName()));
            newIntent.putExtra(AMSHookHelper.RAW_INTENT, rawIntent);
            args[index] = newIntent;
            KLog.e("在APP进程到AMS进程时进行activity替换");
        }
        return method.invoke(object, args);
    }
}
