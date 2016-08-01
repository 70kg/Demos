package com.demos.faceabstrct;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demos.R;

/**
 * Created by Mr_Wrong on 16/3/11.
 */
public class Layout extends LinearLayout {
    public Layout(Context context) {
        this(context, null);
    }

    public Layout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Layout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.abstract_layout, this);
    }

    private TextView mName;
    private TextView mContent;

    private IPost mIPost;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mName = (TextView) findViewById(R.id.tv_name);
        mContent = (TextView) findViewById(R.id.tv_content);
    }

    public void setPost(IPost post) {
        this.mIPost = post;
        mName.setText(mIPost.getName());
        mContent.setText(mIPost.getContent());
    }
}
