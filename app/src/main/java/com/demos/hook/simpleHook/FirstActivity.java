package com.demos.hook.simpleHook;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.demos.NestedScrolling.FabActivity;
import com.demos.R;
import com.demos.RequestQueue.Executor;
import com.demos.hook.simpleHook.amsHook.HookHandler;
import com.demos.hook.simpleHook.amsHook.HookHelper;
import com.demos.hook.simpleHook.classLoad_hook.BaseDexClassLoaderHookHelper;
import com.demos.hook.simpleHook.classLoad_hook.LoadedApkClassLoaderHookHelper;
import com.demos.hook.simpleHook.intercept_activity.AMSHookHelper;
import com.demos.hook.simpleHook.intercept_activity.TargetActivity;
import com.demos.hook.simpleHook.startActivityHook.HookerHelper;
import com.demos.main.base.ToolBarActivity;
import com.demos.utils.Utils;
import com.morgoo.droidplugin.pm.PluginManager;
import com.socks.library.KLog;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.iwgang.simplifyspan.SimplifySpanBuild;
import cn.iwgang.simplifyspan.other.OnClickableSpanListener;
import cn.iwgang.simplifyspan.other.SpecialGravity;
import cn.iwgang.simplifyspan.unit.SpecialClickableUnit;
import cn.iwgang.simplifyspan.unit.SpecialImageUnit;
import cn.iwgang.simplifyspan.unit.SpecialLabelUnit;
import cn.iwgang.simplifyspan.unit.SpecialTextUnit;

/**
 * Created by wangpeng on 16/7/10.
 */
public class FirstActivity extends ToolBarActivity {
    @Override
    public int getLayout() {
        return R.layout.hook_first;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
//            HookerHelper.hookActivity(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_hook)
    public void _click() {
        go(FabActivity.class);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setData(Uri.parse("http://www.baidu.com"));
//        // 注意这里使用的ApplicationContext 启动的Activity
//        // 因为Activity对象的startActivity使用的并不是ContextImpl的mInstrumentation
//        // 而是自己的mInstrumentation, 如果你需要这样, 可以自己Hook
//        // 比较简单, 直接替换这个Activity的此字段即可.
//        getApplicationContext().startActivity(intent);
    }

    @OnClick(R.id.btn_hook_ams)
    public void _hook_ams() {
        go(FabActivity.class);

        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

    }

    @OnClick(R.id.btn_interceot_activity)
    public void _intercept_activity() {
//        go(TargetActivity.class);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.demos.hook.simpleHook.intercept_activity",
                "com.demos.hook.simpleHook.intercept_activity.TargetActivity"));
        startActivity(intent);
    }


    @OnClick(R.id.btn_hook_classloader)
    public void _hook_classloader() {
        KLog.e(getApplicationContext().getClassLoader());
        // 先获取到当前的ActivityThread对象
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

        Intent t = new Intent();
//        t.setComponent(new ComponentName("com.example.myapplication",
//                "com.example.myapplication.MainActivity"));
        t.setComponent(new ComponentName("com.example.myapplication",
                "com.example.myapplication.MainActivity"));
        startActivity(t);
    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(newBase);
//        try {
////            HookerHelper.attachContext(this);
////            HookHelper.hookAMS();
//            Utils.extractAssets(newBase, "myapplication.apk");
////
////            File dexFile = getFileStreamPath("myapplication.apk");
////            File optDexFile = getFileStreamPath("myapplication.dex");
////            BaseDexClassLoaderHookHelper.patchClassLoader(getClassLoader(), dexFile, optDexFile);
//
//            LoadedApkClassLoaderHookHelper.hookLoadedApkInActivityThread(getFileStreamPath("myapplication.apk"));
//            AMSHookHelper.hookActivityManagerNative();
//            AMSHookHelper.hookActivityThreadHandler();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    @OnClick(R.id.tv_apk)
    public void _load() {
        try {
            int result = PluginManager.getInstance().installPackage(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/Meizi.apk", 0);
            KLog.e(result);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.tv_open)
    public void _open() {
        PackageManager pm = getPackageManager();
        final PackageInfo info = pm.getPackageArchiveInfo(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/Meizi.apk", 0);
        Intent intent = pm.getLaunchIntentForPackage(info.packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
