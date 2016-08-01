package com.demos.main.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.demos.R;
import com.demos.tools.Tool;

import butterknife.ButterKnife;

/**
 * Created by Mr_Wrong on 16/2/2.
 */
public abstract class ToolBarActivity extends AppCompatActivity {
    public static final String TITLE = "title";
    protected Toolbar mToolbar;
    protected Tool mTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mTool = new Tool(this);
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        mToolbar = (Toolbar) findViewById(R.id.inc_toolbar);
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(R.drawable.ic_back);
            mToolbar.setTitleTextColor(Color.WHITE);
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        String title = getIntent().getStringExtra(TITLE);
        setTitle(title == null ? getResources().getString(R.string.app_name) : title);
    }


    public abstract int getLayout();


    protected void go(Class<?> clazz) {
        startActivity(new Intent(this, clazz));
    }
}
