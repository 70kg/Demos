package com.demos.window;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.demos.utils.ScreenUtil;
import com.socks.library.KLog;

/**
 * Created by Mr_Wrong on 16/2/23.
 */
public class WindowService extends Service {
    WindowManager mManager;
    ImageView mImageView;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createWindow();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        float alpha = intent.getFloatExtra("alpha", 0.0f);
        KLog.e(alpha);
        linearLayout.setAlpha(alpha);
        mManager.updateViewLayout(linearLayout, layoutParams);
        return START_STICKY;
    }

    WindowManager.LayoutParams layoutParams;
    LinearLayout linearLayout;

    private void createWindow() {
        layoutParams = new WindowManager.LayoutParams();
        mManager = (WindowManager) getApplication().getSystemService(WINDOW_SERVICE);
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

        layoutParams.gravity = Gravity.CENTER;
        int maxSize = Math.max(ScreenUtil.getTrueScreenHeight(getApplicationContext()), ScreenUtil.getTrueScreenWidth(getApplicationContext()));
        layoutParams.height = maxSize + 200;
        layoutParams.width = maxSize + 200;

        mImageView = new ImageView(this);

        mImageView.setImageResource(android.R.drawable.ic_menu_add);

        linearLayout = new LinearLayout(this);
        linearLayout.setBackgroundColor(Color.BLACK);
        mManager.addView(linearLayout, layoutParams);
        mImageView.setOnTouchListener(touchListener);
    }

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            layoutParams.x = (int) event.getX();
            layoutParams.y = (int) event.getY();
            mManager.updateViewLayout(mImageView, layoutParams);
            return true;
        }
    };
}
