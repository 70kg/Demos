package com.demos.headlist;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import com.socks.library.KLog;

/**
 * Created by Mr_Wrong on 16/3/2.
 * 一个下拉的小玩意
 */
public class RefreshList extends LinearLayout {
    public RefreshList(Context context) {
        super(context);
        init();
    }

    public RefreshList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RefreshList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightSize = mHeadView.getMeasuredHeight() + getMeasuredHeight();
        int heightMode = MeasureSpec.EXACTLY;
        mHeadHeight = mHeadView.getMeasuredHeight();
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(heightSize, heightMode));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mContentView.layout(0, mCurrentHeight, r, mCurrentHeight + mContentView.getMeasuredHeight());
        mHeadView.layout(0, -mHeadHeight + mCurrentHeight, r, mCurrentHeight);
//        KLog.e(mContentView.getTop());
    }

    AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();

    private void init() {
        setOrientation(VERTICAL);
        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {

                float f = top;
                KLog.e((f*interpolator.getInterpolation(f / mHeadHeight)));
//                mCurrentHeight = (int) (top*interpolator.getInterpolation(f / mHeadHeight));
                mCurrentHeight = top;
                requestLayout();
                mHeadView.setPercent(f / mHeadHeight);
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                if (mCurrentHeight + dy < 0) {
                    return 0;
                } else if (mCurrentHeight + dy > mHeadHeight) {
                    return mHeadHeight;
                } else {
                    return top;
                }
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (mContentView == releasedChild && mCurrentHeight > mHeadHeight * 0.5) {
                    open();
                } else {
                    close();
                }
            }
        });

    }

    private void open() {
        if (mViewDragHelper.smoothSlideViewTo(mContentView, 0, mHeadHeight)) {
            ViewCompat.postInvalidateOnAnimation(this);
            mRvReachTopWhenDown = false;
            mRvReachBottomWhenUp = false;
        }
    }

    private void close() {
        if (mViewDragHelper.smoothSlideViewTo(mContentView, 0, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
            mRvReachTopWhenDown = false;
            mRvReachBottomWhenUp = false;
        }
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    ChrysanthemumView mHeadView;
    View mContentView;
    ViewDragHelper mViewDragHelper;
    int mHeadHeight;
    int mCurrentHeight;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHeadView = (ChrysanthemumView) getChildAt(0);
        mContentView = getChildAt(1);
    }

    int initY;
    boolean shouldIntercept = false;
    boolean mRvReachTopWhenDown = false;
    boolean mRvReachBottomWhenUp = false;


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        shouldIntercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mRvReachTopWhenDown || mRvReachBottomWhenUp)
                    mViewDragHelper.processTouchEvent(ev);
                initY = (int) ev.getY();
                mRvReachTopWhenDown = mContentView.getTop() == 0;
                mRvReachBottomWhenUp = mContentView.getTop() == mHeadHeight;
                break;
            case MotionEvent.ACTION_MOVE:
//                KLog.e(mContentView.getTop());
                if (mRvReachTopWhenDown && ev.getY() - initY > 8) {//在上面 且向下滑动
                    shouldIntercept = true;
                }
                if (mRvReachBottomWhenUp && initY - ev.getY() > 8) {//在上面 且向下滑动
                    shouldIntercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                shouldIntercept = false;
                break;
        }

//        KLog.e(shouldIntercept);
        return shouldIntercept && mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }
}
