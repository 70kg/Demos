package com.demos;

import android.app.Application;
import android.content.Context;

import com.morgoo.droidplugin.PluginHelper;


/**
 * Created by Mr_Wrong on 16/3/23.
 */
public class App extends Application {

    static App mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        PluginHelper.getInstance().applicationOnCreate(getBaseContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        /**
         * 主要进行crash的处理
         */
        PluginHelper.getInstance().applicationAttachBaseContext(base);
        super.attachBaseContext(base);
    }

    public static Context getContext() {
        return mContext;
    }
}
