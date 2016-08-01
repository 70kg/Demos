package com.demos.headlist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Mr_Wrong on 16/3/2.
 * 菊花
 */
public class ChrysanthemumView extends View {

    public ChrysanthemumView(Context context) {
        super(context);
        init();
    }

    public ChrysanthemumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChrysanthemumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint mPaint;

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mCenterX = getMeasuredWidth() / 2;
        mCenterY = getMeasuredHeight() / 2;
    }

    int mCenterX, mCenterY;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        for (int i = 0; i < 120 * percent; i++) {
            canvas.rotate(3, mCenterX, mCenterY);
            canvas.drawLine(mCenterX, 200, mCenterX, 300, mPaint);
        }
        canvas.restore();
    }

    float percent;

    public void setPercent(float percent) {
        this.percent = percent;
        invalidate();
    }
}
