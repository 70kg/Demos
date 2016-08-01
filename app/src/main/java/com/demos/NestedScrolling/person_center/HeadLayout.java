package com.demos.NestedScrolling.person_center;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by wangpeng on 16/7/4.
 */
public class HeadLayout extends RelativeLayout implements NestedScrollingChild{
    public HeadLayout(Context context) {
        super(context);
    }

    public HeadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private HeaderTranslationListener HeaderTranslationListener;

    public void setHeaderTranslationListener(HeaderTranslationListener HeaderTranslationListener) {
        this.HeaderTranslationListener = HeaderTranslationListener;
    }

    public void notifyBeginTranslation(int offsetY) {
        if (HeaderTranslationListener != null) {
            HeaderTranslationListener.onTranslate(offsetY);
        }
    }

}
