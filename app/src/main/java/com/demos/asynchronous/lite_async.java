package com.demos.asynchronous;

import com.demos.main.base.ToolBarActivity;
import com.litesuits.android.async.SimpleCachedTask;

import java.util.concurrent.TimeUnit;

/**
 * Created by Mr_Wrong on 16/3/16.
 */
public class lite_async extends ToolBarActivity{
    private void test() {
        new SimpleCachedTask<String>(this, "key", 1000, TimeUnit.SECONDS) {
            @Override
            protected String doConnectNetwork() throws Exception {
                return null;
            }
        };
    }

    @Override
    public int getLayout() {
        return 0;
    }
}
