package com.demos.RequestQueue;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * Created by Mr_Wrong on 16/3/28.
 */
public class Dispatcher {

    Handler mHandler = new Handler(Looper.getMainLooper());
    private final Executor mResponsePoster;

    public Dispatcher() {
        mResponsePoster = new Executor() {
            public void execute(Runnable command) {
                mHandler.post(command);
            }
        };
    }

    public <T extends Resquestfor70kg> void dispatch(final T request) {
        mResponsePoster.execute(new Runnable() {
            @Override
            public void run() {
                request.callBack.onResponse(request.data);
            }
        });
    }
}
