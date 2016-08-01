package com.demos.Bessel;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr_Wrong on 16/3/4.
 * 画图表的  还有曲线运动
 */
public class TestBesselView extends View {
    public TestBesselView(Context context) {
        super(context);
        init();
    }

    public TestBesselView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestBesselView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private float[] testDatas = {60f, 30f, 57f, 41f, 88f, 70f};

    private List<Point> datas;
    private final float maxValue = 100f;
    private Paint mPaint;
    // 辅助性画笔
    private Paint controllPaintA;
    private Paint controllPaintB;
    private Path clicPath;
    int width, height, offSet;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();

        offSet = width / testDatas.length;
        if (datas.size() == 0) {
            for (int i = 0; i < testDatas.length; i++) {
                float ratio = testDatas[i] / maxValue;
                Point point;
                if (i == 0) {
                    point = new Point(0, (int) (height * (1 - ratio)));
                } else if (i == testDatas.length - 1) {
                    point = new Point(width, (int) (height * (1 - ratio)));
                } else {
                    point = new Point(i * offSet, (int) (height * (1 - ratio)));
                }
                datas.add(point);
            }
        }
    }

    private void init() {
        datas = new ArrayList<>();
        controllPaintA = new Paint(Paint.ANTI_ALIAS_FLAG);
        controllPaintA.setColor(Color.RED);
        controllPaintB = new Paint(Paint.ANTI_ALIAS_FLAG);
        controllPaintB.setColor(Color.GREEN);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        clicPath = new Path();
    }

    PathMeasure mPathMeasure;
    float[] mCurrentPosition = new float[2];

    public void startAnimator(long duration) {
        onlyBall = true;
        if (mPathMeasure == null) mPathMeasure = new PathMeasure(clicPath, false);
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                mPathMeasure.getPosTan(value, mCurrentPosition, null);
                invalidate();
//                if (value == mPathMeasure.getLength())
//                    animaFirst = true;
            }
        });
        valueAnimator.start();
    }

    boolean onlyBall = false;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < datas.size() - 1; i++) {
            Point startPoint = datas.get(i);
            Point endPoint = datas.get(i + 1);

            if (i == 0) clicPath.moveTo(startPoint.x, startPoint.y);

            int controllA_X = (startPoint.x + endPoint.x) >> 1;
            int controllA_Y = startPoint.y;
            int controllB_X = (startPoint.x + endPoint.x) >> 1;
            int controllB_Y = endPoint.y;
            clicPath.cubicTo(controllA_X, controllA_Y, controllB_X, controllB_Y, endPoint.x, endPoint.y);

            // 控制点展示
            canvas.drawCircle(controllA_X, controllA_Y, 5, controllPaintA);
            canvas.drawCircle(controllB_X, controllB_Y, 5, controllPaintB);
        }

        canvas.drawPath(clicPath, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(mCurrentPosition[0], mCurrentPosition[1], 10, mPaint);

    }
}
