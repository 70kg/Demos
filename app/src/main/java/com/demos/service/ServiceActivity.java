package com.demos.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;
import com.socks.library.KLog;

import butterknife.OnClick;

/**
 * Created by Mr_Wrong on 16/3/14.
 */
public class ServiceActivity extends ToolBarActivity {
    Intent mIntent;

    @Override
    public int getLayout() {
        return R.layout.service_layout;
    }

    @OnClick(R.id.btn_start_service)
    void _start() {
        startService(mIntent);
    }

    @OnClick(R.id.btn_bind_service)
    void _bind() {
        bindService(mIntent, mConnection, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIntent = new Intent(this, TestService.class);
    }

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            KLog.e(name.toString());
            int hashCode = ((TestService.MyBinder) service).doThing("70kg");
            KLog.e(hashCode);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
