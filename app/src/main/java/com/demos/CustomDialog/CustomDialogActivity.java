package com.demos.CustomDialog;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;
import com.socks.library.KLog;

import butterknife.OnClick;

/**
 * Created by Mr_Wrong on 16/2/2.
 */
public class CustomDialogActivity extends ToolBarActivity {
    ImageView imageView;


    @Override
    public int getLayout() {
        return R.layout.test;
    }

    @OnClick(R.id.btn_dialog)
    void _dialog() {
        AnimationDialog dialog = new AnimationDialog(CustomDialogActivity.this);
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView = mTool.find(R.id.iv_view_animation);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CustomDialogActivity.this, R.style.color_dialog);
                View view = LayoutInflater.from(CustomDialogActivity.this).inflate(R.layout.dialog_layout, null);
                builder.setView(view);
                AlertDialog d = builder.create();
                TextView title = (TextView) view.findViewById(R.id.tvTitle);
                title.setText("hehe");
                TextView button = (TextView) view.findViewById(R.id.btnNegative);
                button.setText("取消");
                d.show();

                Window window = d.getWindow();
                window.setWindowAnimations(R.style.dialogWindowAnim);

            }
        });


        final AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();


        KLog.e(interpolator.getInterpolation(0.25f));

        ValueAnimator animator = ValueAnimator.ofFloat(0, 10);
        animator.setInterpolator(interpolator);
        animator.setDuration(2000);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float f = (float) animation.getAnimatedValue();
                imageView.setScaleX(f);

            }
        });


    }

}
