package com.demos.singleon;

import android.os.Bundle;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;

/**
 * Created by Mr_Wrong on 16/3/27.
 */
public class SingleTonActivity extends ToolBarActivity {
    @Override
    public int getLayout() {
        return R.layout.singleton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SingletonEnum singleton = SingletonEnum.INSTANCE;
        singleton.doSomeThings();
    }
}
