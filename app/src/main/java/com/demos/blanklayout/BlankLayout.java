package com.demos.blanklayout;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingParent;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.demos.R;

/**
 * Created by Mr_Wrong on 16/3/7.
 */
public class BlankLayout extends FrameLayout implements NestedScrollingChild, NestedScrollingParent {
    public BlankLayout(Context context) {
        this(context, null);
    }

    public BlankLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlankLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    View blankView;
    RecyclerView mRecyclerView;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRecyclerView = (RecyclerView) getChildAt(0);
        blankView = LayoutInflater.from(getContext()).inflate(R.layout.blank_view, null);
    }

    public void showBlankView() {
        if (null == blankView.getParent()) {
            addView(blankView);
        }
    }

    public void removeBlankView() {
        if (null != blankView.getParent()) {
            removeView(blankView);
        }
    }

}
