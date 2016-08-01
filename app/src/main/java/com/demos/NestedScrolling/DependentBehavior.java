package com.demos.NestedScrolling;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Mr_Wrong on 16/2/24.
 */
public class DependentBehavior extends CoordinatorLayout.Behavior<View> {
    public DependentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        int offsetV = dependency.getTop() - child.getTop();

//        int top = dependency.getTop();
//        TextView textView = (TextView) parent.getChildAt(3);
//        textView.setText(top + "");

//        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
//        layoutParams.height = top;
//        child.setLayoutParams(layoutParams);

        ViewCompat.offsetTopAndBottom(child, offsetV);
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof TextView;
    }
}
