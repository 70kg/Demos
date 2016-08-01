package com.demos.NestedScrolling.person_center;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.socks.library.KLog;


/**
 * Created by wangpeng on 16/7/4.
 */
public class HeadBehavior extends ViewOffsetBehavior<HeadLayout> {
    private static final int SCROLL_BACK_SPEED = 24;
    private static final int SCROLL_RESET_FROMTOP = 30;
    private static final int INVALID_POINTER = -1;
    private HeadLayout headLayout;
    private boolean mIsScrolling;
    private int mSkippedOffset;
    private boolean mIsBeingDragged;
//    private int mActivePointerId = INVALID_POINTER;
//    private int mLastMotionY;
    private int mTouchSlop = -1;
    private RecyclerView recyclerView;
    private ScrollBackRunnable runnable;
    private ScrollTopRunnable scrollTopRunnable;
    private ScrollResetFromTopRunnable scrollResetFromTopRunnable;

    public HeadBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, HeadLayout child, int layoutDirection) {
        headLayout = child;
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, HeadLayout child, View directTargetChild,
                                       View target, int nestedScrollAxes) {
        if ((nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0) {
            mIsScrolling = false;
            mSkippedOffset = 0;
            return true;
        }
        return false;
    }

    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, HeadLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, HeadLayout child, View target,
                                  int dx, int dy, int[] consumed) {
        if (!mIsScrolling) {
            mSkippedOffset += dy;
            if (Math.abs(mSkippedOffset) > mTouchSlop) {
                mIsScrolling = true;
            }
        }
        if (mIsScrolling) {
            if (dy > 0) {//up
                consumed[1] = scroll(coordinatorLayout, child, dy);
            } else {//down
                recyclerView = (RecyclerView) target;
                if (recyclerView.computeVerticalScrollOffset() == 0) {
                    consumed[1] = scroll(coordinatorLayout, child, dy / 2);
                }
            }
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, HeadLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, HeadLayout child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
        resetHeadView();
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, HeadLayout child, View target,
                                 float velocityX, float velocityY, boolean consumed) {
        if (velocityY < 0) {
            if (recyclerView.computeVerticalScrollOffset() == 0) {
                resetFromTop();
            }
        } else if (getTopAndBottomOffset() <= 0) {
            setHeadViewToTop();
        }
        return false;
    }

    public void resetHeadView() {
        if (runnable == null) {
            runnable = new ScrollBackRunnable(this, headLayout);
        }
        headLayout.post(runnable);
    }

    public void setHeadViewToTop() {
        if (scrollTopRunnable == null) {
            scrollTopRunnable = new ScrollTopRunnable(this, headLayout);
        }
        headLayout.post(scrollTopRunnable);
    }

    public void resetFromTop() {
        if (scrollResetFromTopRunnable == null) {
            scrollResetFromTopRunnable = new ScrollResetFromTopRunnable(this, headLayout);
        }
        headLayout.post(scrollResetFromTopRunnable);
    }

    private final static class ScrollBackRunnable implements Runnable {

        private HeadBehavior headBehavior;

        private HeadLayout headLayout;

        public ScrollBackRunnable(HeadBehavior headBehavior, HeadLayout headLayout) {
            this.headBehavior = headBehavior;
            this.headLayout = headLayout;
        }

        @Override
        public void run() {
            if (headBehavior.getTopAndBottomOffset() > 0) {
                int newOffset = Math.max(headBehavior.getTopAndBottomOffset() - SCROLL_BACK_SPEED, 0);
                headBehavior.setTopAndBottomOffset(newOffset);
                headLayout.post(this);
            }
        }
    }

    private final static class ScrollResetFromTopRunnable implements Runnable {

        private HeadBehavior headBehavior;

        private HeadLayout headLayout;

        public ScrollResetFromTopRunnable(HeadBehavior headBehavior, HeadLayout headLayout) {
            this.headBehavior = headBehavior;
            this.headLayout = headLayout;
        }

        @Override
        public void run() {
            if (headBehavior.getTopAndBottomOffset() < 0) {
                int newOffset = Math.min(headBehavior.getTopAndBottomOffset() + SCROLL_RESET_FROMTOP, 0);
                headBehavior.setTopAndBottomOffset(newOffset);
                headLayout.post(this);
            }
        }
    }

    private final static class ScrollTopRunnable implements Runnable {

        private HeadBehavior headBehavior;

        private HeadLayout headLayout;

        public ScrollTopRunnable(HeadBehavior headBehavior, HeadLayout headLayout) {
            this.headBehavior = headBehavior;
            this.headLayout = headLayout;
        }

        @Override
        public void run() {
            if (headBehavior.getTopAndBottomOffset() - SCROLL_RESET_FROMTOP > -350) {
                int newOffset = Math.min(headBehavior.getTopAndBottomOffset() - SCROLL_RESET_FROMTOP, 0);
                headBehavior.setTopAndBottomOffset(newOffset);
                headLayout.post(this);
            }
        }
    }

    @Override
    public boolean setTopAndBottomOffset(int offset) {
        headLayout.notifyBeginTranslation(offset);
        return super.setTopAndBottomOffset(offset);
    }

    private int mInitialTouchY;
    private int mLastTouchY;
    private int mScrollPointerId = INVALID_POINTER;

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, HeadLayout child, MotionEvent ev) {
        if (mTouchSlop < 0) {
            mTouchSlop = ViewConfiguration.get(parent.getContext()).getScaledTouchSlop();
        }

        final int actionIndex = MotionEventCompat.getActionIndex(ev);

        switch (MotionEventCompat.getActionMasked(ev)) {
            case MotionEvent.ACTION_DOWN:
                mScrollPointerId = MotionEventCompat.getPointerId(ev, 0);
                mInitialTouchY = mLastTouchY = (int) (ev.getY() + 0.5f);
                break;
            case MotionEventCompat.ACTION_POINTER_DOWN:
                mScrollPointerId = MotionEventCompat.getPointerId(ev, actionIndex);
                mInitialTouchY = mLastTouchY = (int) (MotionEventCompat.getY(ev, actionIndex) + 0.5f);
                break;
            case MotionEvent.ACTION_MOVE: {
                final int activePointerIndex = MotionEventCompat.findPointerIndex(ev,
                        mScrollPointerId);
                if (activePointerIndex == -1) {
                    return false;
                }

                final int y = (int) MotionEventCompat.getY(ev, activePointerIndex);
                int dy = mLastTouchY - y;
                if (!mIsBeingDragged && Math.abs(dy) > mTouchSlop) {
                    mIsBeingDragged = true;
                    if (dy > 0) {
                        dy -= mTouchSlop;
                    } else {
                        dy += mTouchSlop;
                    }
                }

                if (mIsBeingDragged) {
                    mLastTouchY = y;
                    scroll(parent, child, dy);
                }
                break;
            }
            case MotionEventCompat.ACTION_POINTER_UP:
                onPointerUp(ev);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                resetHeadView();
                break;
        }
//        switch (ev.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN: {
//                final int x = (int) ev.getX();
//                final int y = (int) ev.getY();
//                if (parent.isPointInChildBounds(child, x, y)) {
//                    mLastMotionY = y;
//                } else {
//                    return false;
//                }
//                break;
//            }
//            case MotionEvent.ACTION_POINTER_DOWN:
//            case MotionEvent.ACTION_POINTER_UP:
//                mLastMotionY = (int) ev.getY();
//                break;
//            case MotionEvent.ACTION_MOVE: {
//                final int y = (int) ev.getY();
//                int dy = mLastMotionY - y;
//                if (!mIsBeingDragged && Math.abs(dy) > mTouchSlop) {
//                    mIsBeingDragged = true;
//                    if (dy > 0) {
//                        dy -= mTouchSlop;
//                    } else {
//                        dy += mTouchSlop;
//                    }
//                }
//
//                if (mIsBeingDragged) {
//                    mLastMotionY = y;
//                    scroll(parent, child, dy / 2);
//                }
//                break;
//            }
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                resetHeadView();
//                break;
//        }
        return true;
    }

    private void onPointerUp(MotionEvent e) {
        final int actionIndex = MotionEventCompat.getActionIndex(e);
        if (MotionEventCompat.getPointerId(e, actionIndex) == mScrollPointerId) {
            final int newIndex = actionIndex == 0 ? 1 : 0;
            mScrollPointerId = MotionEventCompat.getPointerId(e, newIndex);
            mInitialTouchY = mLastTouchY = (int) (MotionEventCompat.getY(e, newIndex) + 0.5f);
        }
    }

    final int scroll(CoordinatorLayout coordinatorLayout, HeadLayout header, int dy) {
        return setHeaderTopBottomOffset(coordinatorLayout, header,
                getTopAndBottomOffset() - dy, -header.getHeight() + 150, 2000);
    }

    int setHeaderTopBottomOffset(CoordinatorLayout parent, HeadLayout header, int newOffset,
                                 int minOffset, int maxOffset) {
        final int curOffset = getTopAndBottomOffset();
        int consumed = 0;

        if (minOffset != 0 && curOffset >= minOffset && curOffset <= maxOffset) {
            newOffset = constrain(newOffset, minOffset, maxOffset);
            if (curOffset != newOffset) {
                setTopAndBottomOffset(newOffset);
                consumed = curOffset - newOffset;
            }
        }

        return consumed;
    }

    static int constrain(int amount, int low, int high) {
        return amount < low ? low : (amount > high ? high : amount);
    }


}
