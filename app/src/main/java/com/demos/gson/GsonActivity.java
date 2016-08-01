package com.demos.gson;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;

/**
 * Created by wangpeng on 16/7/23.
 */
public class GsonActivity extends ToolBarActivity {
    @Override
    public int getLayout() {
        return R.layout.gson;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

    }
}
