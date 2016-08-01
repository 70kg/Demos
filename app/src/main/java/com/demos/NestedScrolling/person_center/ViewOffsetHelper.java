package com.demos.NestedScrolling.person_center;

import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * Created by wangpeng on 16/7/4.
 */
public class ViewOffsetHelper {
    private final View mView;
    private int mLayoutTop;
    private int mOffsetTop;

    public ViewOffsetHelper(View view) {
        mView = view;
    }

    public void onViewLayout() {
        mLayoutTop = mView.getTop();
        updateOffsets();
    }

    private void updateOffsets() {
        if (Build.VERSION.SDK_INT == 22) {
            ViewCompat.setTranslationY(mView, (float) mOffsetTop);
        } else {
            ViewCompat.offsetTopAndBottom(mView, mOffsetTop - mView.getTop() - mLayoutTop);
        }
    }

    public boolean setTopAndBottomOffset(int offset) {
        if (mOffsetTop != offset) {
            mOffsetTop = offset;
            updateOffsets();
            return true;
        } else {
            return false;
        }
    }

    public int getTopAndBottomOffset() {
        return mOffsetTop;
    }
}
