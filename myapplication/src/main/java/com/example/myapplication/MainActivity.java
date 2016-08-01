package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author weishu
 * @date 16/3/28
 */
public class MainActivity extends Activity {

//    private static final String TAG = "MainActivity";
//
//    private static final int PATCH_BASE_CLASS_LOADER = 1;
//
//    private static final int CUSTOM_CLASS_LOADER = 2;
//
//    private static final int HOOK_METHOD = PATCH_BASE_CLASS_LOADER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Log.d(TAG, "context classloader: " + getApplicationContext().getClassLoader());
//        t.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    Intent t = new Intent();
//                    if (HOOK_METHOD == PATCH_BASE_CLASS_LOADER) {
//                        t.setComponent(new ComponentName("com.demos.main", "com.demos.main.MainActivity"));
//                    } else {
//                        t.setComponent(new ComponentName("com.demos.main", "com.demos.main.MainActivity"));
//                    }
//                    startActivity(t);
//                } catch (Throwable e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(newBase);
//        try {
//            Utils.extractAssets(newBase, "app.apk");
//
//            if (HOOK_METHOD == PATCH_BASE_CLASS_LOADER) {
//                File dexFile = getFileStreamPath("app.apk");
//                File optDexFile = getFileStreamPath("app.dex");
//                BaseDexClassLoaderHookHelper.patchClassLoader(getClassLoader(), dexFile, optDexFile);
//            } else {
//                LoadedApkClassLoaderHookHelper.hookLoadedApkInActivityThread(getFileStreamPath("app.apk"));
//            }
//
//            AMSHookHelper.hookActivityManagerNative();
//            AMSHookHelper.hookActivityThreadHandler();
//
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//    }

}
