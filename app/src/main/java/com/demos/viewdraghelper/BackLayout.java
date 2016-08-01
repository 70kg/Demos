package com.demos.viewdraghelper;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Mr_Wrong on 16/2/17.
 */
public class BackLayout extends FrameLayout {
    ViewDragHelper mDragHelper;
    int mWidth, mHeight, mSwipLeft, mSwipWidth;
    View mContent, mBehind;
    BackEnum mBackEnum = BackEnum.close;

    enum BackEnum {
        swip, close, open
    }

    public BackLayout(Context context) {
        super(context);
        init();
    }

    public BackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBehind = getChildAt(0);
        mContent = getChildAt(1);
        mBehind.setClickable(true);
        mContent.setClickable(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mSwipWidth = mBehind.getMeasuredWidth();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mContent.layout(mSwipLeft, 0, mWidth + mSwipLeft, mHeight);
        mBehind.layout(0, 0, mSwipWidth, mHeight);
    }

    private void init() {
        mDragHelper = ViewDragHelper.create(this, new DragCallBack());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    class DragCallBack extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return false;
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            mDragHelper.captureChildView(mContent, pointerId);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return 6;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            if (mSwipLeft + dx < 0) {
                return 0;
            } else if (mSwipLeft + dx > mSwipWidth) {
                return mSwipWidth;
            } else {
                return left;
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (releasedChild == mContent && mSwipLeft > 0.5 * mSwipWidth) {
                open();
            } else {
                close();
            }

        }


        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == mContent) {
                mSwipLeft = left;
            }
            handleEvent(mSwipLeft);
            requestLayout();
        }
    }

    private void handleEvent(int swipLeft) {
        if (swipLeft == 0) {
            mBackEnum = BackEnum.close;
            mListener.close();
        } else if (mSwipWidth == swipLeft) {
            mBackEnum = BackEnum.open;
        } else {
            mBackEnum = BackEnum.swip;
            float percent = ((float) swipLeft) / mSwipWidth;
            mListener.onSwip(percent);
        }

    }

    /**
     * 打开事件
     */
    private void open() {
        if (mDragHelper.smoothSlideViewTo(mContent, mSwipWidth, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        mListener.open();
    }

    /**
     * 关闭事件
     */
    private void close() {
        if (mDragHelper.smoothSlideViewTo(mContent, 0, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    onSwipListener mListener;

    public void setOnSwipListener(onSwipListener mListener) {
        this.mListener = mListener;
    }


    interface onSwipListener {
        void open();

        void close();

        void onSwip(float percent);
    }
}
