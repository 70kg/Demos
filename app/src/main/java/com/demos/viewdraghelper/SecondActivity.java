package com.demos.viewdraghelper;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;
import com.socks.library.KLog;

/**
 * Created by Mr_Wrong on 16/2/17.
 */
public class SecondActivity extends ToolBarActivity {
    BackLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayout = (BackLayout) findViewById(R.id.swip_back_layout);
        mLayout.setOnSwipListener(new BackLayout.onSwipListener() {
            @Override
            public void open() {
                finish();
                overridePendingTransition(0, R.anim.right_anim);
            }

            @Override
            public void close() {

            }

            @Override
            public void onSwip(float percent) {
                KLog.e(percent);
            }
        });

        Button button = (Button) findViewById(R.id.btn_swip_second);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SecondActivity.this, "点击了", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getLayout() {
        return R.layout.swip_second;
    }
}
