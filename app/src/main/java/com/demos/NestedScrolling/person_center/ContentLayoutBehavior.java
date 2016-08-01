package com.demos.NestedScrolling.person_center;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wangpeng on 16/7/4.
 */
public class ContentLayoutBehavior extends ViewOffsetBehavior<RecyclerView> {
    private CoordinatorLayout.LayoutParams layoutParams;
    HeadLayout headerLayout;

    public ContentLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, RecyclerView child, int layoutDirection) {
        layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {
        return dependency instanceof HeadLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RecyclerView child, View dependency) {
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) dependency.getLayoutParams()).getBehavior();
        if (behavior instanceof HeadBehavior) {
            int headerOffset = ((HeadBehavior) behavior).getTopAndBottomOffset();
            int contentsOffset = dependency.getHeight() + headerOffset;
            setTopAndBottomOffset(contentsOffset);
            int height = parent.getHeight() - contentsOffset;
            layoutParams.height = height;
            child.setLayoutParams(layoutParams);
        }
        return false;
    }


    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, RecyclerView child, int parentWidthMeasureSpec,
                                  int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        if (child.getLayoutParams().height == ViewGroup.LayoutParams.MATCH_PARENT) {
            List dependencies = parent.getDependencies(child);
            if (dependencies.isEmpty()) {
                return false;
            }
            headerLayout = findHeaderLayout(dependencies);
            if (headerLayout != null && ViewCompat.isLaidOut(headerLayout)) {
                int height = parent.getHeight() - headerLayout.getMeasuredHeight();
                int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST);
                parent.onMeasureChild(child, parentWidthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed);
                return true;
            }
        }
        return false;
    }


    private static HeadLayout findHeaderLayout(List<View> views) {
        int z = views.size();
        for (int i = 0; i < z; ++i) {
            View view = views.get(i);
            if (view instanceof HeadLayout) {
                return (HeadLayout) view;
            }
        }

        return null;
    }

}
