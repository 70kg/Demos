package com.demos.hook.simpleHook.intercept_activity;

import android.app.Activity;
import android.os.Bundle;

import com.demos.R;
import com.socks.library.KLog;

/**
 * Created by wangpeng on 16/7/16.
 */
public class TargetActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.target_activity);
        KLog.e("onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        KLog.e("onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KLog.e("onDestroy");
    }
}
