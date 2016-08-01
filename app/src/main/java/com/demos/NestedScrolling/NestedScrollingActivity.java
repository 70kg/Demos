package com.demos.NestedScrolling;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;

/**
 * Created by Mr_Wrong on 16/2/24.
 */
public class NestedScrollingActivity extends ToolBarActivity {
    @Override
    public int getLayout() {
        return R.layout.nested_scrolling;
    }

    TextView depentent;
    int y = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        depentent = mTool.find(R.id.depentent);
        depentent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        y = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        depentent.offsetTopAndBottom((int) (event.getY() - y));
                        y = (int) event.getY();
                        break;
                }

                return true;
            }
        });
    }
}
