package com.demos.NestedScrolling;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;
import com.socks.library.KLog;

/**
 * Created by Mr_Wrong on 16/2/24.
 */
public class BehaviorActivity extends ToolBarActivity {
    @Override
    public int getLayout() {
        return R.layout.behavior_layout;
    }

    int y = 10;
    float mFromXDelta = 0, mFromYDelta = 0;
    float mToXDelta = 200, mToYDelta = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Button button1 = mTool.find(R.id.btn_behavior1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTool.start(NestedScrollingActivity.class);
            }
        });
        Button button2 = mTool.find(R.id.btn_behavior2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTool.start(FabActivity.class);
            }
        });

        final Button button = mTool.find(R.id.btn_x);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation1 = new Animation() {
                    @Override
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        float dx = mFromXDelta;
                        float dy = mFromYDelta;
                        if (mFromXDelta != mToXDelta) {
                            dx = mFromXDelta + ((mToXDelta - mFromXDelta) * interpolatedTime);
                        }
                        if (mFromYDelta != mToYDelta) {
                            dy = mFromYDelta + ((mToYDelta - mFromYDelta) * interpolatedTime);
                        }
//                        KLog.e(dx);
                        KLog.e(interpolatedTime);
                        t.getMatrix().setTranslate(dx, dy);
                    }
                };
                animation1.setDuration(1000);
                button.startAnimation(animation1);


//                button.setTranslationY(y += 10);//getTop并不会变  变的是getTranslationY view也移动了

//                ViewCompat.offsetTopAndBottom(button, 10);//getTop变了  但是getTranslationY并没有变  view也移动了
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button.getLayoutParams();
//                params.setMargins(0, y += 10, 0, 0);
//                button.setLayoutParams(params);//这个和offsetTopAndBottom是一样的  也就是说setTranslationY并没有改变LayoutParams的属性

//                button.setTop(button.getTop() + 10);//只是重绘?
//                button.layout(0,button.getTop() + 10,button.getWidth(),button.getTop()+10+button.getHeight());
//                KLog.e(button.getTop());
//                KLog.e(button.getY());
//                KLog.e(button.getTranslationY());

                //还是不明白为什么setTranslationY没有改变getTop的数值
                //既然都是3.0之后的属性,那为什么改变translation的同时把gettop也给改变了
            }
        });
    }
}
