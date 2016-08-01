package com.demos.agera;

import com.demos.R;
import com.demos.main.base.ToolBarActivity;

import butterknife.OnClick;

/**
 * Created by wangpeng on 16/6/2.
 */

public class AgeraSecondActivity extends ToolBarActivity {
    @Override
    public int getLayout() {
        return R.layout.second_activity;
    }

    @OnClick(R.id.txt)
    public void _click() {
        AgeraBus.post("这是来自二次元的东西");
    }

    @OnClick(R.id.txt_model)
    void _clickModel() {
        AgeraModel model = new AgeraModel("123", "value");
        AgeraBus.post(model);
    }

}
