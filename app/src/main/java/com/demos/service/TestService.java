package com.demos.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.socks.library.KLog;

/**
 * Created by Mr_Wrong on 16/3/14.
 */
public class TestService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        KLog.e("onStartCommand");

        return Service.START_NOT_STICKY;
    }

    class MyBinder extends Binder {
        int doThing(String arg) {
            return arg.hashCode();
        }
    }

}