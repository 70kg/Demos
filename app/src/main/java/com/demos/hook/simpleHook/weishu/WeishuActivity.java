package com.demos.hook.simpleHook.weishu;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.demos.hook.simpleHook.weishu.ams_hook.AMSHookHelper;
import com.demos.hook.simpleHook.weishu.classloder_hook.BaseDexClassLoaderHookHelper;
import com.demos.hook.simpleHook.weishu.classloder_hook.LoadedApkClassLoaderHookHelper;
import com.socks.library.KLog;

/**
 * @author weishu
 * @date 16/3/28
 */
public class WeishuActivity extends Activity {

    private static final String TAG = "WeishuActivity";

    private static final int PATCH_BASE_CLASS_LOADER = 1;

    private static final int CUSTOM_CLASS_LOADER = 2;

    private static final int HOOK_METHOD = CUSTOM_CLASS_LOADER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button t = new Button(this);
        t.setText("test button");

        setContentView(t);

        KLog.e(getApplicationContext().getClassLoader());
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Class<?> activityThreadClass = null;
                try {
                    activityThreadClass = Class.forName("android.app.ActivityThread");
                    Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
                    currentActivityThreadMethod.setAccessible(true);
                    Object currentActivityThread = currentActivityThreadMethod.invoke(null);

                    // 获取到 mPackages 这个静态成员变量, 这里缓存了dex包的信息
                    Field mPackagesField = activityThreadClass.getDeclaredField("mPackages");
                    mPackagesField.setAccessible(true);
                    Map mPackages = (Map) mPackagesField.get(currentActivityThread);
                    KLog.e(mPackages);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                try {
                    Intent t = new Intent();
                    if (HOOK_METHOD == PATCH_BASE_CLASS_LOADER) {
                        t.setComponent(new ComponentName("com.example.myapplication",
                                "com.example.myapplication.MainActivity"));
                    } else {
                        t.setComponent(new ComponentName("com.example.myapplication",
                                "com.example.myapplication.MainActivity"));
                    }
                    startActivity(t);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            Utils.extractAssets(newBase, "myapplication.apk");

            if (HOOK_METHOD == PATCH_BASE_CLASS_LOADER) {
                File dexFile = getFileStreamPath("myapplication.apk");
                File optDexFile = getFileStreamPath("myapplication.dex");
                BaseDexClassLoaderHookHelper.patchClassLoader(getClassLoader(), dexFile, optDexFile);
            } else {
                LoadedApkClassLoaderHookHelper.hookLoadedApkInActivityThread(getFileStreamPath("myapplication.apk"));
            }

            AMSHookHelper.hookActivityManagerNative();
            AMSHookHelper.hookActivityThreadHandler();

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
