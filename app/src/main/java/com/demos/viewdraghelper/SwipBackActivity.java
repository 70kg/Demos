package com.demos.viewdraghelper;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;
import com.socks.library.KLog;

/**
 * Created by Mr_Wrong on 16/2/17.
 */
public class SwipBackActivity extends ToolBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = (Button) findViewById(R.id.btn_swipback);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTool.start(SecondActivity.class);
            }
        });


        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                KLog.e("main_thread_" + Thread.currentThread().getName());
            }
        };

        HandlerThread handlerThread = new HandlerThread("handleThread") {
            @Override
            protected void onLooperPrepared() {
                KLog.e("handle_thread_" + Thread.currentThread().getName());
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
            }
        };
        handlerThread.start();

    }

    @Override
    public int getLayout() {
        return R.layout.swipback;
    }

    private void learnContext(){
        Context context = getBaseContext();
    }
}
