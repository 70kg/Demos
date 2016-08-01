package com.demos.TwoBallsSeekbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Mr_Wrong on 16/2/23.
 */
public class TwoBallsSeekBar extends FrameLayout {

    public TwoBallsSeekBar(Context context) {
        super(context);
        init();
    }

    public TwoBallsSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TwoBallsSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    View mLeftBall;
    View mRightBall;
    View mLine;
    int mBallWidth;
    ViewDragHelper mDragHelper;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLine = getChildAt(0);
        mLeftBall = getChildAt(1);
        mRightBall = getChildAt(2);
    }

    private void init() {
        setWillNotDraw(false);
        mDragHelper = ViewDragHelper.create(this, mCallback);
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setStyle(Paint.Style.FILL);
    }

    Paint mLinePaint;
    int rect_w = 42, rect_h = 20, rect_radius = 8;
    int triangle_b = 8, triangle_h = 4;
    int font_size = 14;

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        mLinePaint.setStrokeWidth(mLine.getMeasuredHeight());
        int left, right, top, bottom;
        int half_line_img_h = mLine.getMeasuredHeight() / 2;

        left = mLeftBall.getRight();
        top = mLine.getTop() + half_line_img_h;
        right = mRightBall.getLeft();
        bottom = mLine.getTop() + half_line_img_h;
        //画遮盖线
        canvas.drawLine(left, top, right, bottom, mLinePaint);

        int half_left_img_w = mLeftBall.getMeasuredWidth() / 2;
        left = (int) (mLeftBall.getLeft() + half_left_img_w - pxToDp(rect_w) / 2);
        top = (int) (mLeftBall.getTop() - pxToDp(rect_h + triangle_h));

        float percent = ((float) mLeftBall.getLeft()) / getMeasuredWidth();
        drawBlueRoundRectWithDownTriangleWithText(canvas, left, top, (int) (percent * 100));

    }

    Paint paint;

    private void drawBlueRoundRectWithDownTriangleWithText(Canvas canvas, float x, float y, int value) {
        if (paint == null) paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.reset();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        RectF roundRect = new RectF(x, y, x + pxToDp(rect_w), y + pxToDp(rect_h));// 设置个新的长方形
        canvas.drawRoundRect(roundRect, rect_radius, rect_radius, paint);
        Path path = new Path();
        path.moveTo(x + pxToDp(rect_w / 2 - triangle_b / 2), y + pxToDp(rect_h));
        path.lineTo(x + pxToDp(rect_w / 2), y + pxToDp(rect_h + triangle_h));
        path.lineTo(x + pxToDp(rect_w / 2 + triangle_b / 2), y + pxToDp(rect_h));
        path.close();
        canvas.drawPath(path, paint);

        paint.reset();
        paint.setAntiAlias(true);
        paint.setTextSize(pxToDp(font_size));
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.DEFAULT_BOLD);

        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = (int) ((roundRect.bottom + roundRect.top - fontMetrics.bottom - fontMetrics.top) / 2);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(value + "", roundRect.centerX(), baseline, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mRightBall || mLeftBall == child;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == mLeftBall) {
                if (left < getPaddingLeft())
                    left = getPaddingLeft();
                if (left > getMeasuredWidth() - mRightBall.getMeasuredWidth() - mLeftBall.getMeasuredWidth() - getPaddingRight())
                    left = getMeasuredWidth() - mRightBall.getMeasuredWidth() - mLeftBall.getMeasuredWidth() - getPaddingRight();
            }

            if (child == mRightBall) {
                if (left < mLeftBall.getMeasuredWidth() + getPaddingLeft()) {
                    left = mLeftBall.getMeasuredWidth() + getPaddingLeft();
                }
                if (left > getMeasuredWidth() - mRightBall.getMeasuredWidth() - getPaddingRight()) {
                    left = getMeasuredWidth() - mRightBall.getMeasuredWidth() - getPaddingRight();
                }
            }
            invalidate();
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return getPaddingTop();
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == mLeftBall) {
                //mLeftBall右滑撞到右边Ball
                if (left + mBallWidth - dx == mRightBall.getLeft() && dx > 0) {
                    mRightBall.offsetLeftAndRight(dx);
                    return;
                }
                if (-(left + mBallWidth - mRightBall.getLeft()) <= mDragHelper.getTouchSlop() && dx > 0) {
                    mLeftBall.offsetLeftAndRight(-(left + mBallWidth - mRightBall.getLeft()));
                }
            }
            if (changedView == mRightBall) {
                //mRightBall左滑撞到左边ball
                if (left - dx == mLeftBall.getRight() && dx < 0) {
                    mLeftBall.offsetLeftAndRight(dx);
                    return;
                }
                if ((left - mLeftBall.getRight()) <= mDragHelper.getTouchSlop() && dx < 0) {
                    mRightBall.offsetLeftAndRight(-(left - mLeftBall.getRight()));
                }
            }

        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mBallWidth = mLeftBall.getMeasuredWidth();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int height = getMeasuredHeight();
        mLine.layout(0, height / 2 - mLine.getMeasuredHeight() / 2, right, height / 2 + mLine.getMeasuredHeight() / 2);
        mRightBall.offsetLeftAndRight(400);
    }

    private float pxToDp(int pix) {
        final float scale = getResources().getDisplayMetrics().density;
        return pix * scale;
    }

}
