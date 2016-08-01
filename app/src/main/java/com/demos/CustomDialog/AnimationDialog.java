package com.demos.CustomDialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.demos.R;

/**
 * Created by Mr_Wrong on 16/2/2.
 */
public class AnimationDialog extends AlertDialog implements View.OnClickListener {
    private View mBtnGroupView, mDividerView, mBkgView, mDialogView;
    private TextView mTitleTv, mContentTv, mPositiveBtn, mNegativeBtn;
    private ImageView mContentIv;
    private CharSequence mTitleText, mContentText, mPositiveText, mNegativeText;

    public AnimationDialog(Context context) {
        this(context, 0);
    }

    public AnimationDialog(Context context, int theme) {
        super(context, R.style.color_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = View.inflate(getContext(), R.layout.dialog_layout, null);
        setContentView(contentView);
        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mBkgView = contentView.findViewById(R.id.llBkg);
        mTitleTv = (TextView) contentView.findViewById(R.id.tvTitle);
        mContentTv = (TextView) contentView.findViewById(R.id.tvContent);
        mContentIv = (ImageView) contentView.findViewById(R.id.ivContent);

        mPositiveBtn = (TextView) contentView.findViewById(R.id.btnPositive);
        mNegativeBtn = (TextView) contentView.findViewById(R.id.btnNegative);

        mDividerView = contentView.findViewById(R.id.divider);
        mBtnGroupView = contentView.findViewById(R.id.llBtnGroup);

        mPositiveBtn.setOnClickListener(this);
        mNegativeBtn.setOnClickListener(this);

        mTitleTv.setText(mTitleText);
        mContentTv.setText(mContentText);
        mPositiveBtn.setText(mPositiveText);
        mNegativeBtn.setText(mNegativeText);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDialogView.startAnimation(AnimationLoader.getInAnimation(getContext()));
    }

    @Override
    public void onClick(View v) {


    }
}
