package com.demos.support_23_2;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.TextView;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;
import com.socks.library.KLog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Mr_Wrong on 16/2/25.
 */
public class DayNightActivity extends ToolBarActivity {
    @Bind(R.id.nestedscrollview)
    NestedScrollView nestedscrollview;

    @Bind(R.id.tv_text)
    TextView mTextView;

    @OnClick(R.id.btn_button1)
    void _btn1() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        recreate();
    }

    @OnClick(R.id.btn_button2)
    void _btn2() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        recreate();
    }

    @OnClick(R.id.btn_button3)
    void _btn3() {
        Drawable[] ds = mTextView.getCompoundDrawables();
        if (ds[3] instanceof Animatable) {
            Animatable a = (Animatable)ds[3];
            a.start();
        }



        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.daynight;
    }

    BottomSheetBehavior behavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        behavior = BottomSheetBehavior.from(nestedscrollview);
        behavior.setHideable(true);
        behavior.setPeekHeight(66);
        KLog.e(behavior.getState());
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //0-1
            }
        });
    }
}
