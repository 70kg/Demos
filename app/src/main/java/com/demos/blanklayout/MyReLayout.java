package com.demos.blanklayout;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Mr_Wrong on 16/3/7.
 */
public class MyReLayout extends SwipeRefreshLayout {
    public MyReLayout(Context context) {
        super(context);
    }

    public MyReLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    BlankLayout mBlankLayout;
    RecyclerView mRecyclerView;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBlankLayout = (BlankLayout) getChildAt(1);
        mRecyclerView = (RecyclerView) mBlankLayout.getChildAt(0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isRvReachTop()) {
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    private boolean isRvReachTop() {
        int checkPos = 0;
        View mFirstChild = mRecyclerView.getChildAt(0);
        int rvPos = mRecyclerView.getChildLayoutPosition(mFirstChild);
        if (rvPos == checkPos && mFirstChild.getTop() == 0) {
            return true;
        }
        return false;
    }
}
