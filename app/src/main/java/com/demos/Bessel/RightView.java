package com.demos.Bessel;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Mr_Wrong on 16/3/17.
 * 画对号的
 */
public class RightView extends View {
    public RightView(Context context) {
        this(context, null);
    }

    public RightView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    Paint mPaint;
    Path mPath;
    PathMeasure mPathMeasure;
    Path mPath1;

    public RightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mPath1 = new Path();
        mPath = new Path();
        mPath.moveTo(120, 180);
        mPath.lineTo(180, 220);
        mPath.lineTo(280, 150);
        start();
    }

    public void start() {
        if (mPathMeasure == null) mPathMeasure = new PathMeasure(mPath, false);
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float f = (float) animation.getAnimatedValue();
                mPathMeasure.getSegment(0, f * mPathMeasure.getLength(), mPath1, true);
                invalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(200, 200, 100, mPaint);
        canvas.drawPath(mPath1, mPaint);
    }
}
